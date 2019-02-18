package com.yh.yhadmin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Describle This Class Is 全局Controller 发卡首页
 * @Author ZengMin
 * @Date 2019/1/3 20:26
 */
@Controller
public class HomeController {

    @Value("${spring.profiles.active}")
    private String env;

    @RequestMapping("/")
    public String toHome(){
        return "index";
    }

    @RequestMapping("/api")
    public String toApi(){
        return "api/web_api";
    }


}
