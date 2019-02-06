package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.CommonLog;
import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.CommonLogService;
import com.yh.yhadmin.service.OperateLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle This Class Is 全局日志查询
 * @Author ZengMin
 * @Date 2019/2/3 15:56
 */
@RestController
@RequestMapping("/api/log")
public class LogsController {

    @Autowired
    OperateLogsService operateLogsService;

    @Autowired
    CommonLogService commonLogService;

    @RequestMapping("/get_del_cardpwd")
    public ResponseEntity getDeleteCard(Pager pager){
        return ResponseEntity.success(operateLogsService.findByOptDesc(CommonConstant.DELTET_BATCH_CARDPWD,pager));
    }

    @RequestMapping("/getOptAll")
    public ResponseEntity getOptAll(Pager pager){
        return ResponseEntity.success(operateLogsService.findAll(pager));
    }

    @RequestMapping("/getOptCondition")
    public ResponseEntity getOptAll(Pager pager, OperateLogs operateLogs){
        return ResponseEntity.success(operateLogsService.getByCondition(operateLogs,pager));
    }

    @RequestMapping("/getAccessLog")
    public ResponseEntity getAccessLog(Pager pager){
        return ResponseEntity.success(commonLogService.findAll(pager));
    }

    @RequestMapping("/getAccessLogCondition")
    public ResponseEntity getAccessLogCondition(Pager pager, CommonLog commonLog){
        return ResponseEntity.success(commonLogService.getByCondition(commonLog,pager));
    }

}
