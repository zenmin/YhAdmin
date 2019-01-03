/**
 * To expand bootstrap and jquery plugins.
 * Based on jQuery and bootstrap 3.x.
 * Created by Dendy on 2015/4/27.
 */
if (!jQuery) {
    throw new Error("Requires jQuery")
}

// ~ 基于bootstrap样式的插件扩展 =========================================================================================

$.fn.extend(
    /**
     * 基于bootstrap水平表单布局的resetForm插件。
     */
    {
        resetForm: function () {
            var form = $(this);
            // 重置表单数据
            form[0].reset();
            // 重置表单样式
            form.find(".form-group").removeClass("has-error has-info");
            form.find('.error-tip').remove();
            // 重置远程验证输入框验证结果
            form.find(".form-group :text").each(function (i, v) {
                var previousValue = $(v).data("previousValue");
                if (previousValue) {
                    previousValue.old = null;
                }
            });
            form.find(".form-group select").each(function (i, v) {
                var previousValue = $(v).data("previousValue");
                if (previousValue) {
                    previousValue.old = null;
                }
            });
            form.find(".form-group :radio").each(function (i, v) {
                var previousValue = $(v).data("previousValue");
                if (previousValue) {
                    previousValue.old = null;
                }
            });
        }
    }
);
$('[data-rel=popover]').popover({container: 'body'});
$('[data-rel=tooltip]').tooltip({container: 'body'});