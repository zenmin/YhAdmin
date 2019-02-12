package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.WebConfigRepository;
import com.yh.yhadmin.service.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:19
 * @Company
 */
@Service
public class WebConfigServiceImpl implements WebConfigService {

    @Autowired
    WebConfigRepository webConfigRepository;

    public WebConfig findAll(){
        WebConfig config = webConfigRepository.findAllById(CommonConstant.CONFIG_ID);
        if(null == config){
            config = new WebConfig(CommonConstant.CONFIG_ID);
            webConfigRepository.insert(CommonConstant.CONFIG_ID);
        }
        return config;
    }

    @HandlerMethod(optName = "平台基本信息配置",optDesc = "更新配置")
    @Transactional
    public WebConfig save(WebConfig webConfig){
        webConfig.setId(CommonConstant.CONFIG_ID);
        return webConfigRepository.saveAndFlush(webConfig);
    }
}
