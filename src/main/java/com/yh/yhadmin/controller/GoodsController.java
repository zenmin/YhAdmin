package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/16 20:13
 */
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;


    @RequestMapping("/getAll")
    public ResponseEntity findAll(Pager pager){
        return ResponseEntity.success(goodsService.findAll(pager));
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(Goods goods,Pager pager){
        return ResponseEntity.success(goodsService.findByCondition(goods,pager));
    }

    @RequestMapping("/save")
    public ResponseEntity saveOrUpdate(Goods goods){
        return ResponseEntity.success(goodsService.save(goods));
    }

    @RequestMapping("/delete")
    public ResponseEntity delete(String id){
        return ResponseEntity.success(goodsService.delete(id));
    }

    @RequestMapping("/updateImg")
    public ResponseEntity updateImg(String id,String imgs){
        return ResponseEntity.success(goodsService.updateImg(id,imgs));
    }


}
