package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.InterfaceConfig;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.InterfaceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:29
 * @Company
 */
@RestController
@RequestMapping("api/interfaceConfig")
public class InterfaceConfigController {

    @Autowired
    InterfaceConfigService interfaceConfigService;

    @RequestMapping("getAll")
    public ResponseEntity findAll(){
        return ResponseEntity.success(interfaceConfigService.findAll());
    }

    @RequestMapping("save")
    public  ResponseEntity save(InterfaceConfig InterfaceConfig){
        return ResponseEntity.success(interfaceConfigService.save(InterfaceConfig));
    }

    @RequestMapping("delete")
    public ResponseEntity delete(String id){return ResponseEntity.success(interfaceConfigService.delete(id));}

}

