package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import org.springframework.data.domain.Page;

/**
 * @Describle This Class Is 商品管理
 * @Author ZengMin
 * @Date 2019/1/16 20:14
 */
public interface GoodsService {

    Page<Goods> findAll(Pager pager);

    Goods save(Goods goods);

    boolean delete(String id);

    Page<Goods> findByCondition(Goods goods,Pager pager);

}
