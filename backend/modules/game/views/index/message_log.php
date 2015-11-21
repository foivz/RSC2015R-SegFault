<?php

use yii\helpers\Html;
use yii\grid\GridView;
use backend\modules\game\models\Game;

$this->title = 'Message LOG';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="game-index">

    <h1><?= Game::findOne($id)->name; ?> - LOG</h1>
    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'game_id',
            'text',

            ['class' => 'yii\grid\ActionColumn', 'template' => '{delete}'],
        ],
    ]); ?>

</div>
