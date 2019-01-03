/**//**  
 * @author ming  
 */  
$(document).ready(function(){       
         
/**//* 设置默认属性 */       
$.validator.setDefaults({       
    submitHandler: function(form) {    
        form.submit();    
    }       
});  

//找回密码登录名
jQuery.validator.addMethod("findpwdLogin", function(value, element) {
	var str = "用户名/手机号码/邮箱账号/市民卡号";
	if(str == value){
		return false;
	}else{
		return true;
	}
}, "请不要忘记输入登录名");  


//银行卡号验证，15-20位数字
jQuery.validator.addMethod("money", function(value, element) {
	return this.optional(element) || /^(0.)\d{2}$/.test(value);       
}, "请输入正确金额格式");  


//银行卡号验证，15-20位数字
jQuery.validator.addMethod("bankCardNum", function(value, element) {
	return this.optional(element) || /^\d{15,20}$/g.test(value);       
}, "请输入正确的银行卡号");  
//字符验证       
jQuery.validator.addMethod("idcardphoto", function(value, element) {
	var str = "dfh/smartcity/no_image.png";
    return this.optional(element) || value.indexOf(str)==-1;       
}, "请上传图片"); 


// 字符验证       
jQuery.validator.addMethod("stringCheck", function(value, element) {       
    return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
}, "只能包括中文字、英文字母、数字和下划线");  
 
  //市民卡号码验证
 jQuery.validator.addMethod("isCitizenCard", function(value, element) {       
    //var citizen = /^[a-zA-Z]\d{8}$/;   
    var citizen = /^[H][A-Za-z0-9]{8}$/;
    return this.optional(element) || (citizen.test(value));       
}, "请正确填写您的市民卡号"); 

 //市民卡号码验证
 jQuery.validator.addMethod("citizenCard", function(value, element) {       
    //var citizen = /^[a-zA-Z]\d{8}$/;
    var citizen = /^[H][A-Za-z0-9]{8}$/;
    if(this.optional(element) || (citizen.test(value))){
    	return false;
    }else{
    	return true;
    }
}, "您输入的是市民卡号，请选择市民卡注册");
  
// 字符验证       
jQuery.validator.addMethod("checkname", function(value, element) {       
    return this.optional(element) || /^[\u4e00-\u9fa5\\·]{2,8}$/.test(value);       
}, "只能输入名字"); 

/*
// 数字验证       
jQuery.validator.addMethod("number", function(value, element) {       
    return this.optional(element) || /^[1-9]\d*|0$/i.test(value);       
}, "只能输入数字");   
  */
//数字验证       
jQuery.validator.addMethod("number", function(value, element) {       
    return this.optional(element) || /^\d{6}$/i.test(value);       
}, "只能输入数字");  
// 中文字两个字节       
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {       
    var length = value.length;       
    for(var i = 0; i < value.length; i++){       
        if(value.charCodeAt(i) > 127){       
        length++;       
        }       
    }       
    return this.optional(element) || ( length >= param[0] && length <= param[1] );       
}, "请确保输入的值在3-15个字节之间(一个中文字算2个字节)");   
  
// 身份证号码验证       
jQuery.validator.addMethod("isIdCardNo", function(value, element) {       
    return this.optional(element) || isIdCardNo(value);       
}, "请正确输入您的身份证号码");    

/*     
// 手机号码验证       
jQuery.validator.addMethod("isMobile", function(value, element) {       
    var mobile = /^1[3|5|4|8][0-9]\d{8}$/;   
    return this.optional(element) || (mobile.test(value));       
}, "请正确填写您的手机号码");    
*/   
// 手机号码验证       
jQuery.validator.addMethod("isMobile", function(value, element) {       
    //var mobile = /^1[3|5|4|8][0-9]\d{8}$/;  
    var mobile=/^0?(130|131|132|133|134|135|136|137|138|139|147|150|151|152|155|156|153|157|158|159|180|181|182|183|185|187|188|189|186)\d{8}$/;
    return this.optional(element) || (mobile.test(value));       
}, "请正确填写您的手机号码");       
     
// 电话号码验证       
jQuery.validator.addMethod("isTel", function(value, element) {       
    var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678   
    return this.optional(element) || (tel.test(value));       
}, "请正确填写您的电话号码");   
  
// 联系电话(手机/电话皆可)验证   
jQuery.validator.addMethod("isPhone", function(value,element) {   
    var length = value.length;   
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
    var tel = /^\d{3,4}-?\d{7,9}$/;   
    return this.optional(element) || (tel.test(value) || mobile.test(value));   
  
}, "请正确填写您的联系电话");   
     
// 邮政编码验证       
jQuery.validator.addMethod("isZipCode", function(value, element) {       
    var tel = /^[0-9]{6}$/;       
    return this.optional(element) || (tel.test(value));       
}, "请正确填写您的邮政编码");    


// 字母和数字的验证
jQuery.validator.addMethod("chrnum", function(value, element) {
    var chrnum = /^([a-zA-Z0-9_-]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "您输入的密码包含非法字符哦。");

//字母和数字的验证
jQuery.validator.addMethod("openchar", function(value, element) {
    var chrnum = /^[a-zA-Z].*$/;
    return this.optional(element) || (chrnum.test(value));
}, "您输入的用户名要以字母开头。");
// 检测非法字符
jQuery.validator.addMethod("ffzf", function(value, element) {
    var feifa = /[`~!@#$%^&*()_+<>?:{},.\/;[\]]/im;
    return this.optional(element) || (feifa.test(value));
}, "您输入的密码包含非法字符哦");

// 由英文、数字、“-”、“_”组成，以字母开头
jQuery.validator.addMethod("username", function(value, element) {
    var username = /^[a-zA-Z]{1}[a-zA-Z0-9_-]+$/;
    return this.optional(element) || (username.test(value));
}, "只能输入英文、数字、“-”、“_”并以字母开头");

//邮箱验证
jQuery.validator.addMethod("email", function(value, element) { 
    var email = /^([a-zA-Z0-9_-]|[\.]|[\u4E00-\u9FA5]|[\uFE30-\uFFA0])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/; 
    return email.test($.trim(value)) || this.optional(element); 
}, "邮箱格式错误");

//身份证号码验证       
jQuery.validator.addMethod("isIdCardNo", function(value,element) {       
    return this.optional(element) || isIdCardNo(value);       
}, "请正确输入您的身份证号码"); 
function isIdCardNo(num) {
//	//console.log(param);
//	//zxm修改为了让有****的身份证验证通过
//	var userLevel = param[1]
//	if(userLevel == "02" || userLevel == "2" || userLevel == 2){//已登录，实名
//		return true;
//	}
//	//end   zxm
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set value
    for (i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }
    else {        //length is 15
        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {
            return false;
        }
    }
    return true;
}
function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false
    if (month < 1 || month > 12) return false
    return true
}

function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}
});