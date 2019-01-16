package com.yh.yhadmin.service.impl;
import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.GoodsRepository;
import com.yh.yhadmin.service.GoodsService;
import com.yh.yhadmin.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/16 20:14
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsRepository goodsRepository;


    @Override
    public Page<Goods> findAll(Pager pager) {
        Pageable page = new PageRequest(pager.getStart(), pager.getSize(),Sort.Direction.DESC,"createDate");
        return goodsRepository.findAll(page);
    }

    @HandlerMethod(optName = "商品管理",optDesc = "新增/更新商品")
    @Override
    @Transactional
    public Goods save(Goods goods) {
        Double price = goods.getPrice();
        if(Objects.isNull(price))
            price = 0d;
        else {
            price = Double.valueOf(StaticUtil.numberFormat.format(price).replace(",",""));
        }
        goods.setPrice(price);
        return goodsRepository.saveAndFlush(goods);
    }

    @HandlerMethod(optName = "商品管理",optDesc = "删除商品")
    @Override
    @Transactional
    public boolean delete(String id) {
        goodsRepository.deleteById(id);
        return true;
    }

    /**
     * @param goods
     * @param pager
     * @return 按条件查询
     */
    @Override
    public Page<Goods> findByCondition(Goods goods, Pager pager) {
        goods.setCreateDate(null);
        Pageable pageRequest = PageRequest.of(pager.getStart(), pager.getSize(),Sort.Direction.DESC,"createDate");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher.withIgnoreCase("id","createDate");
        if(Objects.nonNull(goods.getName()))
            exampleMatcher.withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Goods> goodsExample = Example.of(goods,exampleMatcher);
        Page<Goods> all = goodsRepository.findAll(goodsExample, pageRequest);
        return all;
    }
}
