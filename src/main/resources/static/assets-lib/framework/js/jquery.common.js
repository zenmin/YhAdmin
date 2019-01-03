if (!jQuery) {throw new Error("Requires jQuery")}

/**
 * 获取系统路径
 * @returns {string} 系统路径
 */
function getContentPath() {
    if (ctx) return ctx;
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var path = pathName.substr(0, index + 1);
    return path;
}

// jQuery插件工具默认设置=================================================================================================

if ($.fn.inputlimiter()) {
    /**
     * 设置inputlimiter默认配置
     */
    $.extend($.fn.inputlimiter.defaults, {
        remText: '还可输入%n个字符，',
        limitText: '最多可输入%n个字符。'
    });
}

if ($.mask) {
    /**
     * 设置mask默认验证规则配置
     */
    $.extend($.mask.definitions, {
        '2': "[3|4|5|7|8]",
        '8': "[0-9|X|x]"
    });
}

if ($.fn.datepicker) {
    //http://bootstrap-datepicker.readthedocs.org/en/latest/options.html#id5
    /**
     * 设置datepicker默认配置
     */
    $.extend($.fn.datepicker.defaults, {
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        todayBtn: 'linked',
        autoclose: true,
        showOtherMonths: true,
        selectOtherMonths: true
    });
    // http://www.bootcss.com/p/bootstrap-datetimepicker/
    $.extend($.fn.datetimepicker.defaults, {
        format: 'yyyy-mm-dd hh:ii',
        language: 'zh-CN',
        todayBtn: 'linked',
        autoclose: true,
        showOtherMonths: true,
        selectOtherMonths: true
    });
}

if ($.fn.select2) {
    /**
     * 设置select2默认配置
     */
    $.extend($.fn.select2.defaults, {
        dropdownAutoWidth: true,
        allowClear: true,
        dropdownCssClass: "bigdrop"
    });
}


/**
 * 在中间插入内容，适用于textarea
 */
$.fn.extend({
    insertContent: function (myValue, t) {
        var $t = $(this)[0];
        if (document.selection) { // ie
            this.focus();
            var sel = document.selection.createRange();
            sel.text = myValue;
            this.focus();
            sel.moveStart('character', -l);
            var wee = sel.text.length;
            if (arguments.length == 2) {
                var l = $t.value.length;
                sel.moveEnd("character", wee + t);
                t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart("character", wee - t - myValue.length);
                sel.select();
            }
        } else if ($t.selectionStart || $t.selectionStart == '0') {
            var startPos = $t.selectionStart;
            var endPos = $t.selectionEnd;
            var scrollTop = $t.scrollTop;
            $t.value = $t.value.substring(0, startPos)
                + myValue
                + $t.value.substring(endPos, $t.value.length);
            this.focus();
            $t.selectionStart = startPos + myValue.length;
            $t.selectionEnd = startPos + myValue.length;
            $t.scrollTop = scrollTop;
            if (arguments.length == 2) {
                $t.setSelectionRange(startPos - t,
                    $t.selectionEnd + t);
                this.focus();
            }
        } else {
            this.value += myValue;
            this.focus();
        }
    }
});