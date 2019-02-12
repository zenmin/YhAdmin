package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:32
 * @Company
 */
@RestController
@RequestMapping("api/webConfig")
public class WebConfigController {
    @Autowired
    WebConfigService webConfigService;

    @RequestMapping("getAll")
    public ResponseEntity findAll(){
        return ResponseEntity.success(webConfigService.findAll());
    }

    @RequestMapping("save")
    public  ResponseEntity save(WebConfig webConfig){
        return ResponseEntity.success(webConfigService.save(webConfig));
    }
}
