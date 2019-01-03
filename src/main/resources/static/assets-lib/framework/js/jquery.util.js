/**
 * Common utils based on Jquery.
 *
 * Created by Dendy on 2015/4/27.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 */
if (!jQuery) {
    throw new Error("Requires jQuery")
}

/**
 * 自定义键值对Map结构。
 *
 * @constructor
 */
function Map() {
    this._ENTRY = [];
}

Map.prototype = {
    constructor: Map
};

/**
 * 向map存入键值对结构数据。
 * @param key 键
 * @param value 值
 * @returns {Number} 当前map中数据条数
 */
Map.prototype.put = function (key, value) {
    return this._ENTRY.push({"key": key, "value": value});
};

/**
 * 获取map的所有键。
 *
 * @returns {Array} 所有键的数组
 */
Map.prototype.keys = function () {
    var res = [];
    for (var i = 0; i < this._ENTRY.length; i++) {
        res.push(this._ENTRY[i].key);
    }
    return res;
};

/**
 * 获取map中所有值的数据。
 *
 * @returns {Array} 所有值的数组
 */
Map.prototype.values = function () {
    var res = [];
    for (var i = 0; i < this._ENTRY.length; i++) {
        res.push(this._ENTRY[i].value);
    }
    return res;
};

/**
 * 根据键获取值的信息。
 *
 * @param key 键
 * @returns {*} 值
 */
Map.prototype.get = function (key) {
    for (var i = 0; i < this._ENTRY.length; i++) {
        if (key == this._ENTRY[i].key)
            return this._ENTRY[i].value;
    }
    return null;
};

/**
 * 清空map的所有数据。
 *
 * @returns {Array} 空的数组
 */
Map.prototype.clear = function () {
    return this._ENTRY = [];
};

/**
 * 从map中删除指定键的数据。
 *
 * @param key 键
 * @returns {*} 删除成功，则返回被删除的键值对数据，否则返回false
 */
Map.prototype.remove = function (key) {
    for (var i = 0; i < this._ENTRY.length; i++) {
        if (key == this._ENTRY[i].key)
            return this._ENTRY.splice(i, 1);
    }
    return false;
};

/**
 * 检查map中是否存有键值对数据。
 *
 * @returns {boolean} 没有数据，返回true，否则返回false
 */
Map.prototype.empty = function () {
    return this._ENTRY.length == 0;
};

/**
 * 系统工具类。
 *
 * @type {{}}
 */
