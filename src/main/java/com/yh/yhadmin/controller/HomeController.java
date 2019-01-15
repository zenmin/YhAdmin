package com.yh.yhadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Describle This Class Is 全局跳转Controller
 * @Author ZengMin
 * @Date 2019/1/3 20:26
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String toHome(){
        return "api/web_api";
    }

    @RequestMapping("/api")
    public String toApi(){
        return "api/web_api";
    }

    @RequestMapping("/qd")
    public String testException(){
        return "qd";
    }

}
