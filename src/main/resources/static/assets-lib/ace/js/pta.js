//项目专用证书项
var ProjectName = "遂宁市电子化招投标";

//颁发者CA的DN项
var arrayIssuerDN = new Array(
		"C=CN, O=四川省数字证书认证管理中心有限公司, OU=SCEB CA, CN=SCEB CA"
		, "C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEGB CA"
		, "C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEG CA"
		);
//SCEGB通用证书："C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEGB CA"
//SCEG机构证书："C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEG CA"
//SCEB专用证书:"C=CN, O=四川省数字证书认证管理中心有限公司, OU=SCEB CA, CN=SCEB CA"	
//四川CA内部员工颁发者DN："O=四川省数字证书认证管理中心有限公司, OU=技术部, CN=SCCA Employee CA"
//CFCA颁发者DN："C=CN, O=CFCA Operation CA2"
//天威诚信建信网项目颁发者DN："C=CN, O=\"iTruschina Co., Ltd.\", OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2007, CN=iTruschina CN Enterprise Individual Subscriber CA"



//  以下常量用于控制接口的行为和输出方式
var NO_CONTENT_DATA  = 1;
var USE_CONTENT_DATA =	2;
var USE_SIGNCONTENT =	3;
var PARAM_FILENAME  = 0x1000;
var PARAM_STRING    = 0x2000;
var PARAM_P7S	= 0x4000;
var PARAM_P7B	= 0x8000;

//签名时不弹出提示的话,需要设置这个值
var	FUNCOPT_NOALERT	= 0x80000000;

//加密的时候选择对称算法算法,可以设置这些值,如果不设置,默认会使用3DES
var	PTA_CALG_DES	= 0x10000;
var	PTA_CALG_3DES	= 0x20000;
var	PTA_CALG_AES	= 0x40000;
var	PTA_CALG_RC4	= 0x80000;
var	PTA_CALG_SM4	= 0x100000;

//  密钥用法选项
var KEY_USAGE_UNDEFINED		= 0x00;
var KEY_USAGE_ALL			= 0x01;
var KEY_USAGE_CRL_SIGN		= 0x02;
var KEY_USAGE_CERT_SIGN		= 0x04;
var KEY_USAGE_KEY_AGREEMENT	= 0x08;
var KEY_USAGE_DATA_ENCIPHERMENT	= 0x10;
var KEY_USAGE_KEY_ENCIPHERMENT	= 0x20;
var KEY_USAGE_NON_REPUDIATION	= 0x40;
var KEY_USAGE_DIGITAL_SIGNATURE	= 0x80;

var UNDEFINED		= 0x0;
var CRL_SIGN		= 0x02;
var CERT_SIGN		= 0x04;
var KEY_AGREEMENT	= 0x08;
var DATA_ENCIPHERMENT = 0x10;
var KEY_ENCIPHERMENT  = 0x20;
var NON_REPUDIATION	  = 0x40;
var DIGITAL_SIGNATURE = 0x80;

//  签名验签选项
var INPUT_BASE64		= 0x01; //SignMessage和VerifySignature输入参数
var INPUT_HEX			= 0x02; //SignMessage和VerifySignature输入参数
var OUTPUT_BASE64		= 0x04; //以BASE64编码pkcs7签名值
var OUTPUT_HEX			= 0x08; //以16进制字符串编码pkcs7签名值
var	INNER_CONTENT		= 0x10; //SignMessage...signData参数：是否包含原文
var	PLAINTEXT_UTF8		= 0x20; //原文UTF-8编码(SignMessage & VerifySignature函数)，缺省使用GB2312编码原文
var	MIN_CERTSTORE		= 0x40; //最小化证书链(仅包含签名证书)
var	HTML_SHOW			= 0x80; //接受HTML数据并渲染
var	MSG_BASE64			= 0x04; //VerifySignature输入参数
var MSG_HEX				= 0x08; //VerifySignature输入参数
var MSG_IMAGE			= 0x100; //VerifySignature输入参数
var ONLY_SIGNATURE = 0x200;//裸签名输入参数

//  证书导入导出选项
var MARK_CERT_TO_UNEXPORTABLE = 0x02;//标记证书为不可导出
var DELETE_CERT = 0x04;//删除该证书

//HASH算法选项
var PTA_CALG_MD5  = 1;
var PTA_CALG_SHA1 = 2;
var PTA_CALG_SM3  =	3;

//  全局变量
var iTrusPTA;

