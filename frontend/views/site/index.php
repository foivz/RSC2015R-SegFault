<?php

/* @var $this yii\web\View */

$this->title = 'My Yii Application';
?>
<div class="site-index">

    <div class="jumbotron">
        <h1>LIVEBall</h1>

        <p class="lead">Paintball in your pocket</p>
    </div>

    <div class="body-content">
        <div id="games" class="col-md-6">
            <h2>Current games</h2>
            <?php foreach($games as $model): ?>
            <article class="game">
                <h3><?= $model->name; ?></h3>
                <div class="map"><img src="/images/placeholder.png" /></div>
                <ul>
                    <li>SCORE: <?= $model->scoreA; ?>-<?= $model->scoreB; ?></li>
                    <li>START: <?= $model->start; ?></li>
                    <li>END: <?= $model->end; ?></li>
                </ul>
                <p class="time"></p>
            </article>
            <?php endforeach; ?>
        </div>

    </div>
</div>
