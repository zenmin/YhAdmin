package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.InterfaceConfig;
import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:04
 * @Company
 */
public interface InterfaceConfigService {

    List<InterfaceConfig> findAll();

    InterfaceConfig save(InterfaceConfig interfaceConfig);

    boolean delete(String id);

    InterfaceConfig findByType(String typeCode);

}
