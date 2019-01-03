/**
 * 北京统计通用js。需要jQuery
 * Created by Dendy on 2016/1/21.
 */
function connectWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener('WebViewJavascriptBridgeReady',
            function () {
                callback(WebViewJavascriptBridge)
            },
            false)
    }
}


//var deviceId = "测试deviceid";
//var title = _count_config.title || $('title').text();
//var pageId = _count_config.pageId || hex_md5(window.location.href);
//var column = _count_config.cat || window.Buried.CatalogueID.app;
//var serviceCode = _count_config.serviceCode || "";
//$.ajax({
//    url: '/cd_portal/platform/service/Count.countApp.json',
//    dataType: "json",
//    type : "post",
//    data: {
//        "deviceId": deviceId,
//        "title": title,
//        "column": column,
//        "pageId": pageId,
//        "serviceCode": serviceCode
//    },
//    success: function (data) {
//        config.debug && console.log(data);
//    },
//    error: function (data) {
//        config.debug && console.log(data);
//    }
//});
_debug = false;
count(_count_config);

/**
 * 添加统计记录。
 *
 * 配置对象包括以下参数信息：
 *
 "title" ：页面标题，不传直接取title的内容
 "column"：栏目id，不传为空字符串
 "pageId"：页面id，不传为当前页面的地址的MD5加密,
 "debug": 传true，则开启调试模式，可以alert出以上参数信息。

 * @param config 配置对象
 */
function count(config) {
	config && (config.debug = _debug);
    config.debug && alert("entry");
    connectWebViewJavascriptBridge(
        function (bridge) {
            config.debug && alert("bridge ; " + bridge);
           
            /*在回调中首先要对bridge进行init才能继续使用*/
            bridge.init(function (message, responseCallback) {
            	
            });
            
            bridge.callHandler('getDeviceId', null, function (response) {
                response = JSON.parse(response);
                var deviceId = response.deviceId || "";
               
                var title = config.title || $('title').text();
                var pageId = config.pageId || hex_md5(window.location.href);
                var column = config.cat || window.Buried.CatalogueID.app;
                var serviceCode = config.serviceCode || "";

                config.debug && alert(
                    "deviceId : " + deviceId +
                    "\ntitle : " + title +
                    "\ncolumn : " + column +
                    "\npageId : " + pageId
                );

                function getContentPath() {
                    if (typeof(ctx)!= 'undefined') 
                    	  return ctx;
                    var pathName = document.location.pathname;
                    var index = pathName.substr(1).indexOf("/");
                    var path = pathName.substr(0, index + 1);
                    return path;
                }

                $.ajax({
                    url:  getContentPath() + '/platform/service/Count.countApp.json',
                    dataType: "json",
                    type : "post",
                    data: {
                        "deviceId": deviceId,
                        "title": title,
                        "column": column,
                        "pageId": pageId,
                        "serviceCode": serviceCode
                    },
                    success: function (data) {
                        config.debug && console.log(data);
                    },
                    error: function (data) {
                        config.debug && console.log(data);
                    }
                });
            });
        });
}

