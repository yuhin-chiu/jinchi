/**
 * Created by yuxuanjiao on 2017/4/17.
 */
$(function () {
    var events = {
        init_page: function () {
            var type = $("body").attr("page");
            $(".header-content .header-main ." + type).addClass("selected");
        },
        jump: function () {
            if ($(this).hasClass("selected")) {
                return;
            }
            var type = $(this).text();
        }
    };

    function bind_event() {
        $(".header-content .header-main div span").click(events.jump);
    }

    function init() {
        events.init_page();
        bind_event();
    }

    init();
});