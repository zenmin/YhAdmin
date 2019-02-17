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
 * @Date 2019/2/3 0003 15:43
 * @Company
 */
@Data
@Entity
@Table(name = "interfaceconfig")
@EqualsAndHashCode(callSuper = true)
public class InterfaceConfig extends Model {

    @Column(columnDefinition = "varchar(255) COMMENT '接口类型'")
    private String interfaceType;

    @Column(columnDefinition = "int(10) default 0 COMMENT '1启用 0禁用 默认'")
    private Integer status;

    @Column
    private String app_id;

    @Column
    private String app_key;

    @Column(columnDefinition = "varchar(255) COMMENT '支付方式'")
    private String payWay;

    @Column(columnDefinition = "varchar(255) COMMENT '邮箱SMTP服务器'")
    private String mailSMTP;

    @Column(columnDefinition = "varchar(255) COMMENT '支付接口类型'")
    private String payType;

    @Column(columnDefinition = "varchar(255) COMMENT '邮箱账号'")
    private String mailAccount;

    @Column(columnDefinition = "varchar(255) COMMENT '邮箱密码'")
    private String mailPwd;

    @Column(columnDefinition = "varchar(10000) COMMENT '邮件标题'")
    private String mailTitle;

    @Column(columnDefinition = "varchar(10000) COMMENT '邮件内容'")
    private String mailContent;

    @Column(columnDefinition = "int(11) COMMENT '支付宝支付开关'")
    private Integer switch_alipay;

    @Column(columnDefinition = "int(11) default 1 COMMENT 'QQ支付开关'")
    private Integer switch_qq;

    @Column(columnDefinition = "int(11) default 1 COMMENT '微信支付开关'")
    private Integer switch_wx;

    @Column(columnDefinition = "varchar(100) COMMENT '首页模板路径'")
    private String index_style;

    @Column(columnDefinition = "varchar(50) COMMENT '短信模板code '")
    private String smsTemplateCode;

    @Column(columnDefinition = "varchar(500) COMMENT '短信模板'")
    private String smsTemplate;

    @Column(columnDefinition = "varchar(50) COMMENT '短信签名'")
    private String smsSignName;

    @Transient
    private Integer type;
}
