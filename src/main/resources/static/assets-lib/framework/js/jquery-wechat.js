/**
 * 微信工具js
 * Created by TimYao on 2015/11/30.
 */
(function (W, $, S) {

    //~ 变量定义 ========================================================================================================

    // 存储openid的key
    var _openIdKey = 'dc.storage.cd.openid.key';
    var _appIdKey = 'dc.storage.cd.appid.key';

    //~ 对象定义 ========================================================================================================

    // 对象定义
    function Wechat() {
    }

    Wechat.prototype = {
        constructor: Wechat
    };

    Wechat.prototype.getOpenid = function () {
        return S.get(_openIdKey);
    };
    Wechat.prototype.getAppId = function () {
        return S.get(_appIdKey);
    };

    Wechat.prototype.saveOpenid = function (openid) {
        S.set(_openIdKey, openid);
    };

    Wechat.prototype.saveAppId = function (appid) {
        S.set(_appIdKey, appid);
    };

    Wechat.prototype.removeOpenid = function () {
        S.remove(_openIdKey);
    };

    Wechat.prototype.auth = function (appid) {
        var self = this;
        var code = _getvl("code");
        //alert(code);
        if (!code) {
            _getCode(appid);
        } else {
            ajaxJsonCall('/wechat/service/WeChat.addWechatUserInfo.json', {
                'code': code,
                'appid': appid
            }, function (data) {
                if (data.rtnCode == '000000') {
                    //Message.toast.success('授权成功！').appear();
                    var openId = data.responseData.openid;
                    self.saveOpenid(openId);
                    self.saveAppId(appid);
                } else {
                    Message.toast.fail('授权失败，系统出现未知错误！').appear();
                }
            }, false);
        }
    };

    //~ 工具方法 ========================================================================================================

    function _getvl(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(W.location.href)) {
            return unescape(RegExp.$2.replace(/\+/g, " "));
        }
        return undefined;
    }

    function _getCode(appid) {
        var href = W.location.href;

        var redirect_uri = encodeURI(href.split('#')[0]);

        // 多个参数跳转后无法获取，转义
        redirect_uri = redirect_uri.replace(/&/g, '%26');
        W.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + redirect_uri +
            "&response_type=code&scope=snsapi_base&state=abcdefghigklmnopqrstuvwxyz#wechat_redirect";
    }

    //~ 出口定义 ========================================================================================================

    W.Wechat = new Wechat();
})(window, window.jQuery, window.Storage);