package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Category;
import com.yh.yhadmin.repository.CategoryRepository;
import com.yh.yhadmin.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Describle This Class Is 商品分类
 * @Author ZengMin
 * @Date 2019/1/15 20:00
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAllOrderBySortAsc();
    }

    @Override
    public List<Category> getByCondition(String name,Integer status) {
        Category category = new Category();
        //置空默认值  不然会带条件
        category.setCreateDate(null);
        category.setSort(null);

        if(StringUtils.isNotBlank(name))
            category.setName(name);
        if(status != null && status != 0)
            category.setStatus(status);
        //不传ExampleMatcher 默认=
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains()); //包含
        Example example = Example.of(category,exampleMatcher);
        List<Category> all = categoryRepository.findAll(example);
        return all;
    }

    @HandlerMethod(optName = "商品分类管理",optDesc = "新增/更新分类")
    @Override
    @Transactional
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @HandlerMethod(optName = "商品分类管理",optDesc = "删除分类")
    @Override
    @Transactional
    public boolean delCategory(String id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
