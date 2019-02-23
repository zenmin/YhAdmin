package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.OperateLogsRepository;
import com.yh.yhadmin.service.OperateLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

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
    public void saveLogs(OperateLogs operateLogs) {
        OperateLogs save = operateLogsRepository.save(operateLogs);
    }

    @Override
    public Page findByOptDesc(String desc, Pager pager) {
        PageRequest pageRequest = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.DESC, "createDate");
        Page byOptDesc = operateLogsRepository.findByOptDesc(desc, pageRequest);
        return byOptDesc;
    }

    @Override
    public Page findAll(Pager pager) {
        PageRequest pageRequest = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.DESC, "createDate");
        Page page = operateLogsRepository.findAll(pageRequest);
        return page;
    }

    @Override
    public Page getByCondition(OperateLogs operateLogs, Pager pager) {
        operateLogs.setCreateDate(null);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("optName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("optUser",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("optDesc",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<OperateLogs> of = Example.of(operateLogs,exampleMatcher);
        PageRequest pageRequest = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.DESC, "createDate");
        Page<OperateLogs> all = operateLogsRepository.findAll(of, pageRequest);
        return all;
    }
}
