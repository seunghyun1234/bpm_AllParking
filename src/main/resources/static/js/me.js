$(function () {
    const $topBtn = document.querySelector(".moveTopBtn");


    // top 에서는 안나오고 스크롤하면 나오게 하기
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $(".moveTopBtn").addClass("on");
        }
        else {
            $(".moveTopBtn").removeClass("on");
        }

    });

    // 버튼 클릭 시 맨 위로 이동
    $topBtn.onclick = () => {
        window.scrollTo({ top: 0, behavior: "smooth" });
    }

    // 푸터에서 멈추게하기
    var $w = $(window),
        footerHei = $('footer').outerHeight(),
        $banner = $('.moveTopBtn');

    $w.on('scroll', function () {
        var sT = $w.scrollTop();
        var val = $(document).height() - $w.height() - footerHei;
        if (sT >= val)
            $banner.addClass('onn')
        else
            $banner.removeClass('onn')
    });
    // 2번째 이미지 페이징 처리
    $(window).scroll(function () {
        $('.img').each(function (i) {
            var bottom_of_element = $(this).offset().top + $(this).outerHeight() / 3;
            var bottom_of_window = $(window).scrollTop() + $(window).height();
            if (bottom_of_window > bottom_of_element) {
                $(this).animate({ 'opacity': '1' }, 1000);
            }
        });
    });
    // 3번째 이미지 페이징 처리
    $(window).scroll(function () {
        $('.parking1_img').each(function (i) {
            var bottom_of_element = $(this).offset().top + $(this).outerHeight();
            var bottom_of_window = $(window).scrollTop() + $(window).height();
            if (bottom_of_window > bottom_of_element) {
                $(this).animate({ 'opacity': '1', 'margin-left': '0px' }, 1000);
            }
        });
    });


    const elm = document.querySelectorAll('section');
    const elmCount = elm.length;
    elm.forEach(function(item, index){
        item.addEventListener('mousewheel', function(event){
            event.preventDefault();
            let delta = 0;

            if (!event) event = window.event;
            if (event.wheelDelta) {
                delta = event.wheelDelta / 120;
                if (window.opera) delta = -delta;
            }
            else if (event.detail)
                delta = -event.detail / 3;

            let moveTop = window.scrollY;
            let elmSelector = elm[index];

            // wheel down : move to next section
            if (delta < 0){
                if (elmSelector !== elmCount-1){
                    try{
                        moveTop = window.pageYOffset + elmSelector.nextElementSibling.getBoundingClientRect().top;
                    }catch(e){}
                }
            }
            // wheel up : move to previous section
            else{
                if (elmSelector !== 0){
                    try{
                        moveTop = window.pageYOffset + elmSelector.previousElementSibling.getBoundingClientRect().top;
                    }catch(e){}
                }
            }

            const body = document.querySelector('html');
            window.scrollTo({top:moveTop, left:0, behavior:'smooth'});
        });
    });
});

