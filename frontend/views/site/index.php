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
            <?php  ?>
            <article class="game">
                <h3>Game 1</h3>
                <div class="map"><img src="/images/placeholder.png" /></div>
                <ul>
                    <li>SCORE: 5-6</li>
                    <li>START: 16:00</li>
                    <li>END: 16:45</li>
                </ul>
                <p class="time"></p>
            </article>
        </div>

    </div>
</div>
