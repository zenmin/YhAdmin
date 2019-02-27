package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.WebConfigRepository;
import com.yh.yhadmin.repository.impl.BaseNativeRepository;
import com.yh.yhadmin.service.WebConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:19
 * @Company
 */
@Service
@CacheConfig(cacheNames = "WebConfig")
@Slf4j
public class WebConfigServiceImpl implements WebConfigService {

    @Autowired
    WebConfigRepository webConfigRepository;

    @Autowired
    BaseNativeRepository baseNativeRepository;

    @Cacheable
    @PostConstruct
    public WebConfig findAll() {
        WebConfig config = webConfigRepository.findAllById(CommonConstant.CONFIG_ID);
        // 如果为空 初始化数据
        if (null == config) {
            baseNativeRepository.execInitSql();
            System.out.println("初始化数据完成！");
            return webConfigRepository.findAllById(CommonConstant.CONFIG_ID);
        } else {
            return config;
        }
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
