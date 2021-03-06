package com.yh.yhadmin.foundation.constant;


import com.google.common.collect.Lists;
import com.yh.yhadmin.util.StaticUtil;
import lombok.Getter;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 21:11
 */
public class CommonConstant {

    public static final String INIT_PASSWORD = "123456";

    public static final String CAHE_NAME = "YHADMIN_USERINFO";

    public static final String CAHE_NAME_ID = "YHADMIN_USERINFO_ID";

    public static final String AUTH_KEY = "ZM666";

    public static final String AUTH_KEY_CACHE = "YHADMIN_AUTH";

    public static final Integer STATUS_OK = 1; //启用

    public static final Integer STATUS_ERROR = 0; //禁用

    public static int DELETE_TYPE_ALL = 0;  //删除全部

    public static int DELETE_TYPE_TRUE = 1; //删除已使用的卡密

    public static int DELETE_TYPE_FALSE = 2;    //删除未使用的卡密

    public static String CONFIG_ID ="0D16C6345DC045BFBA489D4C9C50F0D0"; //首页配置ID

    public static String DELTET_BATCH_CARDPWD = "批量删除卡密";

    public static final String DEFAULT_TEMPLETE = "webtemps/default/index";

    public static final String DEFAULT_TEMP_STATIC_PATH = "webtemps/default";

    public static String PAY_WAAY_ALIPAY = "alipay";    //支付宝
    public static String PAY_WAAY_QQPAY = "qqpay";         //wx
    public static String PAY_WAAY_WXPAY = "wxpay";       //qq

    public static int PAY_STATUS_OK = 1;       // 已支付
    public static int PAY_STATUS_TIMEOUT = 2;       // 支付超时

    // 接口类型
    public static List<String> ALL_INTERFACE_CONFIG = Lists.newArrayList("PHONE_TYPE","MAIL_TYPE","PAY_TYPE","PAY_SWITCH_TYPE","INDEX_STYLE");

    // 短信发送的key
    public static List<String> ALL_SENDSMS_CONFIG = Lists.newArrayList("code","orderNo");

    public static String PAY_TYPE_MAPAY = "MAPAY";  //支付方式  码支付

    public enum InterfaceConfig{

        PHONE_TYPE(1,"PHONE_TYPE"),     //短信接口
        MAIL_TYPE(2,"MAIL_TYPE"),       //邮件配置
        PAY_TYPE(3,"PAY_TYPE"),     //支付接口
        PAY_SWITCH_TYPE(4,"PAY_SWITCH_TYPE"),       //支付接口开关
        INDEX_STYLE(5,"INDEX_STYLE");       //首页风格

        @Getter
        private int code;

        @Getter
        private String value;

        InterfaceConfig(int code,String value){
            this.code = code;
            this.value = value;
        }

        public static String getValue(int code){
            InterfaceConfig[] values = InterfaceConfig.values();
            for(InterfaceConfig v : values){
                if(v.code == code){
                    return v.value;
                }
            }
            return null;
        }

        public static int getCode(String value){
            InterfaceConfig[] values = InterfaceConfig.values();
            for (InterfaceConfig v : values){
                if(v.value.equals(value)){
                    return v.code;
                }
            }
            return 0;
        }

    }

    public enum PayWay{
        ALIPAY(1,"alipay"),
        QQAPY(2,"qqpay"),
        WXPAY(3,"wxpay");

        @Getter
        private int code;

        @Getter
        private String value;

        PayWay(int code,String value){
            this.code = code;
            this.value = value;
        }

        public static Integer getCode(String value){
            PayWay[] values = PayWay.values();
            for (PayWay v : values){
                if(v.value.equals(value)){
                    return v.code;
                }
            }
            return 0;
        }
    }
}
