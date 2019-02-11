package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.CommonLog;
import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.domain.query.Pager;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/4 20:43
 */
public interface CommonLogService {

    void saveLog(CommonLog commonLog);

    Object findAll(Pager pager);

    Object getByCondition(CommonLog commonLog, Pager pager);
}
