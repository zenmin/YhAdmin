package com.cms.yhadmin.controller;

import com.cms.yhadmin.domain.Test;
import com.cms.yhadmin.foundation.CommonException;
import com.cms.yhadmin.foundation.DefinedCode;
import com.cms.yhadmin.foundation.ResponseEntity;
import com.cms.yhadmin.service.TestService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/3 21:13
 * @Company Matt
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("/crud")
    public ResponseEntity test(Integer type, String name){
        List<Test> list = Lists.newArrayList();
        switch (type){
            case 1:
                list = testService.findAll();
                break;
            case 2:
                list = testService.findByName(name);
                break;
            case 3:
                list = testService.initTable();
                break;
                default:
                    throw new CommonException(DefinedCode.PARAMSERROR,"参数异常！");
        }
        return ResponseEntity.success(list);
    }


}
