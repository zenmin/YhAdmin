package com.yh.yhadmin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

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
    private String orderNo;

    @Column(columnDefinition = "int(11) default 0 COMMENT '1已完成 0未完成'")
    private Integer status;

    @Column
    private String goodsId;

    @Column
    private Double price;

    @Column
    private Double allPrice;

    @Column
    private Integer num;

    @Column
    private String payWay;

    @Column(columnDefinition = "int(11) default 0 COMMENT '1已付款 0待支付 2支付超时'")
    private Integer payStatus;

    @Column
    private Double payPrice;

    @Column
    private String payId;

    @Column
    private String couponNo;

    @Column
    private String ip;

    @Column
    private String userContact;

    @Column(columnDefinition = "int(11) default 0 COMMENT '是否发送短信1发送 0不发送'")
    private Integer isSendMsg;

    @Column(columnDefinition = "int(11) default 0 COMMENT '是否发送邮件1发送 0不发送'")
    private Integer isSendEmail;

    @Column
    private String email;

    @Column(length = 2000)
    private String cardPwds;

    @Column
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModifyDate;

    @Transient
    private String goodsName;

    @Transient
    private String beginTime;

    @Transient
    private String endTime;

    public Orders(String orderNo, Integer status, String goodsId, Double price, Double allPrice, Integer num, String payWay, Integer payStatus, String couponNo, String ip, String userContact, Integer isSendMsg, Integer isSendEmail, String email, Date lastModifyDate) {
        this.orderNo = orderNo;
        this.status = status;
        this.goodsId = goodsId;
        this.price = price;
        this.allPrice = allPrice;
        this.num = num;
        this.payWay = payWay;
        this.payStatus = payStatus;
        this.couponNo = couponNo;
        this.ip = ip;
        this.userContact = userContact;
        this.isSendMsg = isSendMsg;
        this.isSendEmail = isSendEmail;
        this.email = email;
        this.lastModifyDate = lastModifyDate;
    }

    public Orders() {
    }
}
