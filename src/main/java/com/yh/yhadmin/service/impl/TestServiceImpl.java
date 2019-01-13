package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Test;
import com.yh.yhadmin.repository.TestRepository;
import com.yh.yhadmin.service.TestService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/3 21:15
 * @Company Matt
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Override
    public List<Test> initTable() {
        char start = 'a';
        List<Test> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Test t = new Test(String.valueOf(start++), i % 2 == 1, new Date());
            list.add(t);
        }
        List<Test> tests = testRepository.saveAll(list);
        return tests;
    }

    @Override
    @HandlerMethod(optName = "test查询",optDesc = "查询全部")
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    @HandlerMethod(optName = "test查询",optDesc = "按名称查询")
    public List<Test> findByName(String name) {
        return testRepository.findByName(name);
    }
}