/*****************
PTA控件用到的方法
********************/
var PTA =
{
Init: function () {
        // 初始化控件
        if (document.getElementById('iTrusPTA_ie') != null) {
        	//alert("pta have");
            return true;
        }
				var obj = "<object id='iTrusPTA_ie' codebase='/download/itruscertctl.CAB#version=2,5,5,28' style='visibility: hidden' classid='clsid:F1F0506B-E2DC-4910-9CFC-4D7B18FEA5F9' viewastext></object>"
        var divContainer = document.createElement("div");
        divContainer.id = "div_iTrusPTA_ie";
        divContainer.style.display = 'none';
        divContainer.innerHTML = obj;
        try {
        	//alert("pta 潺潺t");
          document.body.appendChild(divContainer);
          iTrusPTA = document.getElementById("iTrusPTA_ie");
        }
        catch (e) {
          return false;
        }
        return true;
},

/**签名
 * @param plainText:
 *            原文
 * @param signCert
 *            用于签名的证书对象，可以使用GetSingleCertificate函数获得证书对象
 *            ，或者使用SelectSingleCertificate函数选择<select>中列出的证书
 * @param opt:
 *            签名参数
 * @return 成功: 返回签名值，失败: 返回""
 */
SignMessage:function (plainText,signCert,opt){
	var signedStr;
	var signCert;
	try {
		opt = typeof(opt) == "number" ? opt | OUTPUT_BASE64 : OUTPUT_BASE64;
		signedStr = signCert.SignMessage(plainText, opt);
	} catch (e) {
		if ((e.number == -2147483135) || e.number == -2146881278 // cancel
				|| e.number == -2146434962 // FT2001 PIN Login canceled
		) {
			return "";// User canceled
		} else if (e.number == -2146885621)
			alert("您不拥有证书“" + signCert.CommonName + "”的私钥，签名失败。");
		else
			alert("PTA模块发生错误\r\n错误号: " + e.number + "\r\n错误描述: "
					+ e.description);
		return "";
	}
	return signedStr;
},

/**验证签名
 * @param strToSign:
 *            原文
 * @param base64StrSignature:
 *            签名值
 * @return 成功: 返回签名证书对象，失败: 返回null
 */
VerifySignature:function (strToSign, base64StrSignature, opt){
	
		if (strToSign == "" || base64StrSignature == "")
		return;
	try {
		opt = typeof(opt) == "number" ? opt | INPUT_BASE64 : INPUT_BASE64; //签名值强制采用Base64编码
		var signCert = iTrusPTA.VerifySignature(strToSign, base64StrSignature, opt);
		alert("签名验证成功。签名者为“" + signCert.CommonName + "”。");
		return true;
	} catch (e) {
		if (e.number == -2146893818)
			alert("签名验证失败。\r\n签名值与原文不匹配，内容已遭篡改。");
		else
			alert("PTA模块发生错误\r\n错误号: " + e.number + "\r\n错误描述: " + e.description);
		return false;
	}
},

/**删除证书
 * @param cert 要删除的证书
 * @return void;
 */
removeCert:function (cert){
	try{
		cert.Remove();
		return true;
	}catch(e){
		//alert("error code:["+iTrusPTA.ErrorCode+"]");
		return false;
	}
},

/**导入证书
 * @param certPath 待导入的证书路径
 * @param password 待导入证书的密码
 * @param option 导入证书的选项 MARK_CERT_TO_UNEXPORTABLE；
 * @return void
 */
importCert:function (certPath,password,option){	
	try{
		iTrusPTA.ImportPKCS12(certPath,password,option);
		return true;
	}catch(e){
		//alert("error code:["+iTrusPTA.ErrorCode+"]");
		return false;
	}
},

/**导出证书
 * @param certPath 导出的证书路径
 * @param password 导出证书的密码
 * @param option 导出证书的选项 MARK_CERT_TO_UNEXPORTABLE | DELETE_CERT;
 * @return void
 */
exportCert:function (cert, option, password){
	try{
		var filePath = iTrusPTA.GetExportPath(cert.CommonName);
		var iRet = cert.ExportPKCS12(password,option,filePath);
		return true;
	}catch(e){
		//alert("code:[" + toHex(e.number) + "],message[" + e.message+"]");
		return false;
	}
},

//产生用户登录时随机数
getLogonData:function (){
	var logonData="LOGONDATA:"+Date()+"|"+Math.random().toString().substr(2);
	return logonData
},

/**
 * filterCertsEx 根据所设置条件过滤证书
 * @param arrayIssuerDN(optional) Array() or string，缺省为""，证书的颁发者字符串和字符串数组，支持多个CA时使用字符串数组
 * @param arraySerialNumber(optional)缺省为""，证书序列号（微软格式）
 * @param dateFlag(optional) 缺省为0，0表示所有证书，1表示处于有效期内的证书，2表示待更新证书，3表示未生效或已过期证书
 * @param keyUsage(option) 缺省为1;具体可以为密钥用法的各个值及相应组合
 * @return Array(), PTALib.Certificate
 */
filterCertsEx:function (arrayIssuerDN, subject, arraySerialNumber, dateFlag, keyUsage, weak) {
	var m_certs = new Array();
	var i = 0;var m=0;
	if (typeof(arrayIssuerDN) == "undefined") {
		arrayIssuerDN = new Array("");
	} else if (typeof(arrayIssuerDN) == "string") {
		arrayIssuerDN = new Array(arrayIssuerDN);
	}
	if (typeof(arraySerialNumber) == "undefined"){
		arraySerialNumber = new Array("");
	} else if (typeof(arraySerialNumber) == "string") {
		arraySerialNumber = new Array(arraySerialNumber);
	}
	if(typeof(subject) == "undefined")
		subject = "";
	for (i = 0; i < arrayIssuerDN.length; i++) {
		for(m = 0; m < arraySerialNumber.length; m++){
			var CertFilter = iTrusPTA.Filter;
			CertFilter.Clear();
//			alert(arrayIssuerDN[i].length);
			CertFilter.Issuer = arrayIssuerDN[i];
//			alert(arraySerialNumber[m].length);

			var serialNumber = arraySerialNumber[m];
			//alert(serialNumber);
			if ((serialNumber.length % 2) == 1)	
	  		serialNumber = "0" + serialNumber;
			if(parseInt(serialNumber.substr(0,1),16) > 7)	//如果传入的序列号(16进制)第一位大于7，则需要在前面加"00"
	  		serialNumber = "00" + serialNumber;
			serialNumber = serialNumber.toLowerCase();
			//alert(serialNumber);	

			CertFilter.SerialNumber = serialNumber;
//			alert(subject.length);
			CertFilter.Subject = subject;
//			alert("issuer:"+arrayIssuerDN[i]+":"+CertFilter.Issuer);
//			alert("serial:"+arraySerialNumber[m]+":"+CertFilter.SerialNumber);
			var t_Certs = iTrusPTA.MyCertificates; // 临时变量
			var now = new Date();
			var t_count = parseInt(t_Certs.Count);
			if ( t_count> 0) { // 找到了证书
				for (var j = 1; j <= t_count; j++) {
					if(!containUsage(t_Certs.Item(j),keyUsage,weak))
						continue;
					var validFrom = eval(t_Certs.Item(j).ValidFrom);
					var validTo = eval(t_Certs.Item(j).ValidTo);
					switch (dateFlag) {
						case 0 :// 所有证书
							m_certs.push(t_Certs.Item(j));
							break;
						case 1 :// 处于有效期内的证书
							if (validFrom < now && now < validTo)
								m_certs.push(t_Certs.Item(j));
							break;
						case 2 :// 待更新证书
							if (JSDateAdd(validTo, -30) < now && now < validTo)
								m_certs.push(t_Certs.Item(j));
							break;
						case 3 :// 未生效或已过期证书
							if (now < validFrom || validTo < now)
								m_certs.push(t_Certs.Item(j));
							break;
						default :// 缺省当作所有证书处理
							m_certs.push(t_Certs.Item(j));
							break;
					}
				}
			}
		}
	}
	return m_certs;
},

/**
 * filterCerts 根据所设置条件过滤证书
 * 
 * @param arrayIssuerDN(optional)
 *            Array() or string，缺省为""，证书的颁发者字符串和字符串数组，支持多个CA时使用字符串数组
 * @param dateFlag(optional)
 *            缺省为0，0表示所有证书，1表示处于有效期内的证书，2表示待更新证书，3表示未生效或已过期证书
 * @param serialNumber(optional)
 *            缺省为""，证书序列号（微软格式）
 * @return Array(), PTALib.Certificate
 */
filterCerts:function (arrayIssuerDN, dateFlag, serialNumber) {
	var m_certs = new Array();
	var i = 0;
	if (typeof(arrayIssuerDN) == "undefined") {
		arrayIssuerDN = new Array("");
	} else if (typeof(arrayIssuerDN) == "string") {
		arrayIssuerDN = new Array(arrayIssuerDN);
	}
	if (typeof(serialNumber) == "undefined")
		serialNumber = "";

	//alert(serialNumber);
	if ((serialNumber.length % 2) == 1)	
	  serialNumber = "0" + serialNumber;
	if(parseInt(serialNumber.substr(0,1),16) > 7)	//如果传入的序列号(16进制)第一位大于7，则需要在前面加"00"
	  serialNumber = "00" + serialNumber;
	serialNumber = serialNumber.toLowerCase();
	//alert(serialNumber);	

	for (i = 0; i < arrayIssuerDN.length; i++){
		var CertFilter = iTrusPTA.Filter;
		CertFilter.Clear();
		CertFilter.Issuer = arrayIssuerDN[i];
		CertFilter.SerialNumber = serialNumber;
		var t_Certs = iTrusPTA.MyCertificates; //临时变量
		var now = new Date();
		if (parseInt(t_Certs.Count) > 0) { //找到了证书
			for (var j = 1; j <= parseInt(t_Certs.Count); j++) {
				var validFrom = new Date(t_Certs(j).validFrom);
				var validTo = new Date(t_Certs(j).validTo);
				switch (dateFlag){
   					case 0://所有证书
						m_certs.push(t_Certs(j));
						break;
					case 1://处于有效期内的证书
						//validFrom     validTo
						//          now
						if(validFrom < now && now < validTo)
							m_certs.push(t_Certs(j));
						break;
					case 2://待更新证书
						//validFrom     validTo-30     validTo
						//                         now
						if(JSDateAdd(validTo, -30) < now && now < validTo)
							m_certs.push(t_Certs(j));
						break;
					case 3://未生效或已过期证书
						//     validFrom     validTo
						// now                       now
						if(now < validFrom || validTo < now)
							m_certs.push(t_Certs(j));
						break;
					default://缺省当作所有证书处理
						m_certs.push(t_Certs(j));
						break;
				}
			}
		}
	}
	return m_certs;
},

/**判断该证书是否包含给出的密钥用法
 * @param cert  要判断的证书；
 * @param usage 要判断的密钥用法；
 * @param weak  判断的模式，当weak为true时，只要证书的
 *			密钥用法包含所给出用法的任意一个就返回true。否则需要包含所有的给出的用法,才返回true
 *
*/
containUsage:function (cert,usage,weak){
	var keyUsage=cert.KeyUsage;
	var flag = true;
	if(weak){
		if((keyUsage&usage)==0){
			flag=false;
		}
	}else{
		if (usage & KEY_USAGE_CRL_SIGN)
			flag= flag &&(keyUsage & KEY_USAGE_CRL_SIGN);
		if (usage & KEY_USAGE_CERT_SIGN)
			flag= flag &&(keyUsage & KEY_USAGE_CERT_SIGN)
		if (usage & KEY_USAGE_KEY_AGREEMENT)
			flag= flag &&(keyUsage & KEY_USAGE_KEY_AGREEMENT);
		if (usage & KEY_USAGE_DATA_ENCIPHERMENT)
			flag= flag &&(keyUsage & KEY_USAGE_DATA_ENCIPHERMENT);
		if (usage & KEY_USAGE_KEY_ENCIPHERMENT)
			flag= flag &&(keyUsage & KEY_USAGE_KEY_ENCIPHERMENT);
		if (usage & KEY_USAGE_NON_REPUDIATION)
			flag= flag &&(keyUsage & KEY_USAGE_NON_REPUDIATION);
		if (usage & KEY_USAGE_NON_REPUDIATION)
			flag= flag &&(keyUsage & KEY_USAGE_NON_REPUDIATION);
	}
	return flag;
},

/**
 * JSDateAdd Javascript 计算给定日期+天数
 * @param theDate:给定日期，Date类型
 * @param days:整型
 * @return 计算结果，Date类型
 */
JSDateAdd:function (theDate, days) {
	var dateValue = theDate.valueOf();
	dateValue += days * 1000 * 60 * 60 * 24;
	var newDate = new Date(dateValue);
	return newDate;
},

/**
 * JSDateDiffByDays Javascript 计算两个日期之间的间隔天数
 * @param date1:给定日期1，Date类型
 * @param date2:给定日期2，Date类型
 * @return 天数，整型
 */
JSDateDiffByDays:function (date1, date2) {
	var mill = date1.valueOf() - date2.valueOf();
	var millStr = new String(mill / 1000 / 60 / 60 / 24)
	return parseInt(millStr);
},

//根据颁发者和序列号过滤证书，如果存在，返回第一张证书，否则返回null
selectSingleCert:function (issuer,serial){
	var filter=iTrusPTA.Filter;
	filter.Clear();
	if(issuer.length>0)
		filter.Issuer=issuer;
	if(serial.length>0)
		filter.SerialNumber=serial;
	if(iTrusPTA.MyCertificates.Count==0){
		alert("未找到指定的数字证书");
		return null;
	}
	return iTrusPTA.MyCertificates.Item(1);
},

//将数据转化为十六进制格式
toHex:function (number){
	number = number >>> 0;
	return number.toString(16);
},

//Base64Encode
Base64Encode:function (str) {
	var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
	var encoded = [];
	var c = 0;
	while (c < str.length) {
		var b0 = str.charCodeAt(c++);
		var b1 = str.charCodeAt(c++);
		var b2 = str.charCodeAt(c++);
		var buf = (b0 << 16) + ((b1 || 0) << 8) + (b2 || 0);
		var i0 = (buf & (63 << 18)) >> 18;
		var i1 = (buf & (63 << 12)) >> 12;
		var i2 = isNaN(b1) ? 64 : (buf & (63 << 6)) >> 6;
		var i3 = isNaN(b2) ? 64 : (buf & 63);
		encoded[encoded.length] = chars.charAt(i0);
		encoded[encoded.length] = chars.charAt(i1);
		encoded[encoded.length] = chars.charAt(i2);
		encoded[encoded.length] = chars.charAt(i3);
	}
	return encoded.join('');
},

//Base64Decode
Base64Decode:function (str) {
	var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
	var invalid = {
		strlen: (str.length % 4 != 0),
		chars:  new RegExp('[^' + chars + ']').test(str),
		equals: (/=/.test(str) && (/=[^=]/.test(str) || /={3}/.test(str)))
	};
	if (invalid.strlen || invalid.chars || invalid.equals)
		alert('Invalid base64 data');
	var decoded = [];
	var c = 0;
	while (c < str.length) {
		var i0 = chars.indexOf(str.charAt(c++));
		var i1 = chars.indexOf(str.charAt(c++));
		var i2 = chars.indexOf(str.charAt(c++));
		var i3 = chars.indexOf(str.charAt(c++));
		var buf = (i0 << 18) + (i1 << 12) + ((i2 & 63) << 6) + (i3 & 63);
		var b0 = (buf & (255 << 16)) >> 16;
		var b1 = (i2 == 64) ? -1 : (buf & (255 << 8)) >> 8;
		var b2 = (i3 == 64) ? -1 : (buf & 255);
		decoded[decoded.length] = String.fromCharCode(b0);
		if (b1 >= 0) decoded[decoded.length] = String.fromCharCode(b1);
		if (b2 >= 0) decoded[decoded.length] = String.fromCharCode(b2);
	}
	return decoded.join('');
},

/**
 * filterCertsByItem  根据特定主题项过滤证书 
 * 
 * @param arrayCerts
 *            证书对象数组
 * @param IssuerDN
 *            颁发者完整串
 * @param SubjectItem
 *            特定主题项
 * @return 过滤后的证书对象数组
 */
filterCertsByItem:function (arrayCerts, IssuerDN, SubjectItem) {
  if ( (arrayCerts.length <= 0) || (IssuerDN.length <= 0) ) { 
    return arrayCerts; 
  }
  for (var i = 0; i < arrayCerts.length; i++) {
  	var Issuer = arrayCerts[i].Issuer.replace("\0","");
    if ( (IssuerDN == "") || (IssuerDN == Issuer) ) { 
      var itemYes = false;
     	var items = arrayCerts[i].Subject.split(",");
     	for (var j = 0; j < items.length; j++){
       	if ( SubjectItem!="" && items[j].indexOf(SubjectItem)!=-1 )  {
         	itemYes = true;
         	break;
        }
       	if ( itemYes ) { break; } 
     	}
      if ( !itemYes ) {
        arrayCerts.splice(i,1);
        i--;
      }
    } 
  }
  return arrayCerts;
},

/**
 * filterCertsBySignKeyUsage  根据签名密钥用法过滤证书  （仅显示出签名证书）
 * 
 * @param arrayCerts
 *            证书对象数组
 * @return 过滤后的证书对象数组
 */
filterCertsBySignKeyUsage:function (arrayCerts) {
  for (var i = 0; i < arrayCerts.length; i++) {
  	if (arrayCerts[i].KeyUsage <128) {  //没有包含签名密钥用法   DIGITAL_SIGNATURE = 128
        arrayCerts.splice(i,1);
        i--;  		
  	}
  }
  return arrayCerts;
},

/**
 * filterCertsByKeyUsageSignature  根据签名密钥用法过滤证书  （仅显示出签名证书）
 * 
 * @param arrayCerts
 *            证书对象数组
 * @return 过滤后的证书对象数组
 */
filterCertsByKeyUsageSignature:function (arrayCerts) {
	var KeyUsageYes = true;
  for (var i = 0; i < arrayCerts.length; i++) {
  	KeyUsageYes = arrayCerts[i].KeyUsage & DIGITAL_SIGNATURE;
  	if (!KeyUsageYes) {
        arrayCerts.splice(i,1);
        i--;  		
  	}  	
  }
  return arrayCerts;
},

/**
 * filterCertsByKeyUsageEncipherment  根据加密密钥用法过滤证书  （仅显示出加密证书）
 * 
 * @param arrayCerts
 *            证书对象数组
 * @return 过滤后的证书对象数组
 */
filterCertsByKeyUsageEncipherment:function (arrayCerts) {
	var KeyUsageYes = true;
  for (var i = 0; i < arrayCerts.length; i++) {
  	KeyUsageYes = arrayCerts[i].KeyUsage & ( DATA_ENCIPHERMENT | KEY_ENCIPHERMENT );
  	if (!KeyUsageYes) {
        arrayCerts.splice(i,1);
        i--;  		
  	}  	
  }
  return arrayCerts;
},

/**
 * filterCertsByKeyUsage  根据密钥用法过滤证书  （仅显示出匹配密钥用法的证书）
 * 
 * @param arrayCerts
 *            证书对象数组
 * @return 过滤后的证书对象数组
 */
filterCertsByKeyUsage:function (arrayCerts, KeyUsage) {
  var KeyUsageYes = true;
  for (var i = 0; i < arrayCerts.length; i++) {
  	KeyUsageYes = arrayCerts[i].KeyUsage & KeyUsage;
  	if (!KeyUsageYes) {
        arrayCerts.splice(i,1);
        i--;  		
  	}
  }
  return arrayCerts;
},

/**
 * getCertPublicKey 获取证书公钥
 * 
 * @param base64Cert:
 *           BASE64编码的公钥证书
 * @return 成功: 返回证书公钥，失败: 返回null
 */
getCertPublicKey:function (base64Cert) { 
	var CAPICOM_ENCODE_BINARY = 1;
	var CertPublicKey = null;  
	var Certificate = new ActiveXObject("CAPICOM.Certificate");
	if (!Certificate)   
	{   
		alert("Cannot create CAPICOM.Certificate object");
		return null;   
	}   
	Certificate.Import(base64Cert);
	CertPublicKey = Certificate.PublicKey().EncodedKey.Format(false);   
	delete Certificate;
	return CertPublicKey;   
},

/**
 * getSubjectKeyIdentifier 获取（计算出）主题密钥标识符
 * 
 * @param base64Cert:
 *           BASE64编码的公钥证书
 * @return 成功: 返回主题密钥标识符，失败: 返回null
 */
getSubjectKeyIdentifier:function (base64Cert) {
	var CAPICOM_ENCODE_BINARY = 1;
	var CAPICOM_HASH_ALGORITHM_SHA1 = 0;
	var SubjectKeyIdentifier = null;
	var Certificate = new ActiveXObject("CAPICOM.Certificate");
	if (!Certificate)   
	{   
		alert("Cannot create CAPICOM.Certificate object");
		return null;   
	}   
	Certificate.Import(base64Cert);
	var HashedData = new ActiveXObject("CAPICOM.HashedData");		
	if (!HashedData)   
	{   
		alert("Cannot create CAPICOM.HashedData object");
		return null;   
	}   
	HashedData.Algorithm = CAPICOM_HASH_ALGORITHM_SHA1;
	HashedData.Hash(Certificate.PublicKey().EncodedKey.Value(CAPICOM_ENCODE_BINARY));
	SubjectKeyIdentifier = HashedData.Value;
	delete HashedData;
	delete Certificate;
	return SubjectKeyIdentifier;
},

/**
 * getCertSubjectItem  根据证书主题项中的关键字（键），获取后面的值（,结束）
 * 
 * @param CertSubjectName
 *            证书主题完整DN字串
 * @param ItemKey
 *            证书主题项中的关键字（键）
 * @return 后面的值
 */
getCertSubjectItem:function (CertSubjectName,ItemKey) {
	var CertSubjectItem = "";
	var items = CertSubjectName.split(",");
  for (var i = 0; i < items.length; i++) {
    if ( items[i].indexOf(ItemKey) >= 0 )  {
			CertSubjectItem = items[i].substring(items[i].indexOf(ItemKey) + ItemKey.length);
			CertSubjectItem = CertSubjectItem.replace("\0","");
      break;
    }
  }
	return CertSubjectItem;
},

/**
 * getSubjectFromBase64Cert 从Base64编码公钥证书中获取证书主题
 * 
 * @param base64Cert:
 *           BASE64编码的公钥证书
 * @return 成功: 返回证书主题串，失败: 返回null
 */
getSubjectFromBase64Cert:function (base64Cert) { 
	var CertSubjectName = "";
	var Certificate = new ActiveXObject("PTA.Certificate");
	if (!Certificate)   
	{   
		alert("Cannot create PTA.Certificate object");
		return null;   
	}
	try{
		Certificate.CreateFromStream(base64Cert);
		CertSubjectName = Certificate.Subject.replace("\0","");
	}catch(e){
		alert("PTA.Certificate CreateFromStream 发生错误\r\n错误号: " + e.number + "\r\n错误描述: " + e.description);
	} 
	delete Certificate;
	return CertSubjectName;   
},

/** 
 * getCommonNameFromBase64Cert 从Base64编码公钥证书中获取证书CN
 * 
 * @param base64Cert:
 *           BASE64编码的公钥证书
 * @return 成功: 返回证书CN，失败: 返回null
 */
getCommonNameFromBase64Cert:function (base64Cert) { 
	var CertCommonName = "";
	var Certificate = new ActiveXObject("PTA.Certificate");
	if (!Certificate)   
	{   
		alert("Cannot create PTA.Certificate object");
		return null;   
	}
	try{
		Certificate.CreateFromStream(base64Cert);   
		CertCommonName = Certificate.CommonName.replace("\0","");
	}catch(e){
		alert("PTA.Certificate CreateFromStream 发生错误\r\n错误号: " + e.number + "\r\n错误描述: " + e.description);
	} 
	delete Certificate;
	return CertCommonName;
},

/**文件签名（签名值为字符串）
 * @param srcFileName 待签名的文件名
 * @param cert 签名使用的私钥对应的证书
 * @param innerContent 是否包含原文 
 * @param noConfirm 是否需要用户确认 
 * @return 签名后的数据BASE64字符串，签名失败返回null
 */
SignFile:function (srcFileName,cert,innerContent,noConfirm){
	var signopt=0;
	var signedData = null;
	signopt=OUTPUT_BASE64;
	if (noConfirm) {
		signopt|=FUNCOPT_NOALERT;
	}
	if(innerContent){
	signopt|=INNER_CONTENT;
	}
	signopt|=MIN_CERTSTORE;//在签名中，最小化证书列，可以减少签名值长度
//signopt|=HTML_SHOW;		//弹出的确认框，采用IE引擎渲染样式
//signopt|=PLAINTEXT_UTF8;//如果签名值是UTF-8编码，需要加上此行，默认UTF-8;
//signopt|=512;
	try{
			signedData = cert.SignFile(srcFileName,signopt);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return signedData;
},

/**文件签名（签名值为文件）
 * @param srcFileName 待签名的文件名
 * @param signedFileName 签名结果输出的文件名
 * @param cert 签名使用的私钥对应的证书
 * @param innerContent 是否包含原文 
 * @param noConfirm 是否需要用户确认 
 * @return 签名成功返回 true ，签名失败返回 false
 */
SignFileEx:function (srcFileName,signedFileName,cert,innerContent,noConfirm){
	var signopt=0;
	var Result = false;
	signopt=OUTPUT_BASE64;
	if (noConfirm) {
		signopt|=FUNCOPT_NOALERT;
	}
	if(innerContent){
	signopt|=INNER_CONTENT;
	}
	signopt|=MIN_CERTSTORE;//在签名中，最小化证书列，可以减少签名值长度
//signopt|=HTML_SHOW;		//弹出的确认框，采用IE引擎渲染样式
//signopt|=PLAINTEXT_UTF8;//如果签名值是UTF-8编码，需要加上此行，默认UTF-8;
//signopt|=512;
	try{
			Result = cert.SignFileEx(srcFileName,signedFileName,signopt);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**P7S文件签名（输出包含原文的P7S文件）
 * @param srcFileName 待签名的文件名
 * @param signedFileName 签名结果输出的文件名
 * @param cert 签名使用的私钥对应的证书
 * @param noConfirm 是否需要用户确认 
 * @return 签名成功返回 true ，签名失败返回 false
 */
SignFileP7S:function (srcFileName,signedFileName,cert,noConfirm){
	var signopt=0;
	var Result = false;
	signopt|=INNER_CONTENT;
	if (noConfirm) {
		signopt|=FUNCOPT_NOALERT;
	}
	signopt|=MIN_CERTSTORE;//在签名中，最小化证书列，可以减少签名值长度
//signopt|=HTML_SHOW;		//弹出的确认框，采用IE引擎渲染样式
//signopt|=PLAINTEXT_UTF8;//如果签名值是UTF-8编码，需要加上此行，默认UTF-8;
//signopt|=512;
	try{
			Result = cert.SignFileEx(srcFileName,signedFileName,signopt);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**验证文件签名（签名值为字符串）
 * @param signedData 签名结果BASE64编码
 * @param srcFileName 原文件名，若为null,说明签名值包含原文件
 * @return 验证成功返回 true，验证失败返回 false
 */
VerifyFileSignature:function (signedData,srcFileName){
	var Result = false;
	var InSignFlag = PARAM_STRING | INPUT_BASE64;
	if (srcFileName!=null){	
		var InContentFlag = PARAM_FILENAME;		
		var FuncFlag = USE_CONTENT_DATA;
	}else{
		var srcFileName="";
		var InContentFlag = NO_CONTENT_DATA;		
		var FuncFlag = USE_SIGNCONTENT;
	}
	try{
		Result = iTrusPTA.VerifySignatureEx(srcFileName, InContentFlag, signedData, InSignFlag, FuncFlag);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**验证文件签名（签名值为文件）
 * @param signedFileName 签名结果文件名
 * @param srcFileName 原文件名，若为null,说明签名值包含原文件
 * @return 验证成功返回 true，验证失败返回 false
 */
VerifyFileSignatureEx:function (signedFileName,srcFileName){
	var Result = false;
	var InSignFlag = PARAM_FILENAME | INPUT_BASE64;
	if (srcFileName!=null){	
		var InContentFlag = PARAM_FILENAME;		
		var FuncFlag = USE_CONTENT_DATA;
	}else{
		var srcFileName="";
		var InContentFlag = NO_CONTENT_DATA;		
		var FuncFlag = USE_SIGNCONTENT;
	}
	try{
		Result = iTrusPTA.VerifySignatureEx(srcFileName, InContentFlag, signedFileName, InSignFlag, FuncFlag);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**验证P7S的文件签名（包含签名原文文件）
 * @param signedFileName 签名结果P7S文件名
 * @return 验证成功返回 true，验证失败返回 false
 */
VerifyFileP7S:function (signedFileName){
	var Result = false;
	var InSignFlag = PARAM_FILENAME|PARAM_P7S;
	var srcFileName="";
	var InContentFlag = NO_CONTENT_DATA;		
	var FuncFlag = USE_SIGNCONTENT;
	try{
		Result = iTrusPTA.VerifySignatureEx(srcFileName, InContentFlag, signedFileName, InSignFlag, FuncFlag);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**从签名值中获取签名证书
 * @param signedData 签名值（字符串消息或签名文件名）
 * @param signedDataType 签名值类型，"File"表示为签名文件,其他表示为字符串消息
 * @return 签名证书对象
 */
GetCertFromSign:function (signedData,signedDataType){
	var OPT = INPUT_BASE64;
	var Cert = null;
	if (signedDataType=="File"){
	  try{
			var Certs = iTrusPTA.GetCertsFromSignFile(signedData, OPT);
		}catch(e){
			alert(e.message+"["+e.number+"]");
		}
		if (Certs.Count >= 1) {
			Cert = Certs.Item(1);	
		};
	}else{
		try{
			var Certs = iTrusPTA.GetCertsFromSignStr(signedData, OPT);
		}catch(e){
			alert(e.message+"["+e.number+"]");
		}
		if (Certs.Count >= 1) {
			Cert = Certs.Item(1);	
		};
	}
	return Cert;
},

/**从签名值中获取原文内容
 * @param signedData 签名值（字符串消息或签名文件名）
 * @param signedDataType 签名值类型，"File"表示为签名文件,其他表示为字符串消息
 * @param outFileName 输出原文的文件名，若为null,表示原文不输出到外部文件
 * @return 若输出到文件，返回是否成功，true/false; 若不输出到外部文件，则返回原文消息
 */
GetContentFromSign:function (signedData,signedDataType,outFileName){
	var InSignFlag=INPUT_BASE64;
	var Result = null;
	if (signedDataType=="File"){
		InSignFlag|=PARAM_FILENAME;
	}else{
		InSignFlag|=PARAM_STRING;
	};		
	if (outFileName!=null){
		var OutDataFlag = PARAM_FILENAME;
		var OutData = outFileName;	
		Result = false;
		try{
			iTrusPTA.GetContentFromSignEx(signedData, InSignFlag, OutData, OutDataFlag);	
			Result = true;
		}catch(e){
			alert(e.message+"["+e.number+"]");
		}
	}else{
		var OutDataFlag = 0;
		var OutData = "";	
		try{
			Result = iTrusPTA.GetContentFromSignEx(signedData, InSignFlag, OutData, OutDataFlag);	
		}catch(e){
			alert(e.message+"["+e.number+"]");
		}
	};
	return Result;
},

/**从P7S签名值中获取签名证书
 * @param signedData 签名值（P7S签名文件名）
 * @return 签名证书对象
 */
GetCertFromP7S:function (signedData){
	var OPT = PARAM_P7S;
	var Cert = null;
	try{
		var Certs = iTrusPTA.GetCertsFromSignFile(signedData, OPT);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	if (Certs.Count >= 1) {
		Cert = Certs.Item(1);	
	};
	return Cert;
},

/**从P7S签名值中获取原文内容
 * @param signedData 签名值（P7S签名文件名）
 * @param outFileName 输出原文的文件名
 * @return 签名证书对象
 */
GetContentFromP7S:function (signedData,outFileName){
	var InSignFlag=PARAM_FILENAME|PARAM_P7S;
	var Result = false;
	var OutDataFlag = PARAM_FILENAME;
	var OutData = outFileName;	
	try{
		iTrusPTA.GetContentFromSignEx(signedData, InSignFlag, OutData, OutDataFlag);
		Result = true;	
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**证书登录签名
 * @param plainData 待签名的登录随机数（服务器产生的）
 * @param cert 签名使用的私钥对应的证书
 * @return 签名后的数据BASE64字符串，签名失败返回null
 */
signLogonData:function (plainData,cert){
	var signopt=0;
	var signedData = null;
	signopt=OUTPUT_BASE64;
	signopt|=MIN_CERTSTORE;
	try{
			if (plainData.indexOf("LOGONDATA:") == -1){
				plainData = "LOGONDATA:" + plainData;
			}
			signedData = cert.SignLogonData(plainData,signopt);
	}catch(e){
        //alert(signedData);
		//alert(e.message+"["+e.number+"]");
	}
	return signedData;
},

/**字符串裸签名
 * @param plainData 待签名的字符串
 * @param cert 签名使用的私钥对应的证书
 * @param noConfirm 是否不需要用户确认 0=需要用户确认  1=不需要用户确认
 * @return 签名后的数据BASE64字符串，签名失败返回null
 */
SignStringOnlySignature:function (plainData,cert,noConfirm){
	var signopt=0;
	var signedData = null;
	signopt=OUTPUT_BASE64;
	if (noConfirm) {
		signopt|=FUNCOPT_NOALERT;
	}
	signopt|=ONLY_SIGNATURE;
//signopt|=HTML_SHOW;		//弹出的确认框，采用IE引擎渲染样式
//signopt|=PLAINTEXT_UTF8;//如果签名值是UTF-8编码，需要加上此行，默认UTF-8;
//signopt|=512;
	try{			
			signedData = cert.SignMessage(plainData,signopt);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return signedData;
},

/**字符串P7签名
 * @param plainData 待签名的字符串
 * @param cert 签名使用的私钥对应的证书
 * @param innerContent 是否包含原文 
 * @param noConfirm 是否不需要用户确认 0=需要用户确认  1=不需要用户确认
 * @return 签名后的数据BASE64字符串，签名失败返回null
 */
SignString:function (plainData,cert,innerContent,noConfirm){
	var signopt=0;
	var signedData = null;
	signopt=OUTPUT_BASE64;
	if (noConfirm) {
		signopt|=FUNCOPT_NOALERT;
	}
	if(innerContent){
	signopt|=INNER_CONTENT;
	}
	signopt|=MIN_CERTSTORE;//在签名中，最小化证书列，可以减少签名值长度
//signopt|=HTML_SHOW;		//弹出的确认框，采用IE引擎渲染样式
//signopt|=PLAINTEXT_UTF8;//如果签名值是UTF-8编码，需要加上此行，默认UTF-8;
//signopt|=512;
	try{
			signedData = cert.SignMessage(plainData,signopt);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return signedData;
},

/**验证字符串P7签名
 * @param signedData 签名结果BASE64编码
 * @param plainData 原文字符串，若为null,说明签名值包含原文字符串
 * @return 验证成功返回 true，验证失败返回 false
 */
VerifyStringSignature:function (signedData,plainData){
	var Result = false;
	var InSignFlag = PARAM_STRING | INPUT_BASE64;
	if (plainData!=null){	
		var InContentFlag = PARAM_STRING;		
		var FuncFlag = USE_CONTENT_DATA;
	}else{
		var InContentFlag = NO_CONTENT_DATA;
		var FuncFlag = USE_SIGNCONTENT;	
		plainData = ""; 
	}
	try{
		Result = iTrusPTA.VerifySignatureEx(plainData, InContentFlag, signedData, InSignFlag, FuncFlag);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return Result;
},

/**获取消息（字符串）的HASH值
 * @param plainData 消息（字符串）
 * @param algorithmName HASH算法，支持算法名称："MD5" "SHA1" "SM3"
 * @param outFileName HASH值输出文件,若为null，不输出到文件
 * @return HASH值（十六进制）
 */
GetMessageHash:function (plainData,algorithmName,outFileName){
	var hashData = null;
	var InData = plainData;
	var InOPT  = PARAM_STRING;	 // 可取值 PARAM_FILENAME / PARAM_STRING / INPUT_BASE64
	var OutOPT = null;
	var OutParam = null;
	if (outFileName==null || outFileName==""){
		OutParam = ""; //"F:\\Hash.txt";	 // 当OutOPT & PARAM_FILENAME时，OutParam会当作文件名处理，Hash结果会被写入文件
		OutOPT = OUTPUT_HEX; //PARAM_FILENAME | OUTPUT_HEX;	 // 如果设置 PARAM_FILENAME，见上。
	}else{
		OutParam = outFileName; //"F:\\Hash.txt";	 // 当OutOPT & PARAM_FILENAME时，OutParam会当作文件名处理，Hash结果会被写入文件
		OutOPT = PARAM_FILENAME|OUTPUT_HEX; //PARAM_FILENAME | OUTPUT_HEX;	 // 如果设置 PARAM_FILENAME，见上。
	}
	if (algorithmName=="MD5"){
		var Algid = PTA_CALG_MD5;
	}else if (algorithmName=="SHA1"){
		var Algid = PTA_CALG_SHA1;
	}else if (algorithmName=="SM3"){
		var Algid = PTA_CALG_SM3;
	}else {
		var Algid = PTA_CALG_SHA1;
	};
	try{
		hashData = iTrusPTA.HashData(InData, InOPT, OutParam, OutOPT, Algid);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return hashData;
},

/**获取文件的HASH值
 * @param srcFileName 原文件
 * @param algorithmName HASH算法，支持算法名称："MD5" "SHA1" "SM3"
 * @param destFileName HASH值输出文件,若为null，不输出到文件
 * @return HASH值（十六进制）
 */
GetFileHash:function (srcFileName,algorithmName,outFileName){
	var hashData = null;
	var InData = srcFileName;
	var InOPT  = PARAM_FILENAME;	 // 可取值 PARAM_FILENAME / PARAM_STRING / INPUT_BASE64
	var OutOPT = null;
	var OutParam = null;
	if (outFileName==null || outFileName==""){
		OutParam = ""; //"F:\\Hash.txt";	 // 当OutOPT & PARAM_FILENAME时，OutParam会当作文件名处理，Hash结果会被写入文件
		OutOPT = OUTPUT_HEX; //PARAM_FILENAME | OUTPUT_HEX;	 // 如果设置 PARAM_FILENAME，见上。
	}else{
		OutParam = outFileName; //"F:\\Hash.txt";	 // 当OutOPT & PARAM_FILENAME时，OutParam会当作文件名处理，Hash结果会被写入文件
		OutOPT = PARAM_FILENAME|OUTPUT_HEX; //PARAM_FILENAME | OUTPUT_HEX;	 // 如果设置 PARAM_FILENAME，见上。
	}
	if (algorithmName=="MD5"){
		var Algid = PTA_CALG_MD5;
	}else if (algorithmName=="SHA1"){
		var Algid = PTA_CALG_SHA1;
	}else if (algorithmName=="SM3"){
		var Algid = PTA_CALG_SM3;
	}else {
		var Algid = PTA_CALG_SHA1;
	};
	try{
		hashData = iTrusPTA.HashData(InData, InOPT, OutParam, OutOPT, Algid);
	}catch(e){
		alert(e.message+"["+e.number+"]");
	}
	return hashData;
},

/**对消息（字符串）加密
 * @param msg 原消息（字符串）
 * @param Issuer 加密证书的颁发者DN
 * @param Subject 加密证书的主题DN
 * @return 加密之后的密文（BASE64）
 */
encryptMessage:function (msg,Issuer,Subject){
	var encryptData = null;
	var CertFilter = iTrusPTA.Filter;
	CertFilter.Clear();
	CertFilter.Issuer = Issuer;
	CertFilter.Subject = Subject;
	var certs = iTrusPTA.MyCertificates;
  for (var i = 1; i <= certs.Count; i++) {
  	if (!(certs.Item(i).KeyUsage & (DATA_ENCIPHERMENT|KEY_ENCIPHERMENT))) {
    	certs.Remove(i-1);
      i--;  		
  	}  	
  }
	try{
		encryptData = certs.EncryptMessage(msg,OUTPUT_BASE64|PTA_CALG_RC4);//加密的时候选择对称算法算法,如果不设置,默认会使用 PTA_CALG_3DES  PTA_CALG_DES/PTA_CALG_3DES/PTA_CALG_AES/PTA_CALG_RC4/PTA_CALG_SM4
	}catch(e){
		alert( "加密消息失败\n错误代码：0x" + (e.number>0?e.number: 0x100000000+e.number).toString(16) + "\n错误描述：" + e.description);
	}
	return encryptData;
},

/**对消息（字符串）加密
 * @param msg 原消息（字符串）
 * @param base64Cert base64加密证书
 * @return 加密之后的密文（BASE64）
 */
encryptMessageUseBase64Cert:function (msg,base64Cert){
	this.Init();
	alert("iTrusPTA=["+iTrusPTA+"]");
	var encryptData = null;
	var CertFilter = iTrusPTA.Filter;
	alert(124);
	CertFilter.Clear();
	var Certificates = iTrusPTA.GetEmptyCertificates();	
	var Certificate = new ActiveXObject("PTA.Certificate");
	if (!Certificate)   
	{   
		alert("Cannot create PTA.Certificate object");
		return null;   
	}
	try{
		Certificate.CreateFromStream(base64Cert);
	}catch(e){
		alert("PTA.Certificate CreateFromStream 发生错误\r\n错误号: " + e.number + "\r\n错误描述: " + e.description);
		return null;   
	} 
	Certificates.Add(Certificate);
	delete Certificate;
	try{
		encryptData = Certificates.EncryptMessage(msg,OUTPUT_BASE64|PTA_CALG_RC4);//加密的时候选择对称算法算法,如果不设置,默认会使用 PTA_CALG_3DES  PTA_CALG_DES/PTA_CALG_3DES/PTA_CALG_AES/PTA_CALG_RC4/PTA_CALG_SM4
	}catch(e){
		alert( "加密消息失败\n错误代码：0x" + (e.number>0?e.number: 0x100000000+e.number).toString(16) + "\n错误描述：" + e.description);
	}
	return encryptData;
},

/**对消息（字符串）解密
 * @param msg 密文
 * @param cert 解密证书，若为null,则用原加密证书解密（根据序列号找原加密证书）；若传入证书,则用指定证书解密
 * @return 成功返回：解密之后的明文；失败返回 0
 */
decryptMessage:function (msg,cert){
	var Result = null;
	if (cert==null){  // 使用默认证书解密
		try{
			Result = iTrusPTA.DecryptMessage(msg,INPUT_BASE64);
		}catch(e){
			var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
			if( errcode==80083004 ){ //0x80083004 意为没有找到原加密证书，后续可调用用户选择的证书解密
				Result = 1;
			}else{
				Result = 0;
				alert( "使用默认证书解密消息失败\n错误代码：0x" + errcode + "\n错误描述：" + e.description);
			}		
		}		
	}else{ // 使用用户选择的证书解密
		try{
			Result = cert.DecryptMessage(msg,INPUT_BASE64);
		}catch(e){
			var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
			Result = 0;
			alert( "使用指定证书解密失败\n错误代码：0x" + errcode + "\n错误描述：" + e.description);					
		}	
	}
	return Result;	
},
/**选择证书对消息（字符串）解密
 * @param msg 密文
 * @return 成功返回：解密之后的明文；失败返回 null
 */
decryptMessageEx:function (msg){
	var Result = null;
	var t_CertFilter = iTrusPTA.Filter;
	t_CertFilter.Clear();
	t_CertFilter.Issuer = arrayIssuerDN[0];
	var t_Certs = iTrusPTA.MyCertificates;
	var Certificates = iTrusPTA.GetEmptyCertificates(); 
	var t_count = parseInt(t_Certs.Count);
	for (var j=1;j<=t_count;j++) {
		if(this.containUsage(t_Certs.Item(j),DATA_ENCIPHERMENT | KEY_ENCIPHERMENT,true)){
			Certificates.Add(t_Certs.Item(j));
		}
	}
	t_CertFilter.Clear();
	t_CertFilter.Issuer = arrayIssuerDN[1];
	t_Certs = iTrusPTA.MyCertificates;
	t_count = parseInt(t_Certs.Count);
	for (var j=1;j<=t_count;j++) {
		if(this.containUsage(t_Certs.Item(j),DATA_ENCIPHERMENT | KEY_ENCIPHERMENT,true)){
			Certificates.Add(t_Certs.Item(j));
		}
	}
	t_CertFilter.Clear();
	t_CertFilter.Issuer = arrayIssuerDN[2];
	t_Certs = iTrusPTA.MyCertificates;
	t_count = parseInt(t_Certs.Count);
	for (var j=1;j<=t_count;j++) {
		if(this.containUsage(t_Certs.Item(j),DATA_ENCIPHERMENT | KEY_ENCIPHERMENT,true)){
			Certificates.Add(t_Certs.Item(j));
		}
	}
	if (Certificates.Count<1) return Result;
	var index = 0;
	try{
		// 用户成功选择后会返回序号，从1开始
		// 失败时有异常，且返回0
		index = Certificates.SelCertByGUI(0);
	}catch(e){
		var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
		//alert(errcode);
		if( errcode=="80081001"){
			// 传入的证书集合为空
		}else if( errcode=="80081002"){
			// 用户取消或没有选择
		}
		index = 0;
	}
	if (index < 1) return Result;
	var t_cert = Certificates.Item(index);
	try{
		Result = t_cert.DecryptMessage(msg,INPUT_BASE64);
	}catch(e){
		var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
		alert( "使用选择的证书解密失败\n错误代码：0x" + errcode + "\n错误描述：" + e.description);					
	}	
	return Result;	
},
selectCert:function (KeyUsage){
	var Result = null;
	var t_CertFilter = iTrusPTA.Filter;
	t_CertFilter.Clear();
	t_CertFilter.Issuer = arrayIssuerDN[0];
	var t_Certs = iTrusPTA.MyCertificates;
	var Certificates = iTrusPTA.GetEmptyCertificates(); 
	var t_count = parseInt(t_Certs.Count);
	for (var j=1;j<=t_count;j++) {
		if(this.containUsage(t_Certs.Item(j),KeyUsage,true)){			
			var Subject = t_Certs.Item(j).Subject.replace("\0","");
    	if (Subject.indexOf("OU="+ProjectName)!=-1){
    		Certificates.Add(t_Certs.Item(j));
      }			
		}
	}
	t_CertFilter.Clear();
	t_CertFilter.Issuer = arrayIssuerDN[1];
	t_Certs = iTrusPTA.MyCertificates;
	t_count = parseInt(t_Certs.Count);
	for (var j=1;j<=t_count;j++) {
		if(this.containUsage(t_Certs.Item(j),KeyUsage,true)){
			Certificates.Add(t_Certs.Item(j));
		}
	}
	t_CertFilter.Clear();
	t_CertFilter.Issuer = arrayIssuerDN[2];
	t_Certs = iTrusPTA.MyCertificates;
	t_count = parseInt(t_Certs.Count);
	for (var j=1;j<=t_count;j++) {
		if(this.containUsage(t_Certs.Item(j),KeyUsage,true)){
			Certificates.Add(t_Certs.Item(j));
		}
	}
	if (Certificates.Count<1) return Result;
	var index = 0;
	try{
		// 用户成功选择后会返回序号，从1开始
		// 失败时有异常，且返回0
		index = Certificates.SelCertByGUI(0);
	}catch(e){
		var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
		//alert(errcode);
		if( errcode=="80081001"){
			// 传入的证书集合为空
		}else if( errcode=="80081002"){
			// 用户取消或没有选择
		}
		index = 0;
	}
	if (index < 1) return Result;
	var t_cert = Certificates.Item(index);
	return t_cert;	
},
/**通过CAPICOM选择证书对消息（字符串）解密
 * @param msg 密文
 * @return 成功返回：解密之后的明文；失败返回 null
 */
decryptMessageBySelectCert:function (msg){
	var Result = null;
	var MyStore = new ActiveXObject("CAPICOM.Store");
	var MyStoreCerts;
  var CAPICOM_CURRENT_USER_STORE = 0x02;
  var CAPICOM_MY_STORE = "MY";
  var CAPICOM_STORE_OPEN_READ_ONLY = 0x00;
  var CAPICOM_CERTIFICATE_FIND_KEY_USAGE = 0x0C;
	var CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE = 0x80;
  var CAPICOM_KEY_ENCIPHERMENT_KEY_USAGE = 0x20;
  //var CAPICOM_CERTIFICATE_FIND_SUBJECT_NAME = 0x01;
  //var CAPICOM_CERTIFICATE_FIND_ISSUER_NAME = 0x02;
  var CAPICOM_E_CANCELLED = -2138568446;
  try{
  	MyStore.Open(CAPICOM_CURRENT_USER_STORE,CAPICOM_MY_STORE,CAPICOM_STORE_OPEN_READ_ONLY);
	}
	catch (e){
		if (e.number != CAPICOM_E_CANCELLED){
	   	alert("An error occurred while opening your personal certificate store, aborting");
			return Result;
		}
	}
	MyStoreCerts = MyStore.Certificates.Find(CAPICOM_CERTIFICATE_FIND_KEY_USAGE, CAPICOM_KEY_ENCIPHERMENT_KEY_USAGE);
  var SelectedCerts = MyStoreCerts.Select();//弹出证书选择框
  var Cert = SelectedCerts(1); 
  alert(Cert.SubjectName);
  var SelectedCertSerialNumber = Cert.SerialNumber;
	MyStoreCerts = null;
	MyStore = null;
	var t_CertFilter = iTrusPTA.Filter;
	t_CertFilter.Clear();
	t_CertFilter.SerialNumber = SelectedCertSerialNumber;
	var t_Certs = iTrusPTA.MyCertificates;	
	var t_count = parseInt(t_Certs.Count);
	if (t_count<1) return Result;
	try{
		Result = t_Certs.Item(1).DecryptMessage(msg,INPUT_BASE64);
	}catch(e){
		var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
		alert( "使用选择的证书解密失败\n错误代码：0x" + errcode + "\n错误描述：" + e.description);					
	}	
	return Result;	
},
/**对文件加密
 * @param srcFile 原文件名
 * @param encFile 加密后输出的文件名
 * @param Issuer 加密证书的颁发者DN
 * @param Subject 加密证书的主题DN
 * @return 加密成功返回 true，加密失败返回 false
 */
encryptFile:function (srcFile,encFile,Issuer,Subject){
	var Result = false;
	var CertFilter = iTrusPTA.Filter;
	CertFilter.Clear();
	CertFilter.Issuer = Issuer;
	CertFilter.Subject = Subject;
	var certs = iTrusPTA.MyCertificates;
  for (var i = 1; i <= certs.Count; i++) {
  	if (!(certs.Item(i).KeyUsage & (DATA_ENCIPHERMENT|KEY_ENCIPHERMENT))) {
    	certs.Remove(i-1);
      i--;  		
  	}  	
  }
	try{
		Result = certs.EncryptFileEx(srcFile,encFile,0); //OUTPUT_BASE64
	}catch(e){
		alert( "加密文件失败\n错误代码：0x" + (e.number>0?e.number: 0x100000000+e.number).toString(16)+ "\n错误描述：" + e.description);
	}
	return Result;
},

/**对消息（字符串）解密
 * @param encFile 加密后的文件名
 * @param destFile 解密后输出的原文件名
 * @param cert 解密证书，若为null,则用原加密证书解密（根据序列号找原加密证书）；若传入证书,则用指定证书解密
 * @return 成功返回：2；失败返回：0
 */
decryptFile:function (encFile,destFile,cert){
	var Result = null;
	if (cert==null){  // 使用默认证书解密
		try{
			if (iTrusPTA.DecryptFileEx(encFile,destFile,0)){ //INPUT_BASE64
				Result = 2;
			};
		}catch(e){
			var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
			if( errcode==0x80083004 ){ //0x80083004 意为没有找到原加密证书，后续可调用用户选择的证书解密
				Result = 1;
			}else{
				Result = 0;
				alert( "使用默认证书解密消息失败\n错误代码：0x" + errcode + "\n错误描述：" + e.description);
			}		
		}		
	}else{ // 使用用户选择的证书解密
		try{
			if (cert.DecryptFileEx(encFile,destFile,0)){//INPUT_BASE64
				Result = 2;
			};
		}catch(e){
			var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
			Result = 0;
			alert( "使用指定证书解密失败\n错误代码：0x" + errcode + "\n错误描述：" + e.description);					
		}	
	}
	return Result;
},
filterSignCerts:function () {
	this.Init();
	var arrayCerts = this.filterCerts(arrayIssuerDN, 0, "");
	//this.filterCertsByItem(arrayCerts, arrayIssuerDN[1], "OU="+ProjectName);
	this.filterCertsByItem(arrayCerts, arrayIssuerDN[2], "OU="+ProjectName);
	this.filterCertsByKeyUsageSignature(arrayCerts);
	return arrayCerts;
},
filterEncryptCerts:function () {
	this.Init();
	var arrayCerts = this.filterCerts(arrayIssuerDN, 0, "");
	this.filterCertsByItem(arrayCerts, arrayIssuerDN[0], "OU="+ProjectName);
	this.filterCertsByKeyUsageEncipherment(arrayCerts);
	return arrayCerts;
},
getCertEntityInfo:function (Cert,ItemKey) {
	var IssuerDN_0 = "C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEG CA"
	var IssuerDN_1 = "C=CN, O=四川省数字证书认证管理中心有限公司, OU=SCEB CA, CN=SCEB CA"
	var IssuerDN_2 = "C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEGB CA"
	var Issuer=Cert.Issuer.replace("\0","");
	var Subject=Cert.Subject.replace("\0","");
	var CertEntityInfo = "";
	if (Issuer==IssuerDN_0){
		if (ItemKey=="用户类型"){
			CertEntityInfo = "Government";
		}else if (ItemKey=="单位名称" || ItemKey=="个人姓名"){
			CertEntityInfo = Cert.CommonName;
		}else if (ItemKey=="组织机构代码" || ItemKey=="个人身份证号码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"SN=");
		}else if (ItemKey=="特殊业务编码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=OPT.4:");
		}else if (ItemKey=="多证书编号"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=OPT.3:");
			if (CertEntityInfo == "") {CertEntityInfo = 1;}
		}else if (ItemKey=="个人所属单位名称"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=OPT.2:");	
		}
	}else if (Issuer==IssuerDN_1){
		if (ItemKey=="用户类型"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=ID3:");
		}else if (ItemKey=="单位名称" || ItemKey=="个人姓名"){
			CertEntityInfo = Cert.CommonName;
		}else if (ItemKey=="组织机构代码" || ItemKey=="个人身份证号码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"SN=");
		}else if (ItemKey=="企业工商营业执照号"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=NO.1:");
		}else if (ItemKey=="多证书编号"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=ID1:");
			if (CertEntityInfo == "") {CertEntityInfo = 1;}
		}else if (ItemKey=="特殊业务编码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=ID2:");
		}else if (ItemKey=="个人所属单位组织机构代码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=ID4:");
		}
	}else if (Issuer==IssuerDN_2){
		if (ItemKey=="用户类型"){
			var CertType = this.getCertSubjectItem(Subject,"OU=NO.1:");
			if (CertType!=""){
				CertEntityInfo = "Organizational";
			}else{
				CertEntityInfo = "Personal";
			}
		}else if (ItemKey=="单位名称" || ItemKey=="个人姓名"){
			CertEntityInfo = Cert.CommonName;
		}else if (ItemKey=="组织机构代码" || ItemKey=="个人身份证号码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"SN=");
		}else if (ItemKey=="企业工商营业执照号"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=NO.1:");
		}else if (ItemKey=="多证书编号"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=NO.3:");
			if (CertEntityInfo == "") {CertEntityInfo = 1;}
		}else if (ItemKey=="个人所属单位名称"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=NO.2:");	
		}else if (ItemKey=="个人所属单位组织机构代码"){
			CertEntityInfo = this.getCertSubjectItem(Subject,"OU=ID4:");	
		}
	}else {
	  CertEntityInfo = "";
	}
	return CertEntityInfo;
},
GetSKIEx:function (Cert){
	var pkey = Cert.PublicKey;
	if( pkey==null ){
		return null;	
	}
	var InOPT  = PARAM_STRING | INPUT_HEX;
	var OutOPT = PARAM_STRING |	OUTPUT_HEX;
	var Result = iTrusPTA.HashData(pkey, InOPT, "", OutOPT, PTA_CALG_SHA1);	
	return Result;
},
getOIDValueString:function (str){
	var out = "";
	for(var i=4;i<str.length;i++)
	{
		var a = "0x" + str[i]+str[i+1];
		i++;		
		out += String.fromCharCode(a);
	}
    return out;
},
/********************************************************
 * 输出随机数
 * @param ByteLen 所需的字节长度
 * @param OutDataFlag 输出数据的格式, 可设置Base64或Hex。
 * @return 随机数
********************************************************/
getRandomByte:function (ByteLen, OutDataFlag)
{
	var Result;
	try{
		Result = iTrusPTA.RandomByte(ByteLen, OutDataFlag);
	}catch(e){
		var errcode = toHex(e.number);
		if( errcode=="80081001" ){
			alert("输入错误");
		}else{
			alert("未知错误");
		}
		return null;
	}
	return Result;
},
/********************************************************
 * 对称加解密 文件到文件
 * @param InFilePath	输入文件地址
 * @param OutFilePath	输出文件地址
 * @param AlgID			算法
 * @param Key			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IV			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IsEnc			设置true时进行加密操作, false时进行解密操作
 * @return				true为成功，false为失败
 * 输入输出的编码都使用Base64编码
 * AlgID可选值:
 * PTA_CALG_DES	= 0x010000;
 * PTA_CALG_3DES= 0x020000;
 * PTA_CALG_AES	= 0x040000;
 * PTA_CALG_RC4	= 0x080000;
 * PTA_CALG_SM4	= 0x100000;
 * 设置错误时将使用3DES
********************************************************/
symmFile:function (InFilePath, OutFilePath, AlgID, Key, IV, IsEnc)
{
	var InputOPT  = PARAM_FILENAME;// | INPUT_BASE64;
	var OutputOPT = PARAM_FILENAME;// | OUTPUT_BASE64;
	var Result;
	try{
		if(IsEnc==true){
			Result = iTrusPTA.SymmEnc(InFilePath, InputOPT, OutFilePath, OutputOPT, AlgID, Key, IV);
		}else{
			Result = iTrusPTA.SymmDec(InFilePath, InputOPT, OutFilePath, OutputOPT, AlgID, Key, IV);
		}
	}catch(e){
		var errcode = toHex(e.number);;
		switch(errcode)
		{
			case "80082001" :
				alert("读取文件错误");
				break;
			case "80082002" :
				alert("参数设置错误");
				break;
			case "80081001" :
				alert("Key或IV长度不适用于选择的算法");
				break;
			case "80083001" :
				alert("写入文件失败");
				break;
			default :
				alert("未知错误");
				break;
		}
		return false;
	}
	return true;
},
/********************************************************
 * 对称加密 消息到消息
 * @param InMSG			输入消息
 * @param AlgID			算法
 * @param Key			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IV			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IsEnc			设置true时进行加密操作, false时进行解密操作
 * @return				null为失败, 成功时返回加密后的消息
 * 输入输出的编码都使用Base64编码
 * AlgID可选值:
 * PTA_CALG_DES	= 0x010000;
 * PTA_CALG_3DES= 0x020000;
 * PTA_CALG_AES	= 0x040000;
 * PTA_CALG_RC4	= 0x080000;
 * PTA_CALG_SM4	= 0x100000;
 * 设置错误时将使用3DES
********************************************************/
symmMSG:function (InMSG, AlgID, Key, IV, IsEnc)
{
	var Result;
	try{
		if(IsEnc==true){
			var InputOPT  = PARAM_STRING;
			var OutputOPT = PARAM_STRING | OUTPUT_BASE64;
			Result = iTrusPTA.SymmEnc(InMSG, InputOPT, "", OutputOPT, AlgID, Key, IV);
		}else{
			var InputOPT  = PARAM_STRING | INPUT_BASE64;
			var OutputOPT = PARAM_STRING;			
			Result = iTrusPTA.SymmDec(InMSG, InputOPT, "", OutputOPT, AlgID, Key, IV);
		}
	}catch(e){
		var errcode = toHex(e.number);
		switch(errcode)
		{
			case "80082001" :
				alert("读取文件错误");
				break;
			case "80082002" :
				alert("参数设置错误");
				break;
			case "80081001" :
				alert("Key或IV长度不适用于选择的算法");
				break;
			case "80083001" :
				alert("写入文件失败");
				break;
			default :
				alert("未知错误");
				break;
		}
		return null;
	}
	return Result;
},
/********************************************************
 * 验证签名值 签名值和原文使用消息传入
 * @Cert				证书
 * @param SignData		签名值
 * @param Content		原文
 * @return				验签结果, true为成功, false为失败
 * 输入签名值和原文都使用Base64编码
********************************************************/
verifySignatureEx:function (Cert, SignData, Content)
{
	var SignOPT    = PARAM_STRING | INPUT_BASE64;
	var ContentOPT = PARAM_STRING;// | INPUT_BASE64;
	try{
		Result = Cert.VerifySignature(SignData, SignOPT, Content, ContentOPT);
	}catch(e){
		var errcode = toHex(e.number);
		switch(errcode)
		{
			case "80081001" :
				alert("证书上下文无效");
				break;
			case "80082001" :
				alert("读取文件错误");
				break;
			case "80082002" :
				alert("参数设置错误");
				break;
			default :
				alert("未知错误");
				break;
		}
		return false;
	}
	return Result;
},
/********************************************************
 * 非对称加解密 消息到消息
 * @Cert				证书
 * @param InputData		输入数据
 * @param IsEnc			设置为true时进行加密操作, false时为解密操作
 * @return				null为操作失败, 其他时候返回操作结果
 * 输入和输出数据使用Base64编码
********************************************************/
asymMSG:function (Cert, InputData, IsEnc)
{
	var Result;
	try{
		if( IsEnc==true ){
			var InputOPT  = PARAM_STRING | INPUT_BASE64
			var OutputOPT = PARAM_STRING | OUTPUT_BASE64;
			Result = Cert.Encrypt(InputData, InputOPT, "", OutputOPT);
		}else{
			var InputOPT  = PARAM_STRING | INPUT_BASE64;
			var OutputOPT = PARAM_STRING | OUTPUT_BASE64
			Result = Cert.Decrypt(InputData, InputOPT, "", OutputOPT);
		}
	}catch(e){
		var errcode = toHex(e.number);
		if( IsEnc==true )
		{
			switch(errcode)
			{
				case "80081001" : 
					alert("证书上下文无效");
					break;
				case "80082001" : 
					alert("从文件读取文件失败");
					break;
				case "80082002" : 
					alert("参数错误");
					break;
				case "80083001" : 
					alert("SM2加密失败");	// 只有SM2证书会出现
					break;
				case "80083002" : 
					alert("RSA加密失败");	// 只有RSA证书会出现
					break;
				case "80084001" : 
					alert("文件写入失败");
					break;
				default :
					alert("未知错误");
					break;
			}
		}else{
			switch(errcode)
			{
				case "80081001" :
					alert("证书上下文无效");
					break;
				case "80082001" :
					alert("从文件读取文件失败");
					break;
				case "80082002" :
					alert("参数错误");
					break;
				case "80083001" :
					alert("获取私钥信息失败");
					break;
				case "80083002" :
					alert("获取私钥信息失败");
					break;
				case "80083003" :
					alert("读取私钥失败");
					break;
				case "80083004" :
					alert("读取私钥失败");
					break;
				case "80083005" :
					alert("解密数据失败");
					break;
				case "80084001" :
					alert("文件写入失败");
					break;
				default :
					alert("未知错误");
					break;
			}

		}	// if( IsEnc==true )
		return null;
	}	// try catch
	return Result;
},
/********************************************************
 * 封装PKCS7签名 签名值和原文使用消息传入, P7结构使用消息输出
 * @Cert				证书
 * @param Sign			输入数据
 * @param Content		设置为true时进行加密操作, false时为解密操作
 * @param OP			设置 INNER_CONTENT 时, P7中包含原文
 * @return				null为操作失败, 其他时候返回操作结果
 * 输入和输出数据使用Base64编码
********************************************************/
encodePKCS7Sign:function (Cert, Sign, Content, OP)
{
	var SignOPT    = PARAM_STRING | INPUT_BASE64;
	var ContentOPT = PARAM_STRING;
	var OutputOPT  = PARAM_STRING | OUTPUT_BASE64;
	if( OP==INNER_CONTENT )
		OutputOPT = OutputOPT | INNER_CONTENT;
	var Result;
	try{
		Result = Cert.EncodePKCS7Sign(Sign, SignOPT, Content, ContentOPT, "", OutputOPT);
	}catch(e){
		var errcode = toHex(e.number);
		switch( errcode )
		{
			case "80081001" : 
				alert("证书上下文无效");
				break;
			case "80082001" : 
				alert("从文件读取文件失败");
				break;
			case "80082002" : 
				alert("参数错误");
				break;
			case "80083001" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80083002" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80083003" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80083004" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80084001" : 
				alert("文件写入失败");
				break;
			default :
				alert("未知错误")
		}	// switch
		return null;
	}	// try catch
	return Result;
},
verifyCertPIN:function (Cert,UserPin)
{
	var Result = false;
	try{
		Result = Cert.CheckUKeyPIN(UserPin);		
	}catch(e){
		/*
		var errcode = (e.number>0?e.number: 0x100000000+e.number).toString(16);
		if( errcode==0x80081001){
			// 证书上下文无效
		}else if( errcode==0x80081002){
			// 获取私钥信息失败
		}
		}else if( errcode==0x80081003){
			// 获取私钥信息失败
		}
		}else if( errcode==0x80081004){
			// 读取私钥失败
		}
		}else if( errcode==0x80081006){
			// 校验PIN失败
		}
		*/
	}
	return Result;	
}
};

function CertEntityInfo(Cert) {
	var IssuerDN_0 = "C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEG CA"
	var IssuerDN_1 = "C=CN, O=四川省数字证书认证管理中心有限公司, OU=SCEB CA, CN=SCEB CA"
	var IssuerDN_2 = "C=CN, O=四川省数字证书认证管理中心有限公司, OU=China Trust Network, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2008, OU=Class 2 Enterprise Individual Subscriber CA, CN=SCEGB CA"
	var Issuer=Cert.Issuer.replace("\0","");
	var Subject=Cert.Subject.replace("\0","");
	if (Issuer==IssuerDN_0){
		this._Type = "Government";
		this._Name = Cert.CommonName;
		this._IdentityCode = PTA.getCertSubjectItem(Subject,"SN=");
		this._SpecialAffairNumber = PTA.getCertSubjectItem(Subject,"OU=OPT.4:");
		var CertNo = PTA.getCertSubjectItem(Subject,"OU=OPT.3:");
		if (CertNo == "") {CertNo = 1;}
		this._MultiCertNumber = CertNo;
		this._OrgNameOfPerson = PTA.getCertSubjectItem(Subject,"OU=OPT.2:");
	}else if (Issuer==IssuerDN_1){
		var CertType = PTA.getCertSubjectItem(Subject,"OU=ID3:");
		this._Type = CertType;
		this._Name = Cert.CommonName;
		this._IdentityCode = PTA.getCertSubjectItem(Subject,"SN=");
		this._ICRegistrationNumber = PTA.getCertSubjectItem(Subject,"OU=NO.1:");
		this._SpecialAffairNumber = PTA.getCertSubjectItem(Subject,"OU=ID2:");
		var CertNo = PTA.getCertSubjectItem(Subject,"OU=ID1:");
		if (CertNo == "") {CertNo = 1;}
		this._MultiCertNumber = CertNo;
		this._OrgIDOfPerson = PTA.getCertSubjectItem(Subject,"OU=ID4:");
	}else if (Issuer==IssuerDN_2){
		var CertType = PTA.getCertSubjectItem(Subject,"OU=NO.1");
		if (CertType!=""){
			CertType = "Organizational";
		}else{
			CertType = "Personal";
		}
		this._Type = CertType;
		this._Name = Cert.CommonName;
		this._IdentityCode = PTA.getCertSubjectItem(Subject,"SN=");
		this._ICRegistrationNumber = PTA.getCertSubjectItem(Subject,"OU=NO.1:");
		var CertNo = PTA.getCertSubjectItem(Subject,"OU=NO.3:");
		if (CertNo == "") {CertNo = 1;}
		this._MultiCertNumber = CertNo;
		this._OrgNameOfPerson = PTA.getCertSubjectItem(Subject,"OU=NO.2:");
		this._OrgIDOfPerson = PTA.getCertSubjectItem(Subject,"OU=ID4:");
	}else {
	}
};


//alert(arrayCerts[index].PublicKey);
//alert(arrayCerts[index].AuthorityKeyIdentifier);
//alert(arrayCerts[index].SubjectKeyIdentifier);
//alert(PTA.GetSKIEx(arrayCerts[index]));
//alert(PTA.getSubjectKeyIdentifier(arrayCerts[index].GetEncodedCert(2)));
//alert(arrayCerts[index].GetX509ExtByOID("1.2.156.112571.6"));
//alert(PTA.getOIDValueString(arrayCerts[index].GetX509ExtByOID("1.2.156.112571.6")));

/********************** 以下是2.5.5.26新增的接口 **********************/

/********************************************************
 * 输出随机数
 * @param ByteLen 所需的字节长度
 * @param OutDataFlag 输出数据的格式, 可设置Base64或Hex。
 * @return 随机数
********************************************************/
function GetRandomByte(ByteLen, OutDataFlag)
{
	var Result;
	try{
		Result = iTrusPTA.RandomByte(ByteLen, OutDataFlag);
	}catch(e){
		var errcode = toHex(e.number);
		if( errcode=="80081001" ){
			alert("输入错误");
		}else{
			alert("未知错误");
		}
		return null;
	}
	return Result;
}


/********************************************************
 * 对称加解密 文件到文件
 * @param InFilePath	输入文件地址
 * @param OutFilePath	输出文件地址
 * @param AlgID			算法
 * @param Key			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IV			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IsEnc			设置true时进行加密操作, false时进行解密操作
 * @return				true为成功，false为失败
 * 输入输出的编码都使用Base64编码
 * AlgID可选值:
 * PTA_CALG_DES	= 0x010000;
 * PTA_CALG_3DES= 0x020000;
 * PTA_CALG_AES	= 0x040000;
 * PTA_CALG_RC4	= 0x080000;
 * PTA_CALG_SM4	= 0x100000;
 * 设置错误时将使用3DES
********************************************************/
function SymmFile(InFilePath, OutFilePath, AlgID, Key, IV, IsEnc)
{
	var InputOPT  = PARAM_FILENAME;// | INPUT_BASE64;
	var OutputOPT = PARAM_FILENAME;// | OUTPUT_BASE64;
	var Result;
	try{
		if(IsEnc==true){
			Result = iTrusPTA.SymmEnc(InFilePath, InputOPT, OutFilePath, OutputOPT, AlgID, Key, IV);
		}else{
			Result = iTrusPTA.SymmDec(InFilePath, InputOPT, OutFilePath, OutputOPT, AlgID, Key, IV);
		}
	}catch(e){
		var errcode = toHex(e.number);;
		switch(errcode)
		{
			case "80082001" :
				alert("读取文件错误");
				break;
			case "80082002" :
				alert("参数设置错误");
				break;
			case "80081001" :
				alert("Key或IV长度不适用于选择的算法");
				break;
			case "80083001" :
				alert("写入文件失败");
				break;
			default :
				alert("未知错误");
				break;
		}
		return false;
	}
	return true;
}

/********************************************************
 * 对称加密 消息到消息
 * @param InMSG			输入消息
 * @param AlgID			算法
 * @param Key			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IV			使用HEX编码, PTA会截取所需的长度, 如果长度不足会报错
 * @param IsEnc			设置true时进行加密操作, false时进行解密操作
 * @return				null为失败, 成功时返回加密后的消息
 * 输入输出的编码都使用Base64编码
 * AlgID可选值:
 * PTA_CALG_DES	= 0x010000;
 * PTA_CALG_3DES= 0x020000;
 * PTA_CALG_AES	= 0x040000;
 * PTA_CALG_RC4	= 0x080000;
 * PTA_CALG_SM4	= 0x100000;
 * 设置错误时将使用3DES
********************************************************/
function SymmMSG(InMSG, AlgID, Key, IV, IsEnc)
{
	var Result;
	try{
		if(IsEnc==true){
			var InputOPT  = PARAM_STRING;
			var OutputOPT = PARAM_STRING | OUTPUT_BASE64;
			Result = iTrusPTA.SymmEnc(InMSG, InputOPT, "", OutputOPT, AlgID, Key, IV);
		}else{
			var InputOPT  = PARAM_STRING | INPUT_BASE64;
			var OutputOPT = PARAM_STRING;			
			Result = iTrusPTA.SymmDec(InMSG, InputOPT, "", OutputOPT, AlgID, Key, IV);
		}
	}catch(e){
		var errcode = toHex(e.number);
		switch(errcode)
		{
			case "80082001" :
				alert("读取文件错误");
				break;
			case "80082002" :
				alert("参数设置错误");
				break;
			case "80081001" :
				alert("Key或IV长度不适用于选择的算法");
				break;
			case "80083001" :
				alert("写入文件失败");
				break;
			default :
				alert("未知错误");
				break;
		}
		return null;
	}
	return Result;
}

/********************************************************
 * 验证签名值 签名值和原文使用消息传入
 * @Cert				证书
 * @param SignData		签名值
 * @param Content		原文
 * @return				验签结果, true为成功, false为失败
 * 输入签名值和原文都使用Base64编码
********************************************************/
function VerifySignatureEx(Cert, SignData, Content)
{
	var SignOPT    = PARAM_STRING | INPUT_BASE64;
	var ContentOPT = PARAM_STRING;// | INPUT_BASE64;
	try{
		Result = Cert.VerifySignature(SignData, SignOPT, Content, ContentOPT);
	}catch(e){
		var errcode = toHex(e.number);
		switch(errcode)
		{
			case "80081001" :
				alert("证书上下文无效");
				break;
			case "80082001" :
				alert("读取文件错误");
				break;
			case "80082002" :
				alert("参数设置错误");
				break;
			default :
				alert("未知错误");
				break;
		}
		return false;
	}
	return Result;
}

/********************************************************
 * 非对称加解密 消息到消息
 * @Cert				证书
 * @param InputData		输入数据
 * @param IsEnc			设置为true时进行加密操作, false时为解密操作
 * @return				null为操作失败, 其他时候返回操作结果
 * 输入和输出数据使用Base64编码
********************************************************/
function AsymMSG(Cert, InputData, IsEnc)
{
	var Result;
	try{
		if( IsEnc==true ){
			var InputOPT  = PARAM_STRING | INPUT_BASE64
			var OutputOPT = PARAM_STRING | OUTPUT_BASE64;
			Result = Cert.Encrypt(InputData, InputOPT, "", OutputOPT);
		}else{
			var InputOPT  = PARAM_STRING | INPUT_BASE64;
			var OutputOPT = PARAM_STRING | OUTPUT_BASE64
			Result = Cert.Decrypt(InputData, InputOPT, "", OutputOPT);
		}
	}catch(e){
		var errcode = toHex(e.number);
		if( IsEnc==true )
		{
			switch(errcode)
			{
				case "80081001" : 
					alert("证书上下文无效");
					break;
				case "80082001" : 
					alert("从文件读取文件失败");
					break;
				case "80082002" : 
					alert("参数错误");
					break;
				case "80083001" : 
					alert("SM2加密失败");	// 只有SM2证书会出现
					break;
				case "80083002" : 
					alert("RSA加密失败");	// 只有RSA证书会出现
					break;
				case "80084001" : 
					alert("文件写入失败");
					break;
				default :
					alert("未知错误");
					break;
			}
		}else{
			switch(errcode)
			{
				case "80081001" :
					alert("证书上下文无效");
					break;
				case "80082001" :
					alert("从文件读取文件失败");
					break;
				case "80082002" :
					alert("参数错误");
					break;
				case "80083001" :
					alert("获取私钥信息失败");
					break;
				case "80083002" :
					alert("获取私钥信息失败");
					break;
				case "80083003" :
					alert("读取私钥失败");
					break;
				case "80083004" :
					alert("读取私钥失败");
					break;
				case "80083005" :
					alert("解密数据失败");
					break;
				case "80084001" :
					alert("文件写入失败");
					break;
				default :
					alert("未知错误");
					break;
			}

		}	// if( IsEnc==true )
		return null;
	}	// try catch
	return Result;
}	// func


/********************************************************
 * 封装PKCS7签名 签名值和原文使用消息传入, P7结构使用消息输出
 * @Cert				证书
 * @param Sign			输入数据
 * @param Content		设置为true时进行加密操作, false时为解密操作
 * @param OP			设置 INNER_CONTENT 时, P7中包含原文
 * @return				null为操作失败, 其他时候返回操作结果
 * 输入和输出数据使用Base64编码
********************************************************/
function EncodePKCS7Sign(Cert, Sign, Content, OP)
{
	var SignOPT    = PARAM_STRING | INPUT_BASE64;
	var ContentOPT = PARAM_STRING;
	var OutputOPT  = PARAM_STRING | OUTPUT_BASE64;
	if( OP==INNER_CONTENT )
		OutputOPT = OutputOPT | INNER_CONTENT;
	var Result;
	try{
		Result = Cert.EncodePKCS7Sign(Sign, SignOPT, Content, ContentOPT, "", OutputOPT);
	}catch(e){
		var errcode = toHex(e.number);
		switch( errcode )
		{
			case "80081001" : 
				alert("证书上下文无效");
				break;
			case "80082001" : 
				alert("从文件读取文件失败");
				break;
			case "80082002" : 
				alert("参数错误");
				break;
			case "80083001" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80083002" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80083003" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80083004" : 
				alert("编码PKCS7签名信息失败");
				break;
			case "80084001" : 
				alert("文件写入失败");
				break;
			default :
				alert("未知错误")
		}	// switch
		return null;
	}	// try catch
	return Result;
}	// func

/********************************************************
 * 2.5.5.26新增接口的测试
********************************************************/

function RandomTest()
{
	// 获取随机数
	var Random;
	Random = GetRandomByte(8, OUTPUT_HEX);
	alert("8字节长的随机数, Hex编码 : " + Random);
}

function SymmEncDecTest()
{
	// 对称加解密, RC4
	PTA_CALG_DES	= 0x010000;
	PTA_CALG_3DES	= 0x020000;
	PTA_CALG_AES	= 0x040000;
	PTA_CALG_RC4	= 0x080000;
	PTA_CALG_SM4	= 0x100000;
	var AlgID = PTA_CALG_RC4;
	// 测试使用Key和IV : ascii "1234567812345678..." 64字节长
	var Key = "31323334353637383132333435363738313233343536373831323334353637383132333435363738313233343536373831323334353637383132333435363738";
	var IV  = Key;
	var SymmResult;
	var InFile;
	var OutFile;
	var InMSG;
	// 对称加密, 文件到文件, RC4
	InFile  = "C:\\[PTA_26_TEST]\\SymmP.txt";
	OutFile = "C:\\[PTA_26_TEST]\\SymmE.txt";
	SymmResult = SymmFile(InFile, OutFile, AlgID, Key, IV, true);
	if( SymmResult==true )
		alert("对称加密文件成功!")
	// 对称解密, 文件到文件, RC4
	InFile  = "C:\\[PTA_26_TEST]\\SymmE.txt";
	OutFile = "C:\\[PTA_26_TEST]\\SymmD.txt";
	SymmResult = SymmFile(InFile, OutFile, AlgID, Key, IV, false);
	if( SymmResult==true )
		alert("对称解密文件成功!")
	// 对称加密, 消息到消息, RC4
	InMSG = "MTIzNDU2Nzg=";		// "12345678"
	alert("原始消息:" + InMSG);
	SymmResult = SymmMSG(InMSG, AlgID, Key, IV, true);
	if( SymmResult!=null )
		alert("对称加密消息成功:"+ SymmResult);
	// 对称解密, 消息到消息, RC4
	InMSG = SymmResult;
	SymmResult = SymmMSG(InMSG, AlgID, Key, IV, false);
	if( SymmResult!=null )
		alert("对称解密消息成功:"+ SymmResult);
}

function verifySignatureExTest()
{
	// 
	var CertFilter = iTrusPTA.Filter;
	var Certs;
	// 签名值验签, SM2
	var VerifyCert;
	var Sign;
	var Content;
	var VerifyResult;
	//
	Content = "MTIzNDU2";
	// SM2证书, 序列号"68b9647afbb788407ecc4abbbe6284f508d67888"
	CertFilter.Clear();
	CertFilter.SerialNumber = "68b9647afbb788407ecc4abbbe6284f508d67888";
	Certs = iTrusPTA.MyCertificates;
	VerifyCert = Certs.Item(1);
	alert("签名值验证测试, SM2证书序列号:"+VerifyCert.SerialNumber);
	Sign = "MEQCIFOuhQRDm4481zkEjS9GAWPJ6/kwV23cVilgMvKzcX0qAiAG2WsAn7aAGvZ22wOVqiwNWr8EOc0k+cJeWUAelclPRQ==";
	VerifyResult = VerifySignatureEx(VerifyCert, Sign, Content);
	if( VerifyResult==true ){
		alert("验签成功")
	}else{
		alert("验签失败");
	}
	// 签名值验签, RSA
	// RSA证书, 序列号"6365fdc89b0706d4f47910bb62982d9f3a5216c9"
	CertFilter.Clear();
	CertFilter.SerialNumber = "6365fdc89b0706d4f47910bb62982d9f3a5216c9";
	Certs = iTrusPTA.MyCertificates;
	VerifyCert = Certs.Item(1);
	alert("签名值验证测试, RSA证书序列号:"+VerifyCert.SerialNumber);
	Sign = "pGidrnBMESAp3dhHxkbgjOCpI5Fdxpwa+3PG7MPum4l/xHDOfoLTvZLKzXt/j5ONkv0SD2xCjhYG+eNkG04W4wE2Z0w7I9YW9Q9S6vUuuXTgUxSrJNSIRG+e5IKPS5aGpsBaTjAWlmCuED3tF5eteIKMXgWecRdegS/hMtqY8J4=";
	VerifyResult = VerifySignatureEx(VerifyCert, Sign, Content);
	if( VerifyResult==true ){
		alert("验签成功")
	}else{
		alert("验签失败");
	}
}

function AsymTest()
{
	// 
	var CertFilter = iTrusPTA.Filter;
	var Certs;
	// 非对称加解密, 消息到消息, SM2
	var AsymCert;
	var AsymResult;
	var InputData = "MTIzNDU2Nzg=";			// "12345678"

	// SM2证书, 序列号"68b9647afbb788407ecc4abbbe6284f508d67888"
	CertFilter.Clear();
	CertFilter.SerialNumber = "68b9647afbb788407ecc4abbbe6284f508d67888";
	Certs = iTrusPTA.MyCertificates;
	AsymCert = Certs.Item(1);
	alert("非对称加解密测试, SM2证书序列号:"+AsymCert.SerialNumber);
	AsymResult = AsymMSG(AsymCert, InputData, true);
	if( AsymResult!=null )
		alert("SM2加密消息成功:"+ AsymResult);
	InputData = AsymResult;
	AsymResult = AsymMSG(AsymCert, InputData, false);
	if( AsymResult!=null )
		alert("SM2解密消息成功:"+ AsymResult);

	// 非对称加解密, 消息到消息, RSA
	InputData = "MTIzNDU2Nzg=";
	// RSA证书, 序列号"6365fdc89b0706d4f47910bb62982d9f3a5216c9"
	CertFilter.Clear();
	CertFilter.SerialNumber = "6365fdc89b0706d4f47910bb62982d9f3a5216c9";
	Certs = iTrusPTA.MyCertificates;
	AsymCert = Certs.Item(1);
	alert("非对称加解密, RSA证书序列号:"+AsymCert.SerialNumber);
	AsymResult = AsymMSG(AsymCert, InputData, true);
	if( AsymResult!=null )
		alert("RSA加密消息成功:"+ AsymResult);
	InputData = AsymResult;
	AsymResult = AsymMSG(AsymCert, InputData, false);
	if( AsymResult!=null )
		alert("RSA解密消息成功:"+ AsymResult);
}

function EncodeP7Test()
{
	var CertFilter = iTrusPTA.Filter;
	var Certs;
	// 签名值封装P7, Sign和Content使用消息
	var P7Cert;
	var P7Sign;
	var P7Content;
	var P7Result;
	//
	var VerifyResult;
	// SM2证书
	// SM2证书, 序列号"68b9647afbb788407ecc4abbbe6284f508d67888"
	CertFilter.Clear();
	CertFilter.SerialNumber = "68b9647afbb788407ecc4abbbe6284f508d67888";
	Certs = iTrusPTA.MyCertificates;
	P7Cert = Certs.Item(1);
	alert("签名值封装P7, SM2证书序列号:"+P7Cert.SerialNumber);
	P7Sign = "MEQCIFOuhQRDm4481zkEjS9GAWPJ6/kwV23cVilgMvKzcX0qAiAG2WsAn7aAGvZ22wOVqiwNWr8EOc0k+cJeWUAelclPRQ==";
	P7Content = "MTIzNDU2";
	// 封装P7, 包含原文
	P7Result = EncodePKCS7Sign(P7Cert, P7Sign, P7Content, INNER_CONTENT);
	if( P7Result==null )
		alert("SM2证书+SM2签名+原文封装P7失败");
	alert("SM2证书+SM2签名+原文封装P7:"+P7Result);
 	VerifyResult = VerifySignature("base64", "base64", false, "", P7Result);
 	alert("签名的证书:"+VerifyResult.SerialNumber);
	// 封装P7, 不包含原文
	P7Result = EncodePKCS7Sign(P7Cert, P7Sign, P7Content, 0);
	if( P7Result==null )
		alert("SM2证书+SM2签名封装P7失败");
	alert("SM2证书+SM2签名封装P7:"+P7Result);
 	VerifyResult = VerifySignature("base64", "base64", false, P7Content, P7Result);
 	alert("签名的证书:"+VerifyResult.SerialNumber);
	// 验证封装的P7, SM2带原文

	// RSA证书
	// RSA证书, 序列号"6365fdc89b0706d4f47910bb62982d9f3a5216c9"
	CertFilter.Clear();
	CertFilter.SerialNumber = "6365fdc89b0706d4f47910bb62982d9f3a5216c9";
	Certs = iTrusPTA.MyCertificates;
	P7Cert = Certs.Item(1);
	alert("签名值封装P7, RSA证书序列号:"+P7Cert.SerialNumber);
	P7Sign = "pGidrnBMESAp3dhHxkbgjOCpI5Fdxpwa+3PG7MPum4l/xHDOfoLTvZLKzXt/j5ONkv0SD2xCjhYG+eNkG04W4wE2Z0w7I9YW9Q9S6vUuuXTgUxSrJNSIRG+e5IKPS5aGpsBaTjAWlmCuED3tF5eteIKMXgWecRdegS/hMtqY8J4=";
	P7Content = "MTIzNDU2";
	// 封装P7, 包含原文
	P7Result = EncodePKCS7Sign(P7Cert, P7Sign, P7Content, INNER_CONTENT);
	if( P7Result==null )
		alert("RSA证书+RSA签名+原文封装P7失败");
	alert("RSA证书+RSA签名+原文封装P7:"+P7Result);
	VerifyResult = VerifySignature("base64", "base64", false, "", P7Result);
 	alert("签名的证书:"+VerifyResult.SerialNumber);
	// 封装P7, 不包含原文
	P7Result = EncodePKCS7Sign(P7Cert, P7Sign, P7Content, 0);
	if( P7Result==null )
		alert("RSA证书+RSA签名封装P7失败");
	alert("RSA证书+RSA签名封装P7:"+P7Result);
	VerifyResult = VerifySignature("base64", "base64", false, P7Content, P7Result);
 	alert("签名的证书:"+VerifyResult.SerialNumber);
}
