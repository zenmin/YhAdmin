package com.yh.yhadmin.foundation.constant;


import com.yh.yhadmin.util.StaticUtil;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 21:11
 */
public class CommonConstant {
    public static int DELETE_TYPE_ALL = 0;  //删除全部

    public static int DELETE_TYPE_TRUE = 1; //删除已使用的卡密

    public static int DELETE_TYPE_FALSE = 2;    //删除未使用的卡密

    public static String CONFIG_ID ="0D16C6345DC045BFBA489D4C9C50F0D0"; //首页配置ID

    public enum InterfaceConfig{

        PHONE_TYPE(1,"PHONE_TYPE"),     //短信接口
        MAIL_TYPE(2,"MAIL_TYPE"),       //邮件配置
        PAY_TYPE(3,"PAY_TYPE"),     //支付接口
        PAY_SWITCH_TYPE(4,"PAY_SWITCH_TYPE"),       //支付接口开关
        INDEX_STYLE(5,"INDEX_STYLE");       //首页风格

        private int code;

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

    public static void main(String args[]){
        System.out.println(CommonConstant.InterfaceConfig.getCode("MAIL_TYPE"));
        System.out.println(CommonConstant.InterfaceConfig.getValue(2));
        System.out.println(StaticUtil.UUID().toUpperCase());
    }



}
