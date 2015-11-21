<?php

namespace backend\modules\user\models;

use Yii;

/**
 * This is the model class for table "user_profile".
 *
 * @property integer $id
 * @property string $code
 * @property integer $data_added
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
            [['data_added'], 'integer'],
            [['code', 'team', 'role', 'username', 'pass'], 'string', 'max' => 255],
        ];
    }

    public function beforeSave() {
        $this->data_added = time();

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
            'data_added' => 'Data Added',
            'team' => 'Team',
            'role' => 'Role',
        ];
    }
}
