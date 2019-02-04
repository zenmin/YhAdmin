package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.domain.query.Pager;
import org.springframework.data.domain.Page;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/13 16:58
 */
public interface OperateLogsService {

    OperateLogs saveLogs(OperateLogs operateLogs);

    Page findByOptDesc(String desc, Pager pager);

    Page findAll(Pager pager);

    Page getByCondition(OperateLogs operateLogs,Pager pager);

}
