package com.yh.yhadmin.domain.vo;

import com.yh.yhadmin.foundation.constant.CommonConstant;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 15:46
 */
@Data
public class PayConfigVo {

    private Integer type = CommonConstant.InterfaceConfig.PAY_TYPE.getCode();

    private String payType;

    private String app_id;

    private String app_key;

    public PayConfigVo(String payType, String app_id, String app_key) {
        this.payType = payType;
        this.app_id = app_id;
        this.app_key = app_key;
    }

    public PayConfigVo() {
    }
}
