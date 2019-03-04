package com.yh.yhadmin.controller;

import com.google.common.collect.Lists;
import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.CardPasswordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 20:21
 */
@RestController
@RequestMapping("/api/card")
public class CardPasswordController {

    @Autowired
    CardPasswordService cardPasswordService;

    @RequestMapping("/getAll")
    public ResponseEntity findAll(Pager pager){
        return ResponseEntity.success(cardPasswordService.findAll(pager));
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(CardPassword cardPassword, Pager pager){
        return ResponseEntity.success(cardPasswordService.findByCondition(cardPassword,pager));
    }

    @RequestMapping("/save")
    public ResponseEntity saveOrUpdate(CardPassword cardPassword){
        if(StringUtils.isBlank(cardPassword.getCardNo().trim()) || StringUtils.isBlank(cardPassword.getGoodsId()))
            throw new CommonException(DefinedCode.PARAMSERROR,"卡密或商品不能为空！");
        return ResponseEntity.success(cardPasswordService.save(cardPassword));
    }

    @RequestMapping("/delete")
    public ResponseEntity delete(String id){
        return ResponseEntity.success(cardPasswordService.delete(id));
    }

    @RequestMapping("/deleteBatch")
    public ResponseEntity delete(Integer type,String ids,String cid ,String goodsId){
        if(type != null){
            return ResponseEntity.success(cardPasswordService.deleteBatch(type,cid,goodsId));
        }else {
            return ResponseEntity.success(cardPasswordService.deleteBatchTwo(ids));
        }
    }


}
