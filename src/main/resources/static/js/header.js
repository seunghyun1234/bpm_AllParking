
$(function () {
    const nav = $(".menu");
    const nav1 = $(".sub");
    const nav2 = $(".container");
    const miniMenu = $(".mini_menu");
    const sub1 = $(".sub1");
    const sub2 = $(".sub2");
    const sub3 = $(".sub3");
    const sub4 = $(".sub4");

    $(nav).mouseenter(function () {
        $(nav1).stop().slideDown();
        $(nav2).stop().slideDown();
    });

    $(nav).mouseleave(function () {
        $(nav1).stop().slideUp();
        $(nav2).stop().slideUp();
    });
/*---------------------------------------------------------*/
    $(document).ready(function () {
        $(".menu_phone").click(function () {
            $(".mini_menu").slideToggle();
            $(".mini_sign").slideUp(); // sign_phone이 열려 있을 경우 닫음
        });

        $(".font").click(function () {
            var subMenu = $(this).find(".sub_menu");
            $(".sub_menu").not(subMenu).slideUp();
            subMenu.slideToggle();
        });

        $(".sign_phone").click(function () {
            $(".mini_sign").slideToggle();
            $(".mini_menu").slideUp(); // menu_phone이 열려 있을 경우 닫음
        });
    });
    /*---------------------------------------------------------*/

});


