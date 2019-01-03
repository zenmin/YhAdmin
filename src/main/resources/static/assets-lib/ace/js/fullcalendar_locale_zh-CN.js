/**
 * Select2 Chinese translation
 */
(function ($) {
    var defaults = {
        buttonText: {
            prev: '<i class="icon-chevron-left"></i>',
            next: '<i class="icon-chevron-right"></i>'
        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
        monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
        dayNames:['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
        dayNamesShort: ['日','一','二','三','四','五','六'],
        buttonText: {
            prev: '&nbsp;&#9668;&nbsp;',
            next: '&nbsp;&#9658;&nbsp;',
            prevYear: '&nbsp;&lt;&lt;&nbsp;',
            nextYear: '&nbsp;&gt;&gt;&nbsp;',
            today: '今天',
            month: '月',
            week: '周',
            day: '天'
        }
    };

    //fullCalendar没有defaults这个参数，修改过min.js源码
    $.extend($.fn.fullCalendar.defaults,defaults);
})(jQuery);
