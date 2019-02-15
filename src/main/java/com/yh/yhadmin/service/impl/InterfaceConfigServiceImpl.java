package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.InterfaceConfig;
import com.yh.yhadmin.repository.InterfaceConfigRepository;
import com.yh.yhadmin.service.InterfaceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:10
 * @Company
 */
@Service
public class InterfaceConfigServiceImpl implements InterfaceConfigService {
    @Autowired
    InterfaceConfigRepository interfaceConfigRepository;

    public List<InterfaceConfig> findAll(){
        return interfaceConfigRepository.findAll();
    }

    @HandlerMethod(optName = "interfaceConfig 接口配置",optDesc = "新增配置")
    @Transactional
    public InterfaceConfig save(InterfaceConfig interfaceConfig){return interfaceConfigRepository.save(interfaceConfig);}

    @HandlerMethod(optName = "interfaceConfig 接口配置",optDesc = "删除配置")
    @Transactional
    public boolean delete(String id){
        interfaceConfigRepository.deleteById(id);
        return true;
    }

    @Override
    public InterfaceConfig findByType(String typeCode) {
        return interfaceConfigRepository.findByInterfaceType(typeCode);
    }
}
