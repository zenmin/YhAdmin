package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Describle This class is Orders(订单表)
 * @Author Li lei
 * @Date 2019/1/29 0029 14:24
 * @Company
 */
@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Orders extends Model {

    @Column
    private String orederNo;

    @Column
    private Integer status;

    @Column
    private String goodsId;

    @Column
    private Integer price;

    @Column
    private Integer allPrice;

    @Column
    private Integer num;

    @Column
    private String payWay;

    @Column(columnDefinition = "int(10) default 0 COMMENT '1已付款 0未付款 默认'")
    private String payStatus;

   @Column
    private Integer payPrice;

    @Column
    private String coupon;

    @Column
    private String userContact;

    @Column
    private Integer phone;

    @Column(length = 2000)
    private String cardPwds;

    public Orders() {
    }

    public Orders(String orederNo, Integer status, String goodsId, Integer price, Integer allPrice, Integer num, String payWay, String payStatus, Integer payPrice, String coupon, String userContact, Integer phone, String cardPwds) {
        this.orederNo = orederNo;
        this.status = status;
        this.goodsId = goodsId;
        this.price = price;
        this.allPrice = allPrice;
        this.num = num;
        this.payWay = payWay;
        this.payStatus = payStatus;
        this.payPrice = payPrice;
        this.coupon = coupon;
        this.userContact = userContact;
        this.phone = phone;
        this.cardPwds = cardPwds;
    }
}
