package com.yh.yhadmin.domain.vo;

import com.yh.yhadmin.foundation.constant.CommonConstant;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 15:04
 */
@Data
public class PaySwitchVo {

    private Integer type = CommonConstant.InterfaceConfig.PAY_SWITCH_TYPE.getCode();

    private Integer switch_alipay;

    private Integer switch_qq;

    private Integer switch_wx;


    public PaySwitchVo(Integer switch_alipay, Integer switch_qq, Integer switch_wx) {
        this.switch_alipay = switch_alipay;
        this.switch_qq = switch_qq;
        this.switch_wx = switch_wx;
    }

    public PaySwitchVo() {
    }
}
