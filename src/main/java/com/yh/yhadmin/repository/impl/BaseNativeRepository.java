package com.yh.yhadmin.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/27 9:53
 */
@Slf4j
@Repository
public class BaseNativeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public boolean execInitSql() {
        try {
            jdbcTemplate.update("INSERT INTO adminuser(id, createDate, isAdministrator, lastloginIP, passWord, phone, qq, realName, status, userName, wx, lastloginTime, adminEmail) VALUES ('36202959eac846e6baed025f11888d46', '1900-01-01 00:00:51', 1, '127.0.0.1', '3acddafbea6fb6fe40dc96a3f1bb16df', NULL, NULL, '超级管理员', 1, 'admin', NULL, NULL, NULL);");
            jdbcTemplate.update("INSERT INTO interfaceconfig(id, createDate, APP_ID, APP_KEY, index_style, mailAccount, mailContent, payType, mailPwd, mailSMTP, payWay, status, interfaceType, smsSignName, smsTemplate, smsTemplateCode, mailTitle, switch_alipay, switch_qq, switch_wx) VALUES ('1', '1900-01-01 00:00:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'PHONE_TYPE', NULL, 'code', NULL, NULL, 1, 1, 1);");
            jdbcTemplate.update("INSERT INTO interfaceconfig(id, createDate, APP_ID, APP_KEY, index_style, mailAccount, mailContent, payType, mailPwd, mailSMTP, payWay, status, interfaceType, smsSignName, smsTemplate, smsTemplateCode, mailTitle, switch_alipay, switch_qq, switch_wx) VALUES ('2', '1900-01-01 00:00:52', NULL, NULL, NULL, NULL, '<p style=\\\"text-align: center;\\\"><span style=\\\"color: #ff0000;\\\"><strong>你购买的卡密为${km}</strong> </span><br /><span style=\\\"color: #ff0000;\\\"> <strong>订单编号为${orderNo}</strong></span></p>\\n<p style=\\\"text-align: center;\\\">&nbsp;</p>\\n<p style=\\\"text-align: center;\\\"><span style=\\\"color: #ff0000;\\\"><strong>本站地址：http://baidu.com</strong></span></p>\\n<p style=\\\"text-align: center;\\\"><span style=\\\"color: #ff0000;\\\"><strong>欢迎再次下单</strong></span></p>', NULL, NULL, NULL, NULL, 0, 'MAIL_TYPE', NULL, NULL, NULL, '恭喜你，购买成功', NULL, NULL, NULL);");
            jdbcTemplate.update("INSERT INTO interfaceconfig(id, createDate, APP_ID, APP_KEY, index_style, mailAccount, mailContent, payType, mailPwd, mailSMTP, payWay, status, interfaceType, smsSignName, smsTemplate, smsTemplateCode, mailTitle, switch_alipay, switch_qq, switch_wx) VALUES ('3', '1900-01-01 00:00:52', NULL, NULL, NULL, NULL, NULL, 'MAPAY', NULL, NULL, NULL, NULL, 'PAY_TYPE', NULL, NULL, NULL, NULL, NULL, NULL, NULL);");
            jdbcTemplate.update("INSERT INTO interfaceconfig(id, createDate, APP_ID, APP_KEY, index_style, mailAccount, mailContent, payType, mailPwd, mailSMTP, payWay, status, interfaceType, smsSignName, smsTemplate, smsTemplateCode, mailTitle, switch_alipay, switch_qq, switch_wx) VALUES ('4', '1900-01-01 00:00:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'PAY_SWITCH_TYPE', NULL, NULL, NULL, NULL, 1, 1, 1);");
            jdbcTemplate.update("INSERT INTO interfaceconfig(id, createDate, APP_ID, APP_KEY, index_style, mailAccount, mailContent, payType, mailPwd, mailSMTP, payWay, status, interfaceType, smsSignName, smsTemplate, smsTemplateCode, mailTitle, switch_alipay, switch_qq, switch_wx) VALUES ('5', '1900-01-01 00:00:52', NULL, NULL, 'webtemps/default/index', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'INDEX_STYLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL);");
            jdbcTemplate.update("INSERT INTO webconfig(id, createDate, bgImg, copyRight, keyWords, logo, mainNotice, mainTitle, showStock, subNotice, subTitle, titleDesc, wbeStyle, webStatus, webUrl, kmNotice, adminEmail) VALUES ('0D16C6345DC045BFBA489D4C9C50F0D0', '1990-01-01 00:00:52', NULL, 'Copyright © 2015-2019 YHADMIN 版权所有', '发卡,yhadmin,个人发卡', '', '<h6 style=\\\"text-align: center;\\\"><span style=\\\"color: #ff0000;\\\">这是一个公告</span><br /><br /><span style=\\\"color: #00ff00;\\\">这是一个公告</span><br /><br /><span style=\\\"color: #0000ff;\\\">这是一个公告</span><br /><br /><span style=\\\"color: #ff6600;\\\">这是一个公告</span></h6>', '首页', 1, '<pre style=\\\"text-align: center;\\\"><span style=\\\"color: #ff0000;\\\">这是一个公告</span><br /><br /><span style=\\\"color: #00ff00;\\\">这是一个公告</span><br /><br /><span style=\\\"color: #0000ff;\\\">这是一个公告</span><br /><br /><span style=\\\"color: #ff6600;\\\">这是一个公告</span></pre>', '发卡', '专业发卡网站', NULL, 1, NULL, 10, NULL);");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
