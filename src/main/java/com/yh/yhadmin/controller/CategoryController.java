package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.Category;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle This Class Is 商品分类管理
 * @Author ZengMin
 * @Date 2019/1/15 19:55
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @RequestMapping("/getAll")
    public ResponseEntity findAll(){
        return ResponseEntity.success(categoryService.findAll());
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(String name,Integer status){
        return ResponseEntity.success(categoryService.getByCondition(name,status));
    }

    @RequestMapping("/save")
    public ResponseEntity saveOrUpdate(Category category){
        return ResponseEntity.success(categoryService.updateCategory(category));
    }

    @RequestMapping("/delete")
    public ResponseEntity delete(String id){
        return ResponseEntity.success(categoryService.delCategory(id));
    }

}
