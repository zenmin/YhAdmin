/**
 * Bootstrap popup message plugin.
 *
 * Show a message box fixed on the right bottom of the screen.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 */
(function (W, $) {
    /**
     * 弹出消息对象。
     *
     * @param msg 提示信息。
     * @param title 信息标题
     * @param during 自动隐藏事件
     * @param type 消息类型[success|warning|error|info]
     * @constructor
     */
    function PopMsg(msg, title, during, type) {
        this.id = 'popmsg_' + new Date().getTime();
        var css = '';
        var ttl = '';
        var icon = '';
        title = title && typeof title == 'string' ? title : '';
        switch (type) {
            case 'success' :
                css = 'alert-success';
                ttl = title || '成功！';
                icon = 'icon-ok';
                break;
            case 'warning' :
                css = 'alert-warning';
                ttl = title || '警告！';
                icon = 'icon-bell';
                break;
            case 'error' :
                css = 'alert-danger';
                ttl = title || '错误！';
                icon = 'icon-remove';
                break;
            case 'info':
            default:
                css = 'alert-info';
                ttl = title || '信息！';
                icon = 'icon-info-sign';
        }
        this.html =
            '<div id="' + this.id + '" class="alert ' + css + '" style="position: fixed; bottom: 10px; right: 10px; width: 250px; ' +
            'max-height: 250px; z-index: 9999; text-overflow: ellipsis; overflow: hidden;display:none;margin-bottom:0">' +
            '<button type="button" class="close" data-dismiss="alert">' +
            '<i class="icon-remove"></i>' +
            '</button>' +
            '<strong>' +
            '<i class="' + icon + '"></i>' + ttl + '<br>' +
            '</strong>' + msg +
            '</div>';
        this.during = during || 1000 * 3;
    }

    PopMsg.prototype = {
        Constructor: PopMsg
    };

    PopMsg.prototype.appear = function (callback) {
        var that = this;
        $('body').append(that.html);
        var this_ = $('#' + this.id);
        this_.slideDown('normal', function () {
            setTimeout(function () {
                this_.slideUp('normal', function () {
                    this_.remove();
                    callback && callback();
                });
            }, that.during);
        });
    };

    /**
     * window系统弹出消息对象。
     * @type {{success: Function, warning: Function, error: Function, info: Function}}
     */
    var PopMessage = {
        _getCallback: function (args) {
            if (!args) return null;
            for (var i = 0; i < args.length; i++) {
                if ($.isFunction(args[i])) {
                    return args[i];
                }
            }
            return null;
        },
        success: function (msg, title, during, callback) {
            return new PopMsg(msg, title, during, 'success').appear(callback || this._getCallback(arguments));
        },
        warning: function (msg, title, during, callback) {
            return new PopMsg(msg, title, during, 'warning').appear(callback || this._getCallback(arguments));
        },
        error: function (msg, title, during, callback) {
            return new PopMsg(msg, title, during, 'error').appear(callback || this._getCallback(arguments));
        },
        info: function (msg, title, during, callback) {
            return new PopMsg(msg, title, during, 'info').appear(callback || this._getCallback(arguments));
        }
    };

    W.PopMessage = PopMessage;
})(window, jQuery);