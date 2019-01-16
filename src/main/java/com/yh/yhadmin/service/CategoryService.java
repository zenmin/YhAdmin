package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.Category;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/15 20:00
 */
public interface CategoryService {

    List<Category> findAll();

    Category updateCategory(Category category);

    boolean delCategory(String id);

    List<Category> getByCondition(String name, Integer status);
}
