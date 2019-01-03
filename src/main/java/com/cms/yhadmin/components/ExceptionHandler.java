package com.cms.yhadmin.components;

import com.cms.yhadmin.foundation.CommonException;
import com.cms.yhadmin.foundation.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Describle This Class Is 全局异常处理器
 * @Author ZengMin
 * @Date 2019/1/3 19:43
 * @Company Matt
 */
@RestControllerAdvice
public class ExceptionHandler extends RuntimeException {

    public ResponseEntity handler(RuntimeException e) {
        if (e instanceof CommonException) {
            return ResponseEntity.error(((CommonException) e).getCode(),e.getMessage());
        }
        e.printStackTrace();
        return ResponseEntity.error();
    }


}
