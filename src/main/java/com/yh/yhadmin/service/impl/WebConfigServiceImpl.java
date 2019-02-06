package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.repository.WebConfigRepository;
import com.yh.yhadmin.service.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<WebConfig> findAll(){
        return webConfigRepository.findAll();
    }

    @HandlerMethod(optName = "webConfig 官网配置",optDesc = "新增配置")
    @Transactional
    public WebConfig save(WebConfig webConfig){return webConfigRepository.save(webConfig);}

    @HandlerMethod(optName = "webConfig 官网配置",optDesc = "删除配置")
    @Transactional
    public boolean delete(String id){
        webConfigRepository.deleteById(id);
        return true;
    }
}
