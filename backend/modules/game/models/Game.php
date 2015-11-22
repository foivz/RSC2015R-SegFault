<?php

namespace backend\modules\game\models;

use Yii;

/**
 * This is the model class for table "game".
 *
 * @property integer $id
 * @property integer $date_added
 * @property integer $name
 * @property integer $start
 * @property integer $end
 * @property integer $scoreA
 * @property integer $scoreB
 */
class Game extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'game';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [[], 'required'],
            [['name'], 'string', 'max' => 255],
            [['date_added', 'scoreA', 'scoreB', 'players_num'], 'integer'],
            [['start', 'end', 'date'], 'safe'],
        ];
    }

    /**
     * before saving do this
     */

    public function beforeSave() {
        $this->date_added = time();

        if(!is_numeric($this->date))
            $this->date = strtotime($this->date);

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
            'date_added' => 'Data Added',
            'name' => 'Name',
            'start' => 'Start ETA',
            'end' => 'End ETA',
            'scoreA' => 'Score A',
            'scoreB' => 'Score B',
        ];
    }
}
