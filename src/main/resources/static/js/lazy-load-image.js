$(function () {

    loadImage();

    let delay;
    $(window).on('scroll',function(){
        if(delay){
            clearTimeout(delay);
        }
        delay = setTimeout(function(){
            loadImage()
        },200)
    })

    function loadImage(){
        $('img.lazy-load').each(function () {
            let img = $(this);
            let win = $(window);
            if (img.offset().top - 1500 <= win.height() + win.scrollTop()) {
                img.attr('src', img.attr('data-src'));
                img.removeClass('lazy-load');
            }
        })
    }
})

