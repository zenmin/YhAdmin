package com.yh.yhadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle This Class Is 支付相关
 * @Author ZengMin
 * @Date 2019/2/17 15:35
 */
@RestController
@RequestMapping("/api/pay")
public class PayController {

    @RequestMapping("/callBack")
    public void payCalllBack()  {

    }

}
