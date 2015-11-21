<?php

namespace backend\modules\game\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use backend\modules\game\models\Game;

/**
 * GameSearch represents the model behind the search form about `backend\modules\game\models\Game`.
 */
class GameSearch extends Game
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'date_added', 'name', 'start', 'end', 'scoreA', 'scoreB'], 'integer'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Game::find();

        // add conditions that should always apply here

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        // grid filtering conditions
        $query->andFilterWhere([
            'id' => $this->id,
            'date_added' => $this->date_added,
            'name' => $this->name,
            'start' => $this->start,
            'end' => $this->end,
            'scoreA' => $this->scoreA,
            'scoreB' => $this->scoreB,
        ]);

        return $dataProvider;
    }
}
