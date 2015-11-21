<?php

namespace backend\modules\game\models;

use Yii;

/**
 * This is the model class for table "game_user".
 *
 * @property integer $id
 * @property integer $user_id
 * @property integer $game_id
 */
class GameUser extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'game_user';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [[], 'required'],
            [['user_id', 'game_id', 'location'], 'integer'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'user_id' => 'User ID',
            'game_id' => 'Game ID',
        ];
    }
}
