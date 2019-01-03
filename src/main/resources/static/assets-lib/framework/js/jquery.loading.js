/**
 * 加载动画窗口
 *
 * @author dendy
 * @since 2013-7-19 10:13:05
 */
function _getLoadingHtml(msg, type) {
    if (!msg) msg = "加载中...";
    var html = "";
    if (type == 1) {
        html = "<div id='loadingDiv'>"
            + "<div class='ajax-loading-overlay1'></div>"
            + "<div class='ajax-loading-showbox ajax-loading'>"
            + "<div class='ajax-loading-loadingWord'>"
            + "<img src='" + getContentPath() + "/asset-libs/css/img/ajax-loader.gif'>" + msg
            + "</div>"
            + "</div>"
            + "</div>";
    } else {
        html = "<div id='loadingDiv'>"
            + "<div class='ajax-loading-overlay1'></div>"
            + "<div style='opacity: 0; margin-top: 250px;margin-left: -160px;height: 20px;width: 400px;' id='AjaxLoading' class='ajax-loading-showbox'>"
            + "<div class='progress progress-striped' data-percent='" + msg + "'>"
            + "<div style='width: 0' class='progress-bar progress-bar-purple'></div>"
            + "</div>"
            + "</div>"
            + "</div>";
    }
    return html;
}

var intervalId;
function ajaxLoadingInit(msg, type) {
    var loadingDiv = _getLoadingHtml(msg, type);
    var h = $(document).height();
    $(".ajax-loading-overlay1").css({"height": h});
    $("body").append($(loadingDiv));
    // 显示进度
    if (type != 1) {
        var internal = 10;
        var pos = 0;
        intervalId = setInterval(function () {
            var percent = pos * internal;
            if (percent == 100) pos = 0;
            $($("body").find(".progress-bar")).css({width: percent + "%"});
            pos++;
        }, 1000);
    }
}

/**
 * 开始显示loading，在ajax执行之前调用
 * @param msg 操作消息，"加载", "保存", "删除"
 * @param type 0-条形样式，1-旋转图片样式
 */
function startLoading(msg, type) {
    ajaxLoadingInit(msg, type);
    $(".ajax-loading-overlay1").show();
    $(".ajax-loading-showbox").stop(true).animate({'margin-top': '300px', 'opacity': '1'}, 200);
}

/**
 * 加载完成后隐藏，在ajax执行完成后（complete）调用
 */
function endLoading() {
    $(".ajax-loading-showbox").stop(true).animate({'margin-top': '250px', 'opacity': '0'}, 400);
    $(".ajax-loading-overlay1").hide();
    $("body").find("#loadingDiv").remove();
    if (intervalId) {
        clearInterval(intervalId);
    }
}