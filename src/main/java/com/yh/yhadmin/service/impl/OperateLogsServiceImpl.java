package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.repository.OperateLogsRepository;
import com.yh.yhadmin.service.OperateLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * @Describle This Class Is 日志保存
 * @Author ZengMin
 * @Date 2019/1/13 16:58
 */
@Service
public class OperateLogsServiceImpl implements OperateLogsService {

    @Autowired
    OperateLogsRepository operateLogsRepository;

    @Async
    @Override
    @Transactional
    public OperateLogs saveLogs(OperateLogs operateLogs) {
        OperateLogs save = operateLogsRepository.save(operateLogs);
        return save;
    }
}
