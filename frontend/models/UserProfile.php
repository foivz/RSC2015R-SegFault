<?php

namespace frontend\models;

use Yii;

/**
 * This is the model class for table "user_profile".
 *
 * @property integer $id
 * @property string $code
 * @property integer $date_added
 * @property string $team
 * @property string $role
 */
class UserProfile extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'user_profile';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [[], 'required'],
            [['date_added', 'deaths', 'kills'], 'integer'],
            [['code', 'team', 'role', 'username', 'pass'], 'string', 'max' => 255],
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
            'code' => 'Code',
            'date_added' => 'Data Added',
            'team' => 'Team',
            'role' => 'Role',
        ];
    }
}
