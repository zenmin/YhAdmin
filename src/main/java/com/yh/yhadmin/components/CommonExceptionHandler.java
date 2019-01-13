package com.yh.yhadmin.components;

import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Describle This Class Is 全局异常处理器
 * @Author ZengMin
 * @Date 2019/1/3 19:43
 */
@RestControllerAdvice
public class CommonExceptionHandler extends RuntimeException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handler(RuntimeException e) {
        if (e instanceof CommonException) {
            return ResponseEntity.error(((CommonException) e).getCode(),e.getMessage());
        }
        e.printStackTrace();
        return ResponseEntity.error();
    }


}
