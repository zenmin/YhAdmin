package com.yh.yhadmin.domain.vo;

import com.yh.yhadmin.domain.base.Model;
import lombok.Data;

/**
 * @Describle This Class Is 商品表
 * @Author ZengMin
 * @Date 2019/1/16 20:09
 */
@Data
public class GoodsVoHome extends Model {

    private String id;

    private String name;

    private String goodsDesc;

    private String img;

    private String cid;

    private Double price;

    private Boolean needPwd;

    private String cname;

    private long kmCount;
}
