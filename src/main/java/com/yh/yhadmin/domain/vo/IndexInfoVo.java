package com.yh.yhadmin.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/16 15:05
 */
@Data
public class IndexInfoVo {

    private Integer orderUsers;

    private Long cardPwds;

    private Double nowPrice;

    private Integer orderNum;

    private List<Map<String,Object>> sevenOrders;

    private List<Map<String,Object>> payAlias;

    private List<Map<String,Object>> finishOrderAlias;

}
