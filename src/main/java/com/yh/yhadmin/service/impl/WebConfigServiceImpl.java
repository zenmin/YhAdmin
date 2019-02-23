package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.WebConfigRepository;
import com.yh.yhadmin.service.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:19
 * @Company
 */
@Service
@CacheConfig(cacheNames = "WebConfig")
public class WebConfigServiceImpl implements WebConfigService {

    @Autowired
    WebConfigRepository webConfigRepository;

    @Cacheable
    public WebConfig findAll(){
        WebConfig config = webConfigRepository.findAllById(CommonConstant.CONFIG_ID);
        if(null == config){
            config = new WebConfig(CommonConstant.CONFIG_ID);
            webConfigRepository.insert(CommonConstant.CONFIG_ID);
        }
        return config;
    }

    @CacheEvict(allEntries = true)
    @HandlerMethod(optName = "平台基本信息配置",optDesc = "更新配置")
    @Transactional
    public WebConfig save(WebConfig webConfig){
        webConfig.setId(CommonConstant.CONFIG_ID);
        return webConfigRepository.saveAndFlush(webConfig);
    }

    @Override
    @Async
    public void updateAdminEmail(String adminEmail) {
        webConfigRepository.updateAdminEmail(adminEmail);
    }
}
