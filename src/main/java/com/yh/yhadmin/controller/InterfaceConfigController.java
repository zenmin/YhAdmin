package com.yh.yhadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yh.yhadmin.domain.InterfaceConfig;
import com.yh.yhadmin.domain.vo.MailVo;
import com.yh.yhadmin.domain.vo.SmsVo;
import com.yh.yhadmin.domain.vo.TemplateVo;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.InterfaceConfigService;
import com.yh.yhadmin.util.MailUtil;
import com.yh.yhadmin.util.MapConvertUtil;
import com.yh.yhadmin.util.SmsUtil;
import com.yh.yhadmin.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:29
 * @Company
 */
@RestController
@RequestMapping("/api/interfaceConfig")
public class InterfaceConfigController {

    @Autowired
    InterfaceConfigService interfaceConfigService;

    @Autowired
    SmsUtil smsUtil;

    @Autowired
    MailUtil mailUtil;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/getAll")
    public ResponseEntity findAll() {
        return ResponseEntity.success(interfaceConfigService.findAll());
    }

    @RequestMapping("/save")
    public ResponseEntity save(InterfaceConfig interfaceConfig) {
        InterfaceConfig save = interfaceConfigService.save(interfaceConfig);
        return ResponseEntity.success(save != null);
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(Integer type) throws IOException {
        Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(type));
        if (type == CommonConstant.InterfaceConfig.INDEX_STYLE.getCode()) {
            List<Map<String, String>> temps = Lists.newArrayList();
            //取resource下所有模板
            ClassPathResource classPathResource = new ClassPathResource("templates/webtemps");
            File file = classPathResource.getFile();
            File[] files = file.listFiles(File::isDirectory);
            for (File f : files) {
                Map<String, String> map = new HashMap<>();
                String path = "webtemps/" + f.getName();
                map.put("path", path + "/index");
                map.put("name", f.getName());
                map.put("img", path + "/index.jpg");
                temps.add(map);
            }
            return ResponseEntity.success(new TemplateVo(byType.toString(), temps));
        }
        return ResponseEntity.success(byType);
    }

    @RequestMapping("/testInterface")
    public ResponseEntity testInterface(Integer type) throws JsonProcessingException {
        if (type == 1) {
            Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(type));
            SmsVo smsVo = (SmsVo) byType;
            String smsTemplate = smsVo.getSmsTemplate();
            Map<String, Object> map = new HashMap<>();
            map.put(smsTemplate, "123456");
            String s = MapConvertUtil.objectMapper.writeValueAsString(map);
            smsUtil.sendSms("15228766049", s);
        }

        if (type == 2) {
            String km = "1wd12edwef";
            String orderNo = StaticUtil.uniqueKeyByTime(new Date());
            Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(type));
            MailVo mailVo = (MailVo) byType;
            String mailContent = mailVo.getMailContent();
            String content = StaticUtil.convertMailContent(mailContent, km, orderNo);
            mailUtil.sendMail("15228766049@163.com", content);
        }

        return ResponseEntity.success(true);
    }

}

