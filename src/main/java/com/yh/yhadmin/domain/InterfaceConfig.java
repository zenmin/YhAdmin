package com.yh.yhadmin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 15:43
 * @Company
 */
@Data
@Table
@Entity
@EqualsAndHashCode
public class InterfaceConfig {

    @Column
    private String interface_type;

    @Column(columnDefinition = "int(10) default 0 COMMENT '1启用 0禁用 默认'")
    private String status;

    @Column
    private String APP_ID;

    @Column
    private String APP_KEY;

    @Column
    private String payWay;

    @Column
    private String mailSMTP;

    @Column
    private String mailPort;

    @Column
    private String mailAccount;

    @Column
    private String mailPwd;

    @Column
    private String mailContent;

    @Column
    private String switch_alipay;

    @Column
    private String switch_qq;

    @Column
    private String switch_wx;

    @Column
    private String index_style;

    public InterfaceConfig() {
    }

    public InterfaceConfig(String interface_type, String status, String APP_ID, String APP_KEY, String payWay, String mailSMTP, String mailPort, String mailAccount, String mailPwd, String mailContent, String switch_alipay, String switch_qq, String switch_wx, String index_style) {
        this.interface_type = interface_type;
        this.status = status;
        this.APP_ID = APP_ID;
        this.APP_KEY = APP_KEY;
        this.payWay = payWay;
        this.mailSMTP = mailSMTP;
        this.mailPort = mailPort;
        this.mailAccount = mailAccount;
        this.mailPwd = mailPwd;
        this.mailContent = mailContent;
        this.switch_alipay = switch_alipay;
        this.switch_qq = switch_qq;
        this.switch_wx = switch_wx;
        this.index_style = index_style;
    }
}
