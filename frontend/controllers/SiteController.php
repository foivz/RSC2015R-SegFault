<?php
namespace frontend\controllers;

use backend\modules\game\models\GameUser;
use Yii;
use common\models\LoginForm;
use frontend\models\PasswordResetRequestForm;
use frontend\models\ResetPasswordForm;
use frontend\models\SignupForm;
use frontend\models\ContactForm;
use yii\base\InvalidParamException;
use yii\web\BadRequestHttpException;
use yii\web\Controller;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use backend\modules\game\models\Game;
use backend\modules\user\models\UserProfile;
use frontend\models\Post;
use yii\web\Cookie;

/**
 * Site controller
 */
class SiteController extends Controller
{
    /**
     * @inheritdoc
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['logout', 'signup'],
                'rules' => [
                    [
                        'actions' => ['signup'],
                        'allow' => true,
                        'roles' => ['?'],
                    ],
                    [
                        'actions' => ['logout'],
                        'allow' => true,
                        'roles' => ['@'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'logout' => ['post'],
                ],
            ],
        ];
    }

    /**
     * @inheritdoc
     */
    public function actions()
    {
        return [
            'error' => [
                'class' => 'yii\web\ErrorAction',
            ],
            'captcha' => [
                'class' => 'yii\captcha\CaptchaAction',
                'fixedVerifyCode' => YII_ENV_TEST ? 'testme' : null,
            ],
        ];
    }

    /**
     * Displays homepage.
     *
     * @return mixed
     */
    public function actionIndex()
    {
        $cookies = Yii::$app->request->cookies;

        $games = Game::find()->where(['live'=>1])->all();
        $posts = Post::find()->all();

        return $this->render('index', [
            'games' => $games,
            'posts' => $posts,
            'cookies' => $cookies,
        ]);
    }

    public function actionRefresh($id=null) {
        //var_dump($id) or die;
        $model = Game::findOne($id);
        $players = GameUser::find()->where(['game_id'=>$model->id])->all();

        $data = '
                <h3>'.$model->name.'</h3>
                <div game-id="'.$model->id.'" class="button">Refresh <i class="fa fa-refresh"></i></div>
                <div id="map_canvas" style="width:100%; height:300px;"></div>
                <script>
                        var map,
                            service;

                                var latlng = new google.maps.LatLng('.$players[0]->lat.', '.$players[0]->lng.');

                                var myOptions = {
                                    zoom: 20,
                                    center: latlng,
                                    mapTypeId: google.maps.MapTypeId.ROADMAP
                                };

                                map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
        ';
        $blue = '"http://maps.google.com/mapfiles/ms/icons/red-dot.png"';
        $red = '"http://maps.google.com/mapfiles/ms/icons/red-dot.png"';

        foreach($players as $player):
            $data = $data.'
            var latlng_marker = new google.maps.LatLng('.$player->lat.', '.$player->lng.');


            var marker = new google.maps.Marker({
                position: latlng_marker,
                icon: '.($player->team=="A" ? $blue:$red).',
            map: map
            });

            marker.setMap(map);';
        endforeach;

        $data = $data.'
                </script>

                <ul>
                <li>SCORE: '.$model->scoreA.'-'.$model->scoreB.'</li>
                <li>START: '.$model->start.'</li>
                <li>END: '.$model->end.'</li>
                </ul>
                <p class="time"></p>
                ';

        return $data;
    }

    public function actionVote($id = null)
    {
        $cookies = \Yii::$app->request->cookies;

        $post = Post::findOne($id);
        if($post) {
            if($cookies->getValue('voted', 0)) return 0;
            else {
                $post->vote++;
                $post->save();

                $cookies = \Yii::$app->response->cookies;

                $cookies->add(new Cookie([
                    'name' => 'voted',
                    'value' => '1',
                ]));

                return $post->vote;
            }
        }

        return 0;
    }

    /**
     * Logs in a user.
     *
     * @return mixed
     */
    public function actionLogin()
    {
        if (!\Yii::$app->user->isGuest) {
            return $this->goHome();
        }

        $model = new LoginForm();
        if ($model->load(Yii::$app->request->post()) && $model->login()) {
            return $this->goBack();
        } else {
            return $this->render('login', [
                'model' => $model,
            ]);
        }
    }

    /**
     * Logs out the current user.
     *
     * @return mixed
     */
    public function actionLogout()
    {
        Yii::$app->user->logout();

        return $this->goHome();
    }

