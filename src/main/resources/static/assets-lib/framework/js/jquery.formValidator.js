/**
 * Based on jquery.validate.js plugin.
 *
 * Created by Dendy on 2015/4/27.
 */
if (!jQuery) {
    throw new Error("Requires jQuery")
}

(function ($) {
    /**
     * 设置默认提交表单
     */
    $.validator.setDefaults({
        submitHandler: function (form) {
            form.submit();
        }
    });

    /**
     * 添加身份证验证方法
     */
    $.validator.addMethod("idCard", function (value, element, params) {
        return this.optional(element) || _validateIdCard(value).pass;
    }, "请输入正确身份证号");

    /**
     * 添加用户名验证方法
     */
    $.validator.addMethod("username", function (value, element, params) {
        var reg = /^[a-zA-Z][a-zA-Z0-9_]+$/;
        return reg.test(value) || value == "";
    }, "该字段仅为字母、数字、下划线组合且仅以字母开头");

    /**
     * 添加仅数字字母验证方法
     */
    $.validator.addMethod("onlyNumChar", function (value, element, params) {
        var reg = /^([a-zA-Z0-9]+)$/;
        if (reg.test(value) || value == "") {
            return true;
        }
        return false;
    }, "请输入数字或者字母");

    /**
     * 添加拼音证验证方法
     */
    $.validator.addMethod("pinyin", function (value, element, params) {
        var reg = /^[A-Za-z ]{1,30}$/;
        if (reg.test(value) || value == "") {
            return true;
        }
        return false;
    }, "请输入正确拼音");

    /**
     * 添加经纬度验证方法
     */
    $.validator.addMethod("losize", function (value, element, params) {
        var reg = /^-{0,1}(\d+)\.{0,1}(\d+)$/g;
        if ((reg.test(value) && (RegExp.$1 >= -180 && RegExp.$1 <= 180)) || value == "") {
            return true;
        }
        return false;
    }, "请输入正确经度");

    /**
     * 添加经度验证方法
     */
    $.validator.addMethod("lasize", function (value, element, params) {
        var reg = /^-{0,1}(\d+)\.{0,1}(\d+)$/g;
        if ((reg.test(value) && (RegExp.$1 >= -90 && RegExp.$1 <= 90)) || value == "") {
            return true;
        }
        return false;
    }, "请输入正确纬度");

    /**
     * 手机号码验证
     */
    $.validator.addMethod("isMobile", function (value, element) {
        var mobile = /^1[3|4|5|6|7|8|9]\d{9}$/;
        return this.optional(element) || (mobile.test(value));
    }, "请正确填写您的手机号码");

    /**
     * 电话号码验证，格式010-12345678
     */
    $.validator.addMethod("isTel", function (value, element) {
        var tel = /^\d{3,4}-?\d{7,9}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的电话号码");

    /**
     * 联系电话(手机/电话皆可)验证
     */
    $.validator.addMethod("isPhone", function (value, element) {
        var mobile = /^1[3|4|5|6|7|8|9]\d{9}$/;
        var tel = /^\d{3,4}-?\d{7,9}$/;
        return this.optional(element) || (tel.test(value) || mobile.test(value));

    }, "请正确填写您的联系电话");

    /**
     * 邮政编码验证
     */
    $.validator.addMethod("isZipCode", function (value, element) {
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的邮政编码");

    /**
     * 检测非法字符
     */
    $.validator.addMethod("invalidChar", function (value, element) {
        var invalidChar = /[`~!@#$%^&*()_+<>?:{},.\/;[\]]/im;
        return this.optional(element) || !(invalidChar.test(value));
    }, "输入包含非法字符");

    /**
     * 判断浮点型
     */
    jQuery.validator.addMethod("isFloat", function (value, element) {
        return this.optional(element) || _isFloat(value);
    }, "只能包含数字、小数点等字符");
    jQuery.validator.addMethod("isFloatGtZero", function (value, element) {
        var b = _isFloat(value);
        if (b)
            return this.optional(element) || parseFloat(value) > 0;
        return this.optional(element) || false;
    }, "浮点数必须大于0");
    function _isFloat(v) {
        return /^[-\+]?\d+(\.\d+)?$/.test(v);
    }
    /*
     根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
     出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
     顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
     校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

     出生日期计算方法。
     15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
     2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
     下面是正则表达式:
     出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
     身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
     15位校验规则 6位地址编码+6位出生日期+3位顺序号
     18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

     校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
     公式(1)中：
     i----表示号码字符从由至左包括校验码在内的位置序号；
     ai----表示第i位置上的号码字符值；
     Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
     i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
     Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1
     */
    /**
     * 身份证号合法性验证。
     * <p>支持15位和18位身份证号；支持地址编码、出生日期、校验位验证。
     * @param code 被校验的身份证号码
     * @return <p>校验结果对象，包含两个属性：
     *      <li>pass : 校验结果，true成功，false失败
     *      <li>tip : 校验结果信息
     */
    function _validateIdCard(code) {
        var city = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江 ",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北 ",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏 ",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外 "
        };
        // 返回的验证消息
        var tip = "";
        // 验证结果
        var pass = true;
        if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
            tip = "身份证号格式错误";
            pass = false;
        }
        else if (!city[code.substr(0, 2)]) {
            tip = "身份证不合法";
            pass = false;
        }
        else {
            // 18位身份证需要验证最后一位校验位
            if (code.length == 18) {
                code = code.split('');
                // ∑(ai×Wi)(mod 11)
                // 加权因子
                var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                // 校验位
                var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++) {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if (parity[sum % 11] != code[17]) {
                    tip = "身份证不合法";
                    pass = false;
                }
            }
        }
        return {'pass': pass, 'tip': tip};
    }
})(jQuery);

/**
 * 表单验证函数，默认验证样式依赖于Bootstrap样式。
 *
 * @param formId 表单jquery对象
 * @param setting 验证规则
 * @returns 验证表单对象
 */
function validateForm(form, setting) {
    var defaultSetting = {
        errorElement: 'div',
        errorClass: 'help-block error-tip',
        focusInvalid: false, //焦点无效
        rules: {},
        messages: {},
        invalidHandler: function (event, validator) { //display error alert on form submit
            $('.alert-danger', $('.login-form')).show();
        },
        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },
        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
            $(e).remove();
        },
        errorPlacement: function (error, element) {
            if (element.is(':checkbox') || element.is(':radio')) {
                var controls = element.closest('div[class*="col-"]');
                if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else
                error.insertAfter(element.parent());
        },
        submitHandler: function (form) {
        },
        callback: function (data) {
        }
    };

    var mergeSetting = {};

    if (setting) {
        $.extend(true, mergeSetting, defaultSetting, setting);
    } else {
        $.extend(true, mergeSetting, defaultSetting);
    }

    return form.validate(mergeSetting);
}