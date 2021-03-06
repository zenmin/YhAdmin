package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.InterfaceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 15:57
 * @Company
 */
public interface InterfaceConfigRepository extends JpaRepository<InterfaceConfig,String> {

    InterfaceConfig findByInterfaceType(String type);
}
