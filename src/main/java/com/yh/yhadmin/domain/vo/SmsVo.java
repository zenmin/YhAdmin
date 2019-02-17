package com.yh.yhadmin.domain.vo;

import com.yh.yhadmin.foundation.constant.CommonConstant;
import lombok.Data;

/**
 * @Describle This Class Is 字段需和InterfaceConfig保持一致
 * @Author ZengMin
 * @Date 2019/2/17 11:09
 */
@Data
public class SmsVo {

    private Integer code = 1;

    private String app_id;

    private String app_key;

    private String smsTemplateCode;

    private String smsTemplate;

    private String smsSignName;

    private Integer type = CommonConstant.InterfaceConfig.PHONE_TYPE.getCode();

    private Integer status = 0;

    public SmsVo(Integer code, String APPID, String APPKEY, String templateCode, String smsTemplate, String signName,Integer status) {
        this.code = code;
        this.app_id = APPID;
        this.app_key = APPKEY;
        this.smsTemplateCode = templateCode;
        this.smsTemplate = smsTemplate;
        this.smsSignName = signName;
        this.status = status;
        this.type = CommonConstant.InterfaceConfig.PHONE_TYPE.getCode();
    }
    public SmsVo() {
    }
}
