<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model backend\modules\game\models\Game */

$this->title = 'Create Game';
$this->params['breadcrumbs'][] = ['label' => 'Games', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="game-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
        'create' => $create,
    ]) ?>

</div>
