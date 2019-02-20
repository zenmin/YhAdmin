package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.domain.vo.GoodsVo;
import com.yh.yhadmin.domain.vo.GoodsVoHome;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Describle This Class Is 商品管理
 * @Author ZengMin
 * @Date 2019/1/16 20:14
 */
public interface GoodsService {

    Page<GoodsVo> findAll(Pager pager);

    Goods save(Goods goods);

    boolean delete(String id);

    Page<GoodsVo> findByCondition(Goods goods,Pager pager);

    boolean updateImg(String id, String imgs);

    List<GoodsVoHome> findByConditionHome(Goods goods);

}
