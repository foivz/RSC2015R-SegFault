//toggle visibility
$(document).on('change', 'table.table-striped input:checkbox.live',function(){
    var element = $(this);
    var id = element.attr('data-id');
    var type = element.attr('data-type');

    //show working animation
    //element.prev().show();
    //element.css('opacity', '0');

    // request change
    $.get("/site/togglelive/", {id:id, name:type}, function(response){
        if(response == '1') {
            element.toggleClass('active');
        }
        //element.prev().hide();
        //element.css('opacity', '1');
    });
    return false;
});