var SysTool = {
    /**
     * ajax请求工具类
     */
    Ajax: {
        processAjaxResult: function (result, callback) {
            if (result.rtnCode == '0000') {
                callback(result.data);
            } else {
                var msg = result.rtnMsg;
                if (window.PopMessage) {
                    if (result.type) {
                        switch (result.type) {
                            case 'success' :
                                PopMessage.success(msg);
                                break;
                            case 'warning' :
                                PopMessage.warning(msg);
                                break;
                            case 'error' :
                                PopMessage.error(msg);
                                break;
                            case 'info':
                                PopMessage.info(msg);
                                break;
                            default:
                                PopMessage.warning(msg);
                        }
                    } else {
                        PopMessage.warning(msg);
                    }
                } else if (window.BootBox) {
                    if (result.type) {
                        switch (result.type) {
                            case 'success' :
                                BootBox.success(msg);
                                break;
                            case 'warning' :
                                BootBox.warning(msg);
                                break;
                            case 'error' :
                                BootBox.error(msg);
                                break;
                            case 'info':
                                BootBox.success(msg);
                                break;
                            default:
                                BootBox.warning(msg);
                        }
                    } else {
                        BootBox.warning(msg);
                    }
                } else {
                    alert(result.rtnMsg);
                }
            }
        },

        _connectWebViewJavascriptBridge: function (callback) {
            if (window.WebViewJavascriptBridge) {
                callback(WebViewJavascriptBridge)
            } else {
                document.addEventListener('WebViewJavascriptBridgeReady', function () {
                    callback(WebViewJavascriptBridge)
                }, false)
            }
        },

        /**
         * 异步请求
         *
         * @author Dendy
         * @since 2013-10-29 16:48:01
         * @param url 请求url
         * @param args 请求参数
         * @param successCallback 成功后回调
         * @param erroCallback 错误后回调
         * @param tipMsg 加载框提示信息
         * @param async 是否异步请求，默认为true
         * @param showLoading 是否显示加载对话框,默认不显示，基于jquery.loading.js
         */
        ajax: function (url, args, successCallback, erroCallback, async, showLoading, tipMsg) {
            if (!url) {
                throw new Error("url地址不能为空！");
            }

            if (showLoading)
                startLoading(tipMsg || "处理", 1);

            if (async == undefined || async == null || async === '') {
                async = true;
            }

            var self = this;
            $.ajax({
                url: url,
                dataType: "json",
                type: "post",
                data: args || {},
                async: async,
                success: function (data) {
                    try {
                        if (data.rtnCode == "310001") {
                            _connectWebViewJavascriptBridge(function (bridge) {
                                bridge.callHandler('handleError', {
                                    'rtnCode': data.rtnCode,
                                    'rtnMsg': data.rtnMsg
                                }, null)

                            })
                        }
                        self.processAjaxResult(data, successCallback);
                    } catch (e) {
//				console.log(e);
                    }
                },
                error: function (data) {
                    try {
                        erroCallback.call(self, data);
                    } catch (e) {
//				console.log(e);
                    }
                },
                complete: function (xhr, textstatus) {
                    if (showLoading)
                        endLoading();
                }
            });
        },

        ajaxJsonCall: function (url, data, callback, completeCallback) {
            function _dataEncode(data) {
                var rel = data;
                var source = "";
                if (typeof(rel) == "object") {
                    // source = SysTool.Html.htmlDeepEncode(JSON.stringify(rel));
                    source = JSON.parse(source);
                    rel = source;
                } else if (typeof(rel) == "string") {
                    // source = SysTool.Html.htmlDeepEncode(rel);
                    rel = source;
                }
                return rel;
            };
            var that = this;
            // data = _dataEncode(data);
            $.ajax({
                url: app_path + '/' + url,
                //contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: data,
                type: "POST",
                dataType: "json",
                error: function (msg) {
                    if (msg && msg.responseText == 'nologin')
                        msg.rtnMsg = '未登录或失效，请重新登录';
                    else {
                        msg.rtnMsg = '发生未知异常';
                    }
                    msg.rtnCode = '999999';
                    callback(msg)
                },
                success: function (data) {
                    // var data = _dataEncode(data);
                    that.processAjaxResult(data, callback);
                },
                complete: function (data) {
                    completeCallback && completeCallback();
                }
            });
        },


        /**
         * 异步提交表单，依赖于jquery.form.js.
         *
         * @param form 提交的表单-dom对象
         * @param successCallback 提交成功回调
         * @param errorCallback 提交失败回调
         * @param showLoading 是否显示加载动画，依赖于jquery.loading.js
         * @param tipMsg 加载动画提示信息，如"保存", "提交".
         */
        ajaxSubmitForm: function (form, successCallback, errorCallback, showLoading, tipMsg) {
            if (!form) {
                Error("目标form不能为空！");
                return;
            }
            if (showLoading)
                startLoading(tipMsg || "提交");

            var self = this;
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (data) {
                    try {
                        self.processAjaxResult(data, successCallback);
                    } catch (e) {
//				console.log(e);
                    }
                }, error: function (data) {
                    try {
                        errorCallback.call(self, data);
                    } catch (e) {
//				console.log(e);
                    }
                }, complete: function () {
                    if (showLoading)
                        endLoading();
                }
            });
        }
    },

    /**
     * 身份证相关工具类。
     */
    IdCard: {
        /**
         * 身份证15位编码规则：dddddd yymmdd xx p
         * dddddd：地区码
         * yymmdd: 出生年月日
         * xx: 顺序类编码，无法确定
         * p: 性别，奇数为男，偶数为女
         * <p />
         * 身份证18位编码规则：dddddd yyyymmdd xxx y
         * dddddd：地区码     * yyyymmdd: 出生年月日
         * xxx:顺序类编码，无法确定，奇数为男，偶数为女
         * y: 校验码，该位数值可通过前17位计算获得
         * <p />
         * 18位号码加权因子为(从右到左) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]
         * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
         * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
         * i为身份证号码从右往左数的 2...18 位; Y_P为脚丫校验码所在校验码数组位置
         *
         */
        _Wi: [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1],    // 加权因子
        _ValideCode: [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2],            // 身份证验证位值.10代表X
        /**
         * 验证身份证号码。
         *
         *
         * 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
         * 地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
         * 出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
         * 顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
         * 校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。
         *
         * 出生日期计算方法。
         * 15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
         * 2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
         * 下面是正则表达式:
         * 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
         * 身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
         * 15位校验规则 6位地址编码+6位出生日期+3位顺序号
         * 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位
         *
         * 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
         * 公式(1)中：
         * i----表示号码字符从由至左包括校验码在内的位置序号；
         * ai----表示第i位置上的号码字符值；
         * Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
         * i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
         * Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1
         *
         * @param idCard
         * @returns {*}
         * @constructor
         */
        idCardValidate: function (idCard) {
            idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
            if (idCard.length == 15) {
                return this.isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
            } else if (idCard.length == 18) {
                var a_idCard = idCard.split("");                // 得到身份证数组
                if (this.isValidityBrithBy18IdCard(idCard) && this.isTrueValidateCodeBy18IdCard(a_idCard)) {   //进行18位身份证的基本验证和第18位的验证
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        },

        /**
         * 判断身份证号码为18位时最后的验证位是否正确
         * @param a_idCard 身份证号码数组
         * @return
         */
        isTrueValidateCodeBy18IdCard: function (a_idCard) {
            var sum = 0;                             // 声明加权求和变量
            if (a_idCard[17].toLowerCase() == 'x') {
                a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
            }
            for (var i = 0; i < 17; i++) {
                sum += this._Wi[i] * a_idCard[i];            // 加权求和
            }
            var valCodePosition = sum % 11;                // 得到验证码所位置
            if (a_idCard[17] == this._ValideCode[valCodePosition]) {
                return true;
            } else {
                return false;
            }
        },

        /**
         * 验证18位数身份证号码中的生日是否是有效生日
         * @param idCard 18位书身份证字符串
         * @return
         */
        isValidityBrithBy18IdCard: function (idCard18) {
            var year = idCard18.substring(6, 10);
            var month = idCard18.substring(10, 12);
            var day = idCard18.substring(12, 14);
            var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
            // 这里用getFullYear()获取年份，避免千年虫问题
            if (temp_date.getFullYear() != parseFloat(year)
                || temp_date.getMonth() != parseFloat(month) - 1
                || temp_date.getDate() != parseFloat(day)) {
                return false;
            } else {
                return true;
            }
        },

        /**
         * 验证15位数身份证号码中的生日是否是有效生日
         * @param idCard15 15位书身份证字符串
         * @return
         */
        isValidityBrithBy15IdCard: function (idCard15) {
            var year = idCard15.substring(6, 8);
            var month = idCard15.substring(8, 10);
            var day = idCard15.substring(10, 12);
            var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
            // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
            if (temp_date.getYear() != parseFloat(year)
                || temp_date.getMonth() != parseFloat(month) - 1
                || temp_date.getDate() != parseFloat(day)) {
                return false;
            } else {
                return true;
            }
        },

        /**
         * 通过身份证判断是男是女。
         *
         * @param idCard 15/18位身份证号码
         * @return 'female'-女、'male'-男
         */
        getGenderByIdCard: function (idCard) {
            idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。
            if (idCard.length == 15) {
                if (idCard.substring(14, 15) % 2 == 0) {
                    return 'female';
                } else {
                    return 'male';
                }
            } else if (idCard.length == 18) {
                if (idCard.substring(14, 17) % 2 == 0) {
                    return 'female';
                } else {
                    return 'male';
                }
            } else {
                return null;
            }
        }
    },

    /**
     * 日期相关工具类。
     */
    Date: {
        /**
         * 将datetime数字转换为指定日期格式的字符串。
         *
         * @param dateStr datetime数字
         * @param format 日期格式，y - 年, M - 月，d - 日, h - 时, m - 分, s - 秒, S - 毫秒。eg：yyyy-MM-dd hh:mm:ss
         * @return date 格式化后的日期格式字符串
         */
        formatDate: function (dateStr, format) {
            if (!dateStr) {
                return null;
            }
            var date = new Date(dateStr);

            var o = {
                "M+": date.getMonth() + 1, // month
                "d+": date.getDate(), // day
                "h+": date.getHours(), // hour
                "m+": date.getMinutes(), // minute
                "s+": date.getSeconds(), // second
                "q+": Math.floor((date.getMonth() + 3) / 3), // quarter
                "S": date.getMilliseconds()
                // millisecond
            };

            if (/(y+)/.test(format))
                format = format.replace(RegExp.$1, (date.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                        : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        },

        /**
         * 将分钟数转换为格式为"时:分"的字符串
         * @param minute 分钟数
         * @return {*} 格式为"时:分"的字符串
         */
        minuteConvertHourMinute: function (minute) {
            if (minute && minute > 0) {
                return _getZeroPrefixNum(parseInt(minute / 60)) + ":" + getZeroPrefixNum(minute % 60);
            } else {
                return "00:00";
            }
        },

        /**
         * 将"时:分"的字符串转换为分钟
         * 将"时.分"的字符串转换为分钟
         * @param hhmm "时:分"字符串
         * @returns {*}
         */
        hourMinuteConvertMinute: function (hhmm) {
            var temp;
            if (/^\d+:\d+$/.test(hhmm)) {
                temp = hhmm.split(":");
                return parseInt(temp[0]) * 60 + parseInt(temp[1]);
            } else if (/^\d+(.\d+)?$/.test(hhmm)) {
                temp = hhmm.split(".");
                if (temp.length == 1) {
                    return parseInt(temp[0]) * 60;
                } else {
                    var dividend = "1";
                    for (var i = 0; i < temp[1].length; i++) {
                        dividend += "0";
                    }
                    return parseInt(temp[0]) * 60 + parseInt(temp[1]) * 60 / dividend;
                }
            } else {
                return null;
            }
        },

        getCurrentWeekInChinese: function () {
            var weeks = new Array('星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日');
            return weeks[new Date().getDay() - 1];
        }
    },

    /**
     * 字符串工具类定义
     */
    String: {

        /**
         * String replaceAll
         * @param src 被替换的字符串
         * @param dest 替换为的字符串
         * @return 替换后的字符串
         */
        replaceAll: function (src, dest) {
            return this.replace(new RegExp(src, "gm"), dest); //g全局
        },

        /**
         * 去两端空格
         * @return 字符串
         */
        trim: function () {
            return this.replace(/(^\s*)|(\s*$)/g, "");
        },

        /**
         * 去左空格
         *
         * @return 字符串
         */
        trimLeft: function () {
            return this.replace(/(^\s*)/g, "");
        },

        /**
         * 去右空格
         *
         * @return 字符串
         */
        trimRight: trimRight = function () {
            return this.replace(/(\s*$)/g, "");
        }
    },

    /**
     * 数字相关工具
     */
    Digit: {
        /**
         * 数字+0前缀数组
         */
        _NUMBER_WITH_ZERO_PREFIX: ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09"],

        /**
         * 获取+0前缀格式的数字[两位]字符串
         *
         * 例如：0 返回 00, 1 返回 01, 11 返回 11
         * @param num 数字
         * @returns {*} 格式化后的数字字符串
         */
        preFillZero: function (num) {
            if (num >= 0 && num < 10) {
                return this._NUMBER_WITH_ZERO_PREFIX[num];
            } else {
                return num;
            }
        },

        /**
         * 判断是否是数字
         * @param oNum
         * @return {boolean}
         */
        isDigit: function (oNum) {
            if (!oNum) {
                return false;
            }
            var strP = /^\d+(\.\d+)?$/;
            if (!strP.test(oNum)) {
                return false;
            }
            try {
                if (parseInt(oNum) != oNum || parseInt(oNum) == 0) {
                    return false;
                }
            } catch (ex) {
                return false;
            }
            return true;
        }
    },

    /**
     * Html相关工具
     */
    Html: {
        /**
         * 将html代码中的特殊字符转义。
         *
         * <p>例如：<html>转义后为&lt;html&gt;
         * <p>例如：%3Chtml%3E转义后为&lt;html&gt;
         * @param str html字符串
         * @return 转义后的html
         */
        htmlEncode: function (str) {
            var s = "";
            if (str.length == 0) return "";
            //s = str.replace(/ /g, "&nbsp;");
            //s = str.replace(/&/g, "&amp;");
            s = str.replace(/</g, "&lt;");
            s = s.replace(/%3C/g, "&lt;");
            s = s.replace(/%3c/g, "&lt;");
            s = s.replace(/>/g, "&gt;");
            s = s.replace(/%3E/g, "&gt;");
            s = s.replace(/%3e/g, "&gt;");
            //s = s.replace(/\'/g, "&#39;");
            //s = s.replace(/\"/g, "&quot;");
            //s = s.replace(/\n/g, "<br>");
            return s;
        },

        /**
         * 将html字符串这种的特殊字符转义。
         *
         * <p>例如：
         *  <li><html>转义后为%26lt%3Bhtml%26gt%3B.
         *  <li>%3Chtml%3E转义后为%26lt%3Bhtml%26gt%3B.
         * @param str html字符串
         * @return 转义后的html
         */
        htmlDeepEncode: function (str) {
            var s = "";
            if (str.length == 0) return "";
            //s = str.replace(/ /g, "&nbsp;");
            //s = str.replace(/&/g, "&amp;");
            s = str.replace(/</g, "%26lt%3B");
            s = s.replace(/%3C/g, "%26lt%3B");
            s = s.replace(/%3c/g, "%26lt%3B");
            s = s.replace(/>/g, "%26gt%3B");
            s = s.replace(/%3E/g, "%26gt%3B");
            s = s.replace(/%3e/g, "%26gt%3B");
            //s = s.replace(/\'/g, "&#39;");
            //s = s.replace(/\"/g, "&quot;");
            //s = s.replace(/\n/g, "<br>");
            return s;
        },

        /**
         * 跑马灯效果
         *
         * @param lh
         * @param speed
         * @param delay
         */
        startmarquee: function (domId, lh, speed, delay) {
            var p = false;
            var t;
            var sh;
            var o = document.getElementById(domId);
            o.innerHTML += o.innerHTML;
            o.style.marginTop = 0;
            o.onmouseover = function () {
                p = true;
            }
            o.onmouseout = function () {
                p = false;
            }
            function start() {
                sh = o.offsetHeight;
                o.style.height = sh;
                t = setInterval(scrolling, speed);
                if (!p) o.style.marginTop = parseInt(o.style.marginTop) - 1 + "px";
            }

            function scrolling() {
                if (parseInt(o.style.marginTop) % lh != 0) {
                    o.style.marginTop = parseInt(o.style.marginTop) - 1 + "px";
                    if (Math.abs(parseInt(o.style.marginTop)) >= sh / 2) o.style.marginTop = 0;
                } else {
                    clearInterval(t);
                    setTimeout(start, delay);
                }
            }

            setTimeout(start, delay);
        },

        bindEnterClick: function ($doms, $enterDom) {
            function _enterClick(e) {
                var curKey = e.which;
                if (curKey == 13) {
                    $enterDom.click();
                    return false;
                }
            }

            if ($doms && $doms.length > 0) {
                $.each($doms, function (i, v) {
                    $(v).keydown(function (e) {
                        _enterClick(e);
                    });
                });
            } else {
                $(document).keydown(function (e) {
                    _enterClick(e);
                });
            }
        }
    },

    /**
     * 浏览器相关。
     */
    Browser: {
        /**
         * 判断浏览器
         * @param ns 判断结果对象
         * @returns {*}
         */
        detectBrowser: function () {
            var ns = {};
            var ua = ns.ua = navigator.userAgent;
            ns.isWebKit = (/webkit/i).test(ua);
            ns.isMozilla = (/mozilla/i).test(ua);
            ns.isIE = "ActiveXObject" in window;
            ns.isFirefox = (/firefox/i).test(ua);
            ns.isChrome = (/chrome/i).test(ua);
            ns.isSafari = (/safari/i).test(ua) && !this.isChrome;
            ns.isMobile = (/mobile/i).test(ua);
            ns.isOpera = (/opera/i).test(ua);
            ns.isIOS = (/ios/i).test(ua);
            ns.isIpad = (/ipad/i).test(ua);
            ns.isIpod = (/ipod/i).test(ua);
            ns.isIphone = (/iphone/i).test(ua) && !this.isIpod;
            ns.isAndroid = (/android/i).test(ua);
            ns.supportStorage = "localStorage" in window;
            ns.supportOrientation = "orientation" in window;
            ns.supportDeviceMotion = "ondevicemotion" in window;
            ns.supportTouch = "ontouchstart" in window;
            ns.supportTransform3d = ('WebKitCSSMatrix' in window);
            ns.cssPrefix = ns.isWebKit ? "webkit" : ns.isFirefox ? "Moz" : ns.isOpera ? "O" : ns.isIE ? "ms" : "";
            return ns;
        },

        /**
         * 判断是否是移动设备
         * @returns {boolean}
         */
        detectMobile: function () {
            var ns = detectBrowser();
            if (ns.isIOS || ns.isIpad || ns.isIpod || ns.isIphone || ns.isAndroid || ns.isMobile) {
                return true;
            } else {
                return false;
            }
        },

        /**
         * 添加到收藏夹。
         *
         * @param url 收藏的地址
         * @param title 收藏名称
         */
        addFavorite: function (url, title) {
            if (document.all) {
                window.external.addFavorite(url, title);
            }
            else if (window.sidebar) {
                window.sidebar.addPanel(title, url, '');
            }
        },

        /**
         * 设置为首页
         * @param url 地址
         */
        setHomepage: function (url) {//设置首页
            if (document.all) {
                document.body.style.behavior = 'url(#default#homepage)';
                document.body.setHomePage(url);
            }
            else if (window.sidebar) {
                if (window.netscape) {
                    try {
                        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                    }
                    catch (e) {
                        alert("您的浏览器未启用[设为首页]功能，开启方法：先在地址栏内输入about:config,然后将项 signed.applets.codebase_principal_support 值该为true即可");
                    }
                }
                var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                prefs.setCharPref('browser.startup.homepage', url);
            }
        },

        /**
         * 验证中文字符
         * @param str
         * @returns {boolean}
         */
        verfyName: function (str) {
            var reg = /^[\u4e00-\u9fa5]+$/i;
            if (reg.test(str))
                return true;
            else
                return false;
        },

        isMobile: function (no) {
            var mobile = /^1[3|5|8|7][0-9]\d{8}$/;
            return mobile.test(no);
        }
    },

    /**
     * 数组相关工具
     */
    Array: {
        /**
         * 数组转换为json格式字符串。
         * @param o 数组
         * @return json格式字符串
         */
        arrayToJSON: function (o) {
            var r = [];
            if (typeof o == "string")
                return "\""
                    + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
                        .replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
            if (typeof o == "object") {
                if (!o.sort) {
                    for (var i in o)
                        r.push(i + ":" + this.arrayToJson(o[i]));
                    if (!!document.all
                        && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
                            .test(o.toString)) {
                        r.push("toString:" + o.toString.toString());
                    }
                    r = "{" + r.join() + "}";
                } else {
                    for (var i = 0; i < o.length; i++) {
                        r.push(this.arrayToJson(o[i]));
                    }
                    r = "[" + r.join() + "]";
                }
                return r;
            }
            return o.toString();
        },
        /**
         * 数组去重
         * @param arr 要去重的数组
         * @returns {Array} 去重后的数组
         */
        unique: function (arr) {
            var ret = [];
            var hash = {};

            for (var i = 0; i < arr.length; i++) {
                var item = arr[i];
                var key = typeof(item) + item;
                if (hash[key] !== 1) {
                    ret.push(item);
                    hash[key] = 1;
                }
            }

            return ret;
        },
    },

    /**
     * 设定命名空间.
     * <p>例如. namespace('com.digitalchina.citizenweb.user')
     * @param spacename 命名空间名称，格式同java包，例如：com.digitalchina.citizenweb.user
     */
    namespace: function () {
        var a = arguments, o = null, i, j, d, rt;
        for (i = 0; i < a.length; ++i) {
            d = a[i].split(".");
            rt = d[0];
            eval('if(typeof ' + rt + '=="undefined"){' + rt + '={};}o=' + rt + ';');
            for (j = 1; j < d.length; ++j) {
                o[d[j]] = o[d[j]] || {};
                o = o[d[j]];
            }
        }
    },
};
