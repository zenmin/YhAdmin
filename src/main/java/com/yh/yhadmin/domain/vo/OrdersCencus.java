package com.yh.yhadmin.domain.vo;

import lombok.Data;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/13 0013 17:28
 * @Company
 * 本类为统计订单所需
 */
@Data
public class OrdersCencus {

    private String total;//订单总数

    private String complete;//交易完成

    private String total_today;//今日总数

    private String total_card;//卡密总数

    private String Surplus;//剩余卡密
}
