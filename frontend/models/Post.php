<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "posts".
 *
 * @property integer $id
 * @property integer $date_added
 * @property string $title
 * @property string $text
 * @property string $image
 */
class Post extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */

    public static function tableName()
    {
        return 'posts';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [[], 'required'],
            [['date_added', 'vote'], 'integer'],
            [['text'], 'string'],
            [['title'], 'string', 'max' => 40],
            [['image'], 'string', 'max' => 255],
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
            'date_added' => 'Date Added',
            'title' => 'Title',
            'text' => 'Text',
            'image' => 'Image',
        ];
    }
}
