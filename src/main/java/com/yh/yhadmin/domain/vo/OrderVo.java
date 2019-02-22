package com.yh.yhadmin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/21 20:55
 */
@Data
public class OrderVo {

    private String id;  //orderId

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;    // 创建时间

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payDate;       // 支付时间

    private String goodsId;     // 商品id

    private String goodsName;     // 商品id

    private String payType;     // 支付方式

    private Integer number;     // 数量

    private Double allPrice;    // 总价

    private Double price;    // 单价

    private String userContact;     // 联系方式

    private String coupon;      // 优惠券编码

    private Integer isSendMsg;  // 是否发送短信

    private Integer isSendEmail; // 是否发送邮件

    private String email;       // 邮箱

    private String kms;         // 卡密

    private Integer status;     //订单状态

    private String ip;          //下单ip

    private String pullPwd;     // 提取密码

    public OrderVo(Date createDate, Date payDate, String goodsId, String payType, Integer number, Double allPrice, String userContact, String coupon, Integer isSendMsg, Integer isSendEmail, String email, String kms, Integer status) {
        this.createDate = createDate;
        this.payDate = payDate;
        this.goodsId = goodsId;
        this.payType = payType;
        this.number = number;
        this.allPrice = allPrice;
        this.userContact = userContact;
        this.coupon = coupon;
        this.isSendMsg = isSendMsg;
        this.isSendEmail = isSendEmail;
        this.email = email;
        this.kms = kms;
        this.status = status;
    }

    public OrderVo() {
    }
}
