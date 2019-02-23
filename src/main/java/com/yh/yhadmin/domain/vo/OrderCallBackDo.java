package com.yh.yhadmin.domain.vo;

import lombok.Data;

/**
 * @Describle This Class Is 码支付回调
 * @Author ZengMin
 * @Date 2019/2/23 11:49
 */
@Data
public class OrderCallBackDo {

    private String pay_id;  // orderNo

    private String money;  // 付款金额

    private String price;  // 订单原价

    private String type;  // 支付方式1：支付宝 2：QQ钱包 3：微信支付。默认值：1

    private String pay_no;  // 流水号

    private String param;  // 创建订单时的参数

    private String pay_time;  // 付款时间 时间戳

    private String pay_tag;  // 付款备注

    private String sign;  // 数据签名

}
