package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Describle This Class Is 商品表
 * @Author ZengMin
 * @Date 2019/1/16 20:09
 */
@Data
@Entity
@Table(name = "goods")
@EqualsAndHashCode(callSuper = true)
public class Goods extends Model {

    @Column
    private String name;

    @Column
    private String goodsDesc;

    @Column
    private String img;

    @Column
    private String cid;

    @Column(columnDefinition = "int(11) default 1 COMMENT '1上架 默认 0下架'")
    private Integer status;

    @Column
    private Double price;

    @Column(columnDefinition = "bit(1) default 0 COMMENT '是否需要提取密码'")
    private Boolean needPwd;

    @Column
    private String pullPwd;

    public Goods(String name, String goodsDesc, String img, String cid, Integer status, Double price, Boolean needPwd, String pullPwd) {
        this.name = name;
        this.goodsDesc = goodsDesc;
        this.img = img;
        this.cid = cid;
        this.status = status;
        this.price = price;
        this.needPwd = needPwd;
        this.pullPwd = pullPwd;
    }

    public Goods() {
    }
}
