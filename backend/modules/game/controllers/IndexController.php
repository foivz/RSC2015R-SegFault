<?php

namespace backend\modules\game\controllers;

use backend\modules\game\models\GameUser;
use backend\modules\game\models\Message;
use backend\modules\user\models\UserProfile;
use Yii;
use backend\modules\game\models\Game;
use backend\modules\game\models\GameSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use yii\data\ActiveDataProvider;

/**
 * IndexController implements the CRUD actions for Game model.
 */
class IndexController extends Controller
{
    /*public function behaviors()
    {
        return [
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'delete' => ['POST'],
                ],
            ],
        ];
    }*/

    /**
     * Lists all Game models.
     * @return mixed
     */
    public function actionIndex()
    {
        $searchModel = new GameSearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single Game model.
     * @param integer $id
     * @return mixed
     */
    public function actionView($id)
    {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
    }

    /**
     * Creates a new Game model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        $create = true;
        $model = new Game();

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['update', 'id' => $model->id]);
        } else {
            return $this->render('create', [
                'model' => $model,
                'create' => $create,
            ]);
        }
    }

    /**
     * Updates an existing Game model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     */
    public function actionUpdate($id)
    {
        $create = false;
        $model = $this->findModel($id);

        $user_ids = [];

        if(!$create) {
            $users = UserProfile::find()->all();
            $users_assigned = GameUser::find()->where(['game_id' => $model->id])->all();

            $i=0; foreach($users_assigned as $user_assigned):
                if($user_assigned)
                    if($user_assigned->team == 'A')
                        $user_ids['A'][$i] = $user_assigned->user_id;
                        else $user_ids['B'][$i] = $user_assigned->user_id;
            $i++; endforeach;
        }

        //var_dump($_POST) or die;

        $i=0; while($i<$model->players_num):
            if(isset($_POST['Auser'.$i])) {
                $assigned = GameUser::find()->where(['team' => 'A', 'user_id' => $_POST['user'.$i], 'game_id' => $model->id])->one();
                if(!$assigned) $assigned = new GameUser();
                $assigned->game_id = $model->id;
                $user_id = $_POST['Auser' . $i];


                if (!$user_id) break;

                $assigned->team = "A";
                $assigned->user_id = $user_id;
                if (!$assigned->save()) var_dump($assigned->getErrors()) or die;
            }

            if(isset($_POST['Buser'.$i])) {
                $assigned = GameUser::find()->where(['team' => 'B', 'user_id' => $_POST['user'.$i], 'game_id' => $model->id])->one();
                if(!$assigned) $assigned = new GameUser();
                $assigned->game_id = $model->id;
                $user_id = $_POST['Buser' . $i];

                if (!$user_id) break;

                $assigned->team = "B";
                $assigned->user_id = $user_id;
                if (!$assigned->save()) var_dump($assigned->getErrors()) or die;
            }
        $i++; endwhile;

        $model->date = date('d.m.Y', $model->date);

        //var_dump($user_ids) or die;

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['update', 'id' => $model->id]);
        } else {
            return $this->render('update', [
                'model' => $model,
                'create' => $create,
                'users' => $users,
                'user_ids' => $user_ids
            ]);
        }
    }

    public function actionMessages()
    {
        $message = new Message();

        header('Content-type:application/json;charset=utf-8');
        if(isset($_POST['text'])) {
            echo 'proslo';
            $message->text = $_POST['text'];
        }
    }

    public function actionLocation() {
        header('Content-type:application/json;charset=utf-8');
        if(isset($_POST['latlng']))
            return json_encode($_POST['latlng']);

        return 'nista';
    }

    public function actionMessagelog($id) {
        $messages = Message::find()->where(['game_id' => $id]);

        $dataProvider = new ActiveDataProvider([
            'query' => $messages,
        ]);

        return $this->render('message_log', [
            'dataProvider' => $dataProvider,
            'id' => $id,
        ]);
    }

    /**
     * Deletes an existing Game model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     */
    public function actionDelete($id)
    {
        $this->findModel($id)->delete();

        return $this->redirect(['index']);
    }

    /**
     * Finds the Game model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Game the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Game::findOne($id)) !== null) {
            return $model;
        } else {
            throw new NotFoundHttpException('The requested page does not exist.');
        }
    }
}
