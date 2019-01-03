/**
 * To expand bootstrap and jquery plugins.
 * Based on jQuery and bootstrap 3.x.
 * Created by Dendy on 2015/4/27.
 */
if (!jQuery) {
    throw new Error("Requires jQuery")
}
// ~ 基于bootbox对话框插件的封装 =========================================================================================

if (!window.bootbox) {
    throw new Error("Requires boobox.js");
}

(function (W, $) {
    /**
     * 设置bootbox的默认配置
     */
    window.bootbox.setDefaults({
        locale: "zh_CN"
    });

    var BootBox = {
        /**
         * 成功信息对话框
         * @param message 成功信息
         * @param callback 点击确定后的回调函数
         */
        success: function (message, callback) {
            var options = {
                message: message,
                title: "成功信息",
                buttons: {
                    ok: {
                        label: "关闭",
                        className: "btn-sm btn-success"
                    }
                }
            };

            if (callback && $.isFunction(callback)) {
                options.callback = callback;
            }

            bootbox.alert(options);
        },

        /**
         * 失败信息对话框
         * @param message 失败信息
         * @param callback 点击确定后的回调函数
         */
        error: function (message, callback) {
            var options = {
                message: message,
                title: "失败信息",
                buttons: {
                    ok: {
                        label: "关闭",
                        className: "btn-sm btn-danger"
                    }
                }
            };

            if (callback && $.isFunction(callback)) {
                options.callback = callback;
            }

            bootbox.alert(options);
        },

        /**
         * 警告信息对话框
         * @param message 警告信息
         * @param callback 点击确定后的回调函数
         */
        warning: function (message, callback) {
            var options = {
                message: message,
                title: "警告信息",
                buttons: {
                    ok: {
                        label: "关闭",
                        className: "btn-sm btn-warning"
                    }
                }
            };

            if (callback && $.isFunction(callback)) {
                options.callback = callback;
            }
            bootbox.alert(options);
        },

        /**
         * 确认信息对话框
         * @param message 确认信息
         * @param callback 点击确定后的回调函数
         */
        confirm: function (message, callback) {
            var options = {
                message: message,
                title: "确认信息",
                buttons: {
                    confirm: {
                        className: "btn-sm btn-primary"
                    },
                    cancel: {
                        className: "btn-sm btn-default"
                    }
                }
            };

            if (callback && $.isFunction(callback)) {
                options.callback = callback;
            }

            bootbox.confirm(options);
        },

        /**
         * 自定义信息对话框
         * @param options 对话框参数
         */
        dialog: function (options) {
            bootbox.dialog(options);
        }
    };
    W.BootBox = BootBox;
})(window, jQuery);