    /**
     * Displays contact page.
     *
     * @return mixed
     */
    public function actionContact()
    {
        $model = new ContactForm();
        if ($model->load(Yii::$app->request->post()) && $model->validate()) {
            if ($model->sendEmail(Yii::$app->params['adminEmail'])) {
                Yii::$app->session->setFlash('success', 'Thank you for contacting us. We will respond to you as soon as possible.');
            } else {
                Yii::$app->session->setFlash('error', 'There was an error sending email.');
            }

            return $this->refresh();
        } else {
            return $this->render('contact', [
                'model' => $model,
            ]);
        }
    }

    /**
     * Displays about page.
     *
     * @return mixed
     */
    public function actionAbout()
    {
        return $this->render('about');
    }

    /**
     * Signs user up.
     *
     * @return mixed
     */
    public function actionSignup()
    {
        $model = new UserProfile();

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            $model->code = 'ID'.$model->id.'TEAM'.$model->team;
            $model->save();

            return $this->redirect(['profile', 'id'=>$model->id]);

        } else {
            return $this->render('signup', [
                'model' => $model,
            ]);
        }
    }

    public function actionProfile($id)
    {
        $model = UserProfile::findOne($id);
        $games = Game::find()->all();

        $games_played = 0;
        foreach($games as $g)
            if(GameUser::find()->where(['user_id'=>$id])->all()) $games_played++;

        return $this->render('profile', [
            'model' => $model,
            'games' => $games,
            'id' => $id,
            'games_played' => $games_played,
        ]);
    }

    public function actionUpload() {
        //$model = new File();
        $post = new Post();
        $file_upload_url = '/uploads';

        //var_dump(json_encode($_POST)) or die;

        if (isset($_FILES['image']['name'])) {
            //$post->load(\Yii::$app->request->post());
            if(isset($_POST['title'])) $post->title = $_POST['title'];
            if(isset($_POST['text'])) $post->title = $_POST['text'];

            $target_path = $file_upload_url . basename($_FILES['image']['name']);

            // reading other post parameters
            $email = isset($_POST['email']) ? $_POST['email'] : '';
            $website = isset($_POST['website']) ? $_POST['website'] : '';

            $response['file_name'] = basename($_FILES['image']['name']);
            $response['email'] = $email;
            $response['website'] = $website;

            try {
                // Throws exception incase file is not being moved
                if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path)) {
                    // make error flag true
                    $response['error'] = true;
                    //$response['message'] = 'Could not move the file!';
                }

                // File successfully uploaded
                //$response['message'] = 'File uploaded successfully!';
                $response['error'] = false;
                $response['file_path'] = $file_upload_url . basename($_FILES['image']['name']);

                //save path
                $post->image = $response['file_name'];
                $post->save();

            } catch (Exception $e) {
                // Exception occurred. Make error flag true
                $response['error'] = true;
                //$response['message'] = $e->getMessage();
            }
        } else {
            // File parameter is missing
            $response['error'] = true;
            //$response['message'] = 'Not received any file!';
        }

        echo json_encode($response);
    }

    /**
     * Requests password reset.
     *
     * @return mixed
     */
    public function actionRequestPasswordReset()
    {
        $model = new PasswordResetRequestForm();
        if ($model->load(Yii::$app->request->post()) && $model->validate()) {
            if ($model->sendEmail()) {
                Yii::$app->session->setFlash('success', 'Check your email for further instructions.');

                return $this->goHome();
            } else {
                Yii::$app->session->setFlash('error', 'Sorry, we are unable to reset password for email provided.');
            }
        }

        return $this->render('requestPasswordResetToken', [
            'model' => $model,
        ]);
    }

    /**
     * Resets password.
     *
     * @param string $token
     * @return mixed
     * @throws BadRequestHttpException
     */
    public function actionResetPassword($token)
    {
        try {
            $model = new ResetPasswordForm($token);
        } catch (InvalidParamException $e) {
            throw new BadRequestHttpException($e->getMessage());
        }

        if ($model->load(Yii::$app->request->post()) && $model->validate() && $model->resetPassword()) {
            Yii::$app->session->setFlash('success', 'New password was saved.');

            return $this->goHome();
        }

        return $this->render('resetPassword', [
            'model' => $model,
        ]);
    }
}
