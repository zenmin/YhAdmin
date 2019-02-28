package com.yh.yhadmin.util;

import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.domain.vo.MailVo;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.InterfaceConfigService;
import com.yh.yhadmin.service.WebConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Describle This Class Is 邮件发送工具类
 * @Author ZengMin
 * @Date 2019/2/17 13:47
 */
@Component
@Slf4j
public class EmailUtil {

    @Autowired
    InterfaceConfigService interfaceConfigService;

    @Autowired
    WebConfigService webConfigService;

    /**
     * 发送邮件主方法 不要直接调
     * @param userTitle
     * @param receiveUser
     * @param content
     */
    @Async
    public void sendMail(String userTitle,String receiveUser, String content) {
        try {
            String username = "";
            String password = "";
            String host = "";
            String title = "";

            Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.MAIL_TYPE.getValue());
            MailVo mailVo = (MailVo) byType;//new MailVo(1,"smtp.qq.com","283339824@qq.com","sycuadpizhwmbgdg","标题","内容！");
            username = mailVo.getMailAccount();
            password = mailVo.getMailPwd();
            host = mailVo.getMailSMTP();
            if (StringUtils.isBlank(userTitle)) {
                title = mailVo.getMailTitle();
            } else {
                title = userTitle;
            }
            //设置发件属性
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setUsername(username);
            javaMailSender.setPassword(password);
            javaMailSender.setHost(host);
            Properties properties = new Properties();
            properties.setProperty("spring.mail.properties.mail.smtp.ssl.enable", "true");
            javaMailSender.setDefaultEncoding("UTF-8");
            javaMailSender.setJavaMailProperties(properties);

            //邮件内容
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, false);  //multipart=true 表示这是一个可以上传附件的消息
            mimeMessageHelper.setTo(receiveUser);     //收件人地址不对  会抛出550 Invalid Addresses
            mimeMessageHelper.setText(content, true);     //表明这是一个html片段
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setFrom(mailVo.getMailAccount());
            mimeMessageHelper.setValidateAddresses(false);
            // 添加邮件附件
            // mimeMessageHelper.addAttachment("cs.png",new File("C:\\Users\\74170\\Pictures\\FLAMING MOUNTAIN.png"));
            javaMailSender.send(mimeMailMessage);
            log.info("邮件发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败");
        }

    }


    @Async
    public void sendErrorToAdmin(String title, String params, String erorMessage) {
        // 给超级管理员发送邮件
        WebConfig all = webConfigService.findAll();
        String adminEmail = all.getAdminEmail();
        String contentMail = title + " 时间：" + DateUtil.getNowTime() + "，请分析参数，错误信息：" + erorMessage;
        params = " 参数：" + params;
        if (null == adminEmail) {
            log.error("未配置管理员邮箱，取消发送错误信息！");
        } else {
            this.sendMail(title, adminEmail, contentMail + params + " \n <b>请将此信息发送给开发者协助解决！</b>");
        }
    }

    @Async
    public void sendMsgToAdmin(String title, String content) {
        // 给超级管理员发送邮件
        WebConfig all = webConfigService.findAll();;
        String adminEmail = all.getAdminEmail();
        if (StringUtils.isBlank(adminEmail)) {
            log.error("未配置管理员邮箱，取消发送信息！");
        } else {
            this.sendMail(title, adminEmail, content);
        }
    }

}
