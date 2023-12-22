
$(function () {
    const nav = $(".menu");
    const nav1 = $(".sub");
    const nav2 = $(".container")
    $(nav).mouseenter(function () {
        $(nav1).stop().slideDown();
        $(nav2).stop().slideDown();



    });

    $(nav).mouseleave(function () {
        $(nav1).stop().slideUp();
        $(nav2).stop().slideUp();
    });


});