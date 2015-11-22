<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use yii\helpers\ArrayHelper;
use \yii\jui\DatePicker;

/* @var $this yii\web\View */
/* @var $model backend\modules\game\models\Game */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="game-form">
    <?php $form = ActiveForm::begin(); ?>

    <?php if($create): ?>
        <?= $form->field($model, 'players_num')->textInput() ?>
    <?php endif; ?>

    <?php if(!$create): ?>

    <?= $form->field($model, 'name')->textInput() ?>

    <?= $form->field($model, 'date')->textInput(['style'=>'display:hidden;'])->widget(DatePicker::classname(), [
        'dateFormat' => 'php:d.m.Y',
    ])->textInput() ?>

    <?= $form->field($model, 'start')->textInput() ?>

    <?= $form->field($model, 'end')->textInput() ?>

    <?= $form->field($model, 'scoreA')->textInput() ?>

    <?= $form->field($model, 'scoreB')->textInput() ?>

    <?php //var_dump($user_ids) or die; ?>

    <label>Team A</label>
    <?php $i=0; while($i<$model->players_num): ?>
            <?php
            $dropdownCategories = ArrayHelper::map($users, 'id', 'username');
            echo Html::dropDownList('Auser'.$i, (isset($user_ids['A'][$i]) ? $user_ids['A'][$i]:'0'), $dropdownCategories, [
                'prompt' => 'Odaberi korisnika',
                'class' => 'form-control',
            ]);
            ?>
            <br />
    <?php $i++; endwhile; ?>

    <label>Team B</label>
    <?php while($i<$model->players_num*2): ?>
        <?php
        $dropdownCategories = ArrayHelper::map($users, 'id', 'username');
        echo Html::dropDownList('Buser'.$i, (isset($user_ids['B'][$i]) ? $user_ids['B'][$i]:'0'), $dropdownCategories, [
            'prompt' => 'Odaberi korisnika',
            'class' => 'form-control',
        ]);
        ?>
        <br />
        <?php $i++; endwhile; ?>

    <?php endif; ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
