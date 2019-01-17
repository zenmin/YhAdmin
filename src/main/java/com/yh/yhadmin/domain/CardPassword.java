package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Describle This Class Is 卡密
 * @Author ZengMin
 * @Date 2019/1/17 20:05
 */
@Entity
@Table(name = "cardpassword")
@Data
@EqualsAndHashCode(callSuper = true)
public class CardPassword extends Model {

    @Column
    private String cardNo;      //卡密编码

    @Column
    private String goodsId;     //商品id

    @Column
    private String createUser;

    @Column
    private String createUserId;

    @Column(columnDefinition = "bit(1) default 0 COMMENT '1已提取 0未提取默认'")
    private Boolean status;

    @Column
    private Date useDate;   //提取时间

    @Column
    private String useUser; //提取人  ip

    @Transient
    private String goodsName;

    public CardPassword(String cardNo, String goodsId, String createUser, String createUserId, Boolean status, Date useDate, String useUser) {
        this.cardNo = cardNo;
        this.goodsId = goodsId;
        this.createUser = createUser;
        this.createUserId = createUserId;
        this.status = status;
        this.useDate = useDate;
        this.useUser = useUser;
    }

    public CardPassword() {
    }

}