/*******  MD5 ******/
function hex_md5(r) {
    return binl2hex(core_md5(str2binl(r), r.length * chrsz))
}
function b64_md5(r) {
    return binl2b64(core_md5(str2binl(r), r.length * chrsz))
}
function str_md5(r) {
    return binl2str(core_md5(str2binl(r), r.length * chrsz))
}
function hex_hmac_md5(r, e) {
    return binl2hex(core_hmac_md5(r, e))
}
function b64_hmac_md5(r, e) {
    return binl2b64(core_hmac_md5(r, e))
}
function str_hmac_md5(r, e) {
    return binl2str(core_hmac_md5(r, e))
}
function md5_vm_test() {
    return "900150983cd24fb0d6963f7d28e17f72" == hex_md5("abc")
}
function core_md5(r, e) {
    r[e >> 5] |= 128 << e % 32, r[(e + 64 >>> 9 << 4) + 14] = e;
    for (var t = 1732584193, n = -271733879, h = -1732584194, d = 271733878, c = 0; c < r.length; c += 16) {
        var a = t, f = n, i = h, o = d;
        t = md5_ff(t, n, h, d, r[c + 0], 7, -680876936), d = md5_ff(d, t, n, h, r[c + 1], 12, -389564586), h = md5_ff(h, d, t, n, r[c + 2], 17, 606105819), n = md5_ff(n, h, d, t, r[c + 3], 22, -1044525330), t = md5_ff(t, n, h, d, r[c + 4], 7, -176418897), d = md5_ff(d, t, n, h, r[c + 5], 12, 1200080426), h = md5_ff(h, d, t, n, r[c + 6], 17, -1473231341), n = md5_ff(n, h, d, t, r[c + 7], 22, -45705983), t = md5_ff(t, n, h, d, r[c + 8], 7, 1770035416), d = md5_ff(d, t, n, h, r[c + 9], 12, -1958414417), h = md5_ff(h, d, t, n, r[c + 10], 17, -42063), n = md5_ff(n, h, d, t, r[c + 11], 22, -1990404162), t = md5_ff(t, n, h, d, r[c + 12], 7, 1804603682), d = md5_ff(d, t, n, h, r[c + 13], 12, -40341101), h = md5_ff(h, d, t, n, r[c + 14], 17, -1502002290), n = md5_ff(n, h, d, t, r[c + 15], 22, 1236535329), t = md5_gg(t, n, h, d, r[c + 1], 5, -165796510), d = md5_gg(d, t, n, h, r[c + 6], 9, -1069501632), h = md5_gg(h, d, t, n, r[c + 11], 14, 643717713), n = md5_gg(n, h, d, t, r[c + 0], 20, -373897302), t = md5_gg(t, n, h, d, r[c + 5], 5, -701558691), d = md5_gg(d, t, n, h, r[c + 10], 9, 38016083), h = md5_gg(h, d, t, n, r[c + 15], 14, -660478335), n = md5_gg(n, h, d, t, r[c + 4], 20, -405537848), t = md5_gg(t, n, h, d, r[c + 9], 5, 568446438), d = md5_gg(d, t, n, h, r[c + 14], 9, -1019803690), h = md5_gg(h, d, t, n, r[c + 3], 14, -187363961), n = md5_gg(n, h, d, t, r[c + 8], 20, 1163531501), t = md5_gg(t, n, h, d, r[c + 13], 5, -1444681467), d = md5_gg(d, t, n, h, r[c + 2], 9, -51403784), h = md5_gg(h, d, t, n, r[c + 7], 14, 1735328473), n = md5_gg(n, h, d, t, r[c + 12], 20, -1926607734), t = md5_hh(t, n, h, d, r[c + 5], 4, -378558), d = md5_hh(d, t, n, h, r[c + 8], 11, -2022574463), h = md5_hh(h, d, t, n, r[c + 11], 16, 1839030562), n = md5_hh(n, h, d, t, r[c + 14], 23, -35309556), t = md5_hh(t, n, h, d, r[c + 1], 4, -1530992060), d = md5_hh(d, t, n, h, r[c + 4], 11, 1272893353), h = md5_hh(h, d, t, n, r[c + 7], 16, -155497632), n = md5_hh(n, h, d, t, r[c + 10], 23, -1094730640), t = md5_hh(t, n, h, d, r[c + 13], 4, 681279174), d = md5_hh(d, t, n, h, r[c + 0], 11, -358537222), h = md5_hh(h, d, t, n, r[c + 3], 16, -722521979), n = md5_hh(n, h, d, t, r[c + 6], 23, 76029189), t = md5_hh(t, n, h, d, r[c + 9], 4, -640364487), d = md5_hh(d, t, n, h, r[c + 12], 11, -421815835), h = md5_hh(h, d, t, n, r[c + 15], 16, 530742520), n = md5_hh(n, h, d, t, r[c + 2], 23, -995338651), t = md5_ii(t, n, h, d, r[c + 0], 6, -198630844), d = md5_ii(d, t, n, h, r[c + 7], 10, 1126891415), h = md5_ii(h, d, t, n, r[c + 14], 15, -1416354905), n = md5_ii(n, h, d, t, r[c + 5], 21, -57434055), t = md5_ii(t, n, h, d, r[c + 12], 6, 1700485571), d = md5_ii(d, t, n, h, r[c + 3], 10, -1894986606), h = md5_ii(h, d, t, n, r[c + 10], 15, -1051523), n = md5_ii(n, h, d, t, r[c + 1], 21, -2054922799), t = md5_ii(t, n, h, d, r[c + 8], 6, 1873313359), d = md5_ii(d, t, n, h, r[c + 15], 10, -30611744), h = md5_ii(h, d, t, n, r[c + 6], 15, -1560198380), n = md5_ii(n, h, d, t, r[c + 13], 21, 1309151649), t = md5_ii(t, n, h, d, r[c + 4], 6, -145523070), d = md5_ii(d, t, n, h, r[c + 11], 10, -1120210379), h = md5_ii(h, d, t, n, r[c + 2], 15, 718787259), n = md5_ii(n, h, d, t, r[c + 9], 21, -343485551), t = safe_add(t, a), n = safe_add(n, f), h = safe_add(h, i), d = safe_add(d, o)
    }
    return Array(t, n, h, d)
}
function md5_cmn(r, e, t, n, h, d) {
    return safe_add(bit_rol(safe_add(safe_add(e, r), safe_add(n, d)), h), t)
}
function md5_ff(r, e, t, n, h, d, c) {
    return md5_cmn(e & t | ~e & n, r, e, h, d, c)
}
function md5_gg(r, e, t, n, h, d, c) {
    return md5_cmn(e & n | t & ~n, r, e, h, d, c)
}
function md5_hh(r, e, t, n, h, d, c) {
    return md5_cmn(e ^ t ^ n, r, e, h, d, c)
}
function md5_ii(r, e, t, n, h, d, c) {
    return md5_cmn(t ^ (e | ~n), r, e, h, d, c)
}
function core_hmac_md5(r, e) {
    var t = str2binl(r);
    t.length > 16 && (t = core_md5(t, r.length * chrsz));
    for (var n = Array(16), h = Array(16), d = 0; 16 > d; d++)n[d] = 909522486 ^ t[d], h[d] = 1549556828 ^ t[d];
    var c = core_md5(n.concat(str2binl(e)), 512 + e.length * chrsz);
    return core_md5(h.concat(c), 640)
}
function safe_add(r, e) {
    var t = (65535 & r) + (65535 & e), n = (r >> 16) + (e >> 16) + (t >> 16);
    return n << 16 | 65535 & t
}
function bit_rol(r, e) {
    return r << e | r >>> 32 - e
}
function str2binl(r) {
    for (var e = Array(), t = (1 << chrsz) - 1, n = 0; n < r.length * chrsz; n += chrsz)e[n >> 5] |= (r.charCodeAt(n / chrsz) & t) << n % 32;
    return e
}
function binl2str(r) {
    for (var e = "", t = (1 << chrsz) - 1, n = 0; n < 32 * r.length; n += chrsz)e += String.fromCharCode(r[n >> 5] >>> n % 32 & t);
    return e
}
function binl2hex(r) {
    for (var e = hexcase ? "0123456789ABCDEF" : "0123456789abcdef", t = "", n = 0; n < 4 * r.length; n++)t += e.charAt(15 & r[n >> 2] >> 8 * (n % 4) + 4) + e.charAt(15 & r[n >> 2] >> 8 * (n % 4));
    return t
}
function binl2b64(r) {
    for (var e = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", t = "", n = 0; n < 4 * r.length; n += 3)for (var h = (255 & r[n >> 2] >> 8 * (n % 4)) << 16 | (255 & r[n + 1 >> 2] >> 8 * ((n + 1) % 4)) << 8 | 255 & r[n + 2 >> 2] >> 8 * ((n + 2) % 4), d = 0; 4 > d; d++)t += 8 * n + 6 * d > 32 * r.length ? b64pad : e.charAt(63 & h >> 6 * (3 - d));
    return t
}
var hexcase = 0, b64pad = "", chrsz = 8;