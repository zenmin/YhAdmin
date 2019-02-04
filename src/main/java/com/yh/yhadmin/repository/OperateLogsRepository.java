package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.OperateLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/13 16:57
 */
public interface OperateLogsRepository extends JpaRepository<OperateLogs,String> {

    Page findByOptDesc(String optDesc, Pageable pageable);
}
