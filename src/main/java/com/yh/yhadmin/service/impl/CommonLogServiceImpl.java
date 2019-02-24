package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.CommonLog;
import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.CommonLogRepository;
import com.yh.yhadmin.service.CommonLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/4 20:44
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

    @Override
    public Object findAll(Pager pager) {
        PageRequest pageRequest = new PageRequest(pager.getStart(), pager.getSize(),Sort.Direction.DESC,"createDate");
        return commonLogRepository.findAll(pageRequest);
    }

    @Override
    public Object getByCondition(CommonLog commonLog, Pager pager) {
        commonLog.setCreateDate(null);
        commonLog.setDate(null);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("requestURL", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CommonLog> of = Example.of(commonLog,exampleMatcher);
        PageRequest pageRequest = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.DESC, "createDate");
        return commonLogRepository.findAll(of,pageRequest);
    }

}
