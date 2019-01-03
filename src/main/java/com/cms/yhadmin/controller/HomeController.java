package com.cms.yhadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/3 20:26
 * @Company Matt
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String toHome(){
        return "index";
    }

    @RequestMapping("/api")
    public String toApi(){
        return "api/web_api";
    }


}
