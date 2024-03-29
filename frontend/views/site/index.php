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
        <div id="games" class="col-md-7">
            <h2>Current games</h2>
            <?php foreach($games as $model): ?>
            <article id="game<?= $model->id; ?>" game-id="<?= $model->id; ?>" class="game">
                <h3><?= $model->name; ?></h3>
                <div game-id="<?= $model->id; ?>" class="button">Refresh <i class="fa fa-refresh"></i></div>
                <div id="map_canvas<?= $model->id; ?>" style="width:100%; height:300px;"></div>
                <script>
                        var map,
                            service;

                        <?php
                            $players = \backend\modules\game\models\GameUser::find()->where(['game_id'=>$model->id])->all();
                        ?>

                                var latlng = new google.maps.LatLng(<?= $players[0]->lat ?>, <?= $players[0]->lng ?>);

                                var myOptions = {
                                    zoom: 20,
                                    center: latlng,
                                    mapTypeId: google.maps.MapTypeId.ROADMAP
                                };

                                map = new google.maps.Map(document.getElementById("map_canvas<?= $model->id; ?>"), myOptions);

                        <?php
                            foreach($players as $player):
                        ?>
                                var latlng_marker = new google.maps.LatLng(<?= $player->lat ?>, <?= $player->lng ?>);

                                console.log(<?= "'".$player->team."'" ?>);

                                var marker = new google.maps.Marker({
                                    position: latlng_marker,
                                    icon: <?= ($player->team=='A' ? "'http://maps.google.com/mapfiles/ms/icons/red-dot.png'":"'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'"); ?>,
                                    map: map
                                });

                                marker.setMap(map);
                                <?php endforeach; ?>


                </script>

                <ul>
                    <li style="width:100%;text-align: center"><h2>REDTEAM <?= $model->scoreA; ?>-<?= $model->scoreB; ?> BLUETEAM</h2></li>
                    <!--<li>START: <?= $model->start; ?></li>
                    <li>END: <?= $model->end; ?></li>-->
                </ul>
                <p class="time"></p>
            </article>
            <?php endforeach; ?>
            <?= (!$games ? 'No live games..':'') ?>
        </div>
        <section id="posts" class="col-md-5">
            <h2>Posts</h2>
            <?php foreach($posts as $model): ?>
                <article class="row post">
                    <h3><?= $model->title ?></h3>
                    <p class="date"><?= date('d-m-Y', $model->date_added) ?></p>
                    <img src="http://admincapjavert.dev/uploads/<?= $model->image ?>" />
                    <p><?= $model->text ?>
                    <p vote-id="<?= $model->id; ?>" class="vote <?= ($cookies->getValue('voted', 0) ? 'voted':'voteup') ?>"><l class="fa fa-thumbs-up"></l> Vote <?= ($cookies->getValue('voted', 0) ? '+'.$model->vote:'') ?></p>
                </article>
            <?php endforeach; ?>
            <?= (!$posts ? 'No posts..':'') ?>
        </section>

    </div>
</div>
