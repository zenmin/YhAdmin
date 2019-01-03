//http://datatables.net/plug-ins/pagination#bootstrap
$.extend(true, $.fn.dataTableExt, {
    //sErrMode: ''
});
$.extend(true, $.fn.dataTable.defaults, {
    "sDom": "rt<'row'<'col-sm-6 inline-flex' li><'col-sm-6'p>>",
    "sPaginationType": "bootstrap",
    "oLanguage": {
        "sLengthMenu": "每页 _MENU_ ",
        "sEmptyTable": "没有数据！",
        "sZeroRecords": "没有数据！",
        "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 页",
        "sInfoFiltered" : "共 _MAX_ 条记录",
        "sInfoEmpty": "",
        "sSearch": "搜索：",
        "sLoadingRecords": "查询中...",
        "sProcessing": "<div style='width: 100px; padding: 14px 0; text-align: center; position: absolute; top: 50%; left: 50%; margin-left: -50px; margin-top: -26px;'>查询中...</div>"
    },
    "sServerMethod": "POST",
    "bAutoWidth": true,
    "bProcessing": true,
    "bFilter": false,
    "bServerSide": true,
    "fnServerData": function (sUrl, aoData, fnCallback, oSettings) {
        oSettings.jqXHR = $.ajax({
            "url": sUrl,
            "data": aoData,
            "success": function (json) {
                if (json.sError) {
                    oSettings.oApi._fnLog(oSettings, 0, json.sError);
                }
                json = json.data || json;
                $(oSettings.oInstance).trigger('xhr', [oSettings, json]);
                //清楚exportData 中的数据。
                exportData = new Array();
                fnCallback(json);
            },
            "dataType": "json",
            "cache": false,
            "type": oSettings.sServerMethod
        });
    },
    "fnInitComplete": function (oSettings, json) {
        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function () {
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });
        });
    }
});

/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function (oSettings) {
    return {
        "iStart": oSettings._iDisplayStart,
        "iEnd": oSettings.fnDisplayEnd(),
        "iLength": oSettings._iDisplayLength,
        "iTotal": oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage": Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
        "iTotalPages": Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
    };
}

/* Bootstrap style pagination control */
$.extend($.fn.dataTableExt.oPagination, {
    "bootstrap": {
        "fnInit": function (oSettings, nPaging, fnDraw) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function (e) {
                e.preventDefault();
                if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
                    fnDraw(oSettings);
                }
            };

            $(nPaging).append(
                '<ul class="pagination">' +
                '<li class="prev disabled"><a href="#"><i class="icon-double-angle-left"></i></a></li>' +
                '<li class="next disabled"><a href="#"><i class="icon-double-angle-right"></i></a></li>' +
                '</ul>'
            );
            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', {action: "previous"}, fnClickHandler);
            $(els[1]).bind('click.DT', {action: "next"}, fnClickHandler);
        },

        "fnUpdate": function (oSettings, fnDraw) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf = Math.floor(iListLength / 2);

            if (oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if (oPaging.iPage <= iHalf) {
                iStart = 1;
                iEnd = iListLength;
            } else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }

            for (i = 0, iLen = an.length; i < iLen; i++) {
                // Remove the middle elements
                $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                // Add the new list items and their event handlers
                for (j = iStart; j <= iEnd; j++) {
                    sClass = (j == oPaging.iPage + 1) ? 'class="active"' : '';
                    $('<li ' + sClass + '><a href="#">' + j + '</a></li>')
                        .insertBefore($('li:last', an[i])[0])
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(), 10) - 1) * oPaging.iLength;
                            fnDraw(oSettings);
                        });
                }

                // Add / remove disabled classes from the static elements
                if (oPaging.iPage === 0) {
                    $('li:first', an[i]).addClass('disabled');
                } else {
                    $('li:first', an[i]).removeClass('disabled');
                }

                if (oPaging.iPage === oPaging.iTotalPages - 1 || oPaging.iTotalPages === 0) {
                    $('li:last', an[i]).addClass('disabled');
                } else {
                    $('li:last', an[i]).removeClass('disabled');
                }
            }
        }
    }
});