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
 * @Describle This Class Is 优惠券
 * @Author ZengMin
 * @Date 2019/1/19 17:44
 */
@Entity
@Table(name = "coupon")
@Data
@EqualsAndHashCode(callSuper = true)
public class Coupon extends Model {

    @Column(columnDefinition = "varchar(10) COMMENT '优惠券码'",unique = true)
    private String couponNo;

    @Column(columnDefinition = "int(11) default 0 COMMENT '是否长期有效 默认否'")
    private Integer validLong;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '失效时间 针对长期优惠券'")
    private Date disDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '使用时间 针对一次性优惠券'")
    private Date useDate;

    @Column(columnDefinition = "varchar(255) COMMENT '使用人 联系方式'")
    private String useUser;

    @Column
    private String createUser;

    @Column(columnDefinition = "int(11) default 1 COMMENT '是否启用 默认是'")
    private Integer status;

    @Column(columnDefinition = "int(11) default 100 COMMENT '折扣百分比 默认100'")
    private Integer saleRate;

    @Column(columnDefinition = "varchar(500) COMMENT '优惠券描述'")
    private String couponDesc;

    @Transient
    private String disDateField;


    public Coupon(String couponNo, Integer validLong, Date disDate, Date useDate, String useUser, String createUser, Integer status, Integer saleRate, String couponDesc) {
        this.couponNo = couponNo;
        this.validLong = validLong;
        this.disDate = disDate;
        this.useDate = useDate;
        this.useUser = useUser;
        this.createUser = createUser;
        this.status = status;
        this.saleRate = saleRate;
        this.couponDesc = couponDesc;
    }

    public Coupon() {
    }
}
