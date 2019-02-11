package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.CommonLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/4 20:43
 */
public interface CommonLogRepository extends JpaRepository<CommonLog,String> {
}
