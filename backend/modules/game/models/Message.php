<?php

namespace backend\modules\game\models;

use Yii;

/**
 * This is the model class for table "messages".
 *
 * @property integer $id
 * @property integer $game_id
 * @property integer $user_id
 * @property string $text
 * @property integer $date_added
 */
class Message extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'messages';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['game_id', 'user_id', 'text', 'date_added'], 'required'],
            [['game_id', 'user_id', 'date_added'], 'integer'],
            [['text'], 'string'],
        ];
    }

    public function beforeSave() {
        $this->date_added = time();

        return $this;
        parent::beforeSave();
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'game_id' => 'Game ID',
            'user_id' => 'User ID',
            'text' => 'Text',
            'date_added' => 'Date Added',
        ];
    }
}
