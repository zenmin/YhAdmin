package com.yh.yhadmin.service;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Test;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/3 21:15
 * @Company Matt
 */
public interface TestService {

    List<Test> initTable();

    List<Test> findAll();

    List<Test> findByName(String name);

}
