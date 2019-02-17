package com.yh.yhadmin.util;

import com.yh.yhadmin.domain.vo.MailVo;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.InterfaceConfigService;
import lombok.extern.slf4j.Slf4j;
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
public class MailUtil {

    @Autowired
    InterfaceConfigService interfaceConfigService;

    @Async
    public void sendMail(String receiveUser, String content) {
        try {
            String username = "";
            String password = "";
            String host = "";
            String title = "";

            Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.MAIL_TYPE.getValue());
            MailVo mailVo = (MailVo) byType;
            username = mailVo.getMailAccount();
            password = mailVo.getMailPwd();
            host = mailVo.getMailSMTP();
            title = mailVo.getMailTitle();

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
            mimeMessageHelper.setFrom("741703967@qq.com");
            mimeMessageHelper.setValidateAddresses(false);
            // 添加邮件附件
            // mimeMessageHelper.addAttachment("cs.png",new File("C:\\Users\\74170\\Pictures\\FLAMING MOUNTAIN.png"));
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败");
        }

    }


}
