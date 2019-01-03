package com.cms.yhadmin.foundation;

import lombok.Data;

/**
 * @Describle This Class Is 全局返回类
 * @Author ZengMin
 * @Date 2019/1/3 19:45
 * @Company Matt
 */
@Data
public class ResponseEntity {

    private int code;

    private String msg;

    private Object data;

    public ResponseEntity(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseEntity() {
    }

    public static ResponseEntity success(Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(DefinedCode.SUCCESS);
        responseEntity.setData(data);
        responseEntity.setMsg("success");
        return responseEntity;
    }

    public static ResponseEntity error(int code, String msg) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(code);
        responseEntity.setMsg(msg);
        return responseEntity;
    }

    public static ResponseEntity error() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(DefinedCode.ERROR);
        responseEntity.setMsg("失败");
        return responseEntity;
    }


}
