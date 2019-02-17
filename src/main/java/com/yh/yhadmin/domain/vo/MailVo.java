package com.yh.yhadmin.domain.vo;

import com.yh.yhadmin.foundation.constant.CommonConstant;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 14:13
 */
@Data
public class MailVo {

    private Integer type = CommonConstant.InterfaceConfig.MAIL_TYPE.getCode();

    private Integer status;

    private String mailSMTP;

    private String mailAccount;

    private String mailPwd;

    private String mailTitle;

    private String mailContent;

    public MailVo(Integer status, String mailSMTP, String mailAccount, String mailPwd, String mailTitle, String mailContent) {
        this.status = status;
        this.mailSMTP = mailSMTP;
        this.mailAccount = mailAccount;
        this.mailPwd = mailPwd;
        this.mailTitle = mailTitle;
        this.mailContent = mailContent;
    }

    public MailVo() {
    }
}
