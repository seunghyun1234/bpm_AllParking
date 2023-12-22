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
        $('.parkinging2').each(function (i) {
            var bottom_of_element = $(this).offset().top + $(this).outerHeight();
            var bottom_of_window = $(window).scrollTop() + $(window).height();
            if (bottom_of_window > bottom_of_element) {
                $(this).animate({ 'opacity': '1', 'margin-left': '0px' }, 1000);
            }
        });
    });
});

