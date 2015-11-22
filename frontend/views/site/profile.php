<?php

use backend\modules\game\models\GameUser;

/* @var $this yii\web\View */

$this->title = 'My Yii Application';
?>
<div class="site-index">
    <div class="body-content">
        <section class="profile col-md-push-1 col-md-10">
            <div class="img col-md-3">
                <img src="/images/placeholder.png" />
            </div>
            <div class="info">
                <h1><?= $model->username; ?></h1>
                <h2>Member since <?= date('m-d-Y', $model->date_added); ?></h2>
            </div>
            <div class="col-md-12"></div>

            <div class="history col-md-4"><i class="fa fa-odnoklassniki-square"></i> DEATHS: <?= $model->deaths; ?></div>
            <div class="history col-md-4"><i class="fa fa-rebel"></i> KILLS: <?= $model->kills; ?></div>
            <div class="history col-md-4"><i class="fa fa-gamepad"></i> GAMES PLAYED: <?= $games_played; ?></div>

            <div id="games" class="history">
                <?php $nogames=true; foreach($games as $model): break; ?>
                    <?php
                    if(!GameUser::find()->where(['user_id'=>$id])->all()) break;
                    $nogames = false;
                    ?>
                    <article class="game col-md-4">
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
                <?php //if($nogames) echo '<div class="col-md-4"><br />No games</div>'; ?>
            </div>

        </section>

    </div>
</div>
