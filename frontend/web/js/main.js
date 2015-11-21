$(document).on('click', '.vote.voteup',function(){
    var element = $(this);
    var id = element.attr('vote-id');

    //show working animation
    //element.prev().show();
    //element.css('opacity', '0');

    // request change
    $.get("/site/vote/", {id:id}, function(response){
        if(response) {
            element.addClass('voted');
            element.removeClass('voteup');
            element.html(element.html()+' +'+response);

            return true;
        }
    });
    return false;
});