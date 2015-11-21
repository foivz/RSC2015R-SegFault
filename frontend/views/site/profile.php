<?php

use backend\modules\game\models\GameUser;

/* @var $this yii\web\View */

$this->title = 'My Yii Application';
?>
<div class="site-index">
    <div class="body-content">
        <section class="profile col-md-12">
            <div class="img col-md-3">
                <img src="/images/placeholder.png" />
            </div>
            <div class="info">
                <h1><?= $model->username; ?></h1>
                <h2>Member since <?= date('m-d-Y', $model->date_added); ?></h2>
            </div>
            <div class="col-md-12"></div>
            <div class="history col-md-4">
                <ul>
                    <li>DEATHS: <?= $model->deaths; ?></li>
                    <li>KILLS: <?= $model->kills; ?></li>
                    <li>GAMES PLAYED: <?= $games_played ?></li>
                </ul>
            </div>
            <div id="games" class="history col-md-4">
                <?php $nogames=true; foreach($games as $model): ?>
                    <?php
                    if(!GameUser::find()->where(['user_id'=>$id])->all()) break;
                    $nogames = false;
                    ?>
                    <article class="game">
                        <h3><?= $model->name; ?></h3>
                        <div id="map_canvas" style="width:100%; height:300px;"></div>
                        <script>
                            var map,
                                service;

                            var latlng = new google.maps.LatLng(-34.397, 150.644);

                            var myOptions = {
                                zoom: 1,
                                center: latlng,
                                mapTypeId: google.maps.MapTypeId.ROADMAP
                            };

                            map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

                            var marker = new google.maps.Marker({
                                position: latlng,
                                map: map
                            });

                            marker.setMap(map);


                        </script>

                        <ul>
                            <li>SCORE: <?= $model->scoreA; ?>-<?= $model->scoreB; ?></li>
                            <li>START: <?= $model->start; ?></li>
                            <li>END: <?= $model->end; ?></li>
                        </ul>
                        <p class="time"></p>
                    </article>
                <?php endforeach; ?>
                <?php if($nogames) echo 'No games'; ?>
            </div>

        </section>

    </div>
</div>
