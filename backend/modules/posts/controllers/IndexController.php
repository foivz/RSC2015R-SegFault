<?php

namespace backend\modules\posts\controllers;

use yii\web\Controller;
use yii\web\UploadedFile;
use backend\modules\posts\models\Post;
use backend\modules\posts\models\File;

class IndexController extends Controller
{
    public function actionIndex()
    {
        return $this->render('index');
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
                //$post->image = $response['file_name'];
                //$post->save();

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
}
