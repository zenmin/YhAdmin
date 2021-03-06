package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 15:51
 * @Company
 */
@Data
@Table(name = "webconfig")
@Entity
@EqualsAndHashCode(callSuper = true)
public class WebConfig extends Model {

    @Column(columnDefinition = "int(11) default 1 COMMENT '网站状态 默认开启'")
    private Integer webStatus;

    @Column(columnDefinition = "varchar(20) COMMENT '网站域名 支付用'")
    private String webUrl;

    @Column(columnDefinition = "varchar(255) COMMENT '主标题'")
    private String mainTitle;

    @Column(columnDefinition = "varchar(255) COMMENT '副标题'")
    private String subTitle;

    @Column(columnDefinition = "varchar(255) COMMENT '标题描述'")
    private String titleDesc;

    @Column(columnDefinition = "varchar(255) COMMENT '网站关键词'")
    private String keyWords;

    @Column(columnDefinition = "text COMMENT '主页公告'")
    private String mainNotice;

    @Column(columnDefinition = "text COMMENT '订单页面公告'")
    private String subNotice;

    @Column(columnDefinition = "varchar(255) COMMENT '版权'")
    private String copyRight;

    @Column(columnDefinition = "int(1) default 1 COMMENT '是否显示库存'")
    private Integer showStock;

    @Column(columnDefinition = "int(11) default 10 COMMENT '库存低于多少时邮件提醒'")
    private Integer kmNotice;

    @Column(columnDefinition = "varchar(255) COMMENT '模板路径'")
    private String wbeStyle;

    @Column(columnDefinition = "varchar(255) COMMENT '网站logo'")
    private String logo;

    @Column(columnDefinition = "varchar(255) COMMENT '网站背景图'")
    private String bgImg;

    @Column(columnDefinition = "varchar(255) COMMENT '超级管理员邮箱'")
    private String adminEmail;

    @Transient
    private String adminQQ;

    @Transient
    private Integer emailSwitch;

    @Transient
    private Integer smsSwitch;


    public WebConfig(String id) {
        super.setId(id);
    }
    public WebConfig() {
    }
}
