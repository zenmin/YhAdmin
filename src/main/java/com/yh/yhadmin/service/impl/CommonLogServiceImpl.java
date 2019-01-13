package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.CommonLog;
import com.yh.yhadmin.repository.CommonLogRepository;
import com.yh.yhadmin.service.CommonLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/4 20:44
 * @Company Matt
 */
@Service
public class CommonLogServiceImpl implements CommonLogService {

    @Autowired
    CommonLogRepository commonLogRepository;

    @Async
    @Override
    public void saveLog(CommonLog commonLog) {
        commonLogRepository.save(commonLog);
    }
}