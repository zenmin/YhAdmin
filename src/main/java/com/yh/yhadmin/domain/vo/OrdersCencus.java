package com.yh.yhadmin.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/13 0013 17:28
 * @Company
 * 本类为统计订单所需
 */
@Data
public class OrdersCencus {

    private Integer allCount;            // 订单总数

    private Integer successCount;        // 交易完成

    private Double allPrice;            // 成交金额

    private Integer allCardPwds;         // 卡密总数

    private Integer successCardPwds;     // 售出卡密

    private Integer consumerCount;       // 下单用户总数

    private List<Map<String,Object>> payAlias; // 支付方式分析

}
