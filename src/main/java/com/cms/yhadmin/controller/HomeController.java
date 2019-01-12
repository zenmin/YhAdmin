package com.cms.yhadmin.controller;

import com.cms.yhadmin.foundation.CommonException;
import com.cms.yhadmin.foundation.DefinedCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/qd")
    public String testException(){
        return "qd";
    }

}
