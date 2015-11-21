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
        </div>
        <section class="col-md-6">
            <?php foreach($posts as $model): ?>
                <article class="row post">
                    <h3><?= $model->title ?></h3>
                    <p class="date"><?= date('d-m-Y', $model->date_added) ?></p>
                    <img src="http://admincapjavert.dev/uploads/<?= $model->image ?>" />
                    <p><?= $model->text ?>
                    <p vote-id="<?= $model->id; ?>" class="vote <?= ($cookies->getValue('voted', 0) ? 'voted':'voteup') ?>"><l class="fa fa-thumbs-up"></l> Vote <?= ($cookies->getValue('voted', 0) ? '+'.$model->vote:'') ?></p>
                </article>
            <?php endforeach; ?>
        </section>

    </div>
</div>
