package com.yh.yhadmin.service.impl;
import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.domain.vo.GoodsVo;
import com.yh.yhadmin.domain.vo.GoodsVoHome;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.repository.GoodsRepository;
import com.yh.yhadmin.repository.impl.GoodsNativeRepository;
import com.yh.yhadmin.service.GoodsService;
import com.yh.yhadmin.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    @Autowired
    GoodsNativeRepository goodsNativeRepository;

    @Override
    public Page<GoodsVo> findAll(Pager pager) {
        List<GoodsVo> list = goodsNativeRepository.findAllAndCateGory(pager);
        long count = goodsRepository.count();
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),count);
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
    public Page<GoodsVo> findByCondition(Goods goods, Pager pager) {
        goods.setCreateDate(null);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher.withIgnoreCase("createDate");
        if(Objects.nonNull(goods.getName()))
            exampleMatcher.withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Goods> goodsExample = Example.of(goods,exampleMatcher);
        long count = goodsRepository.count(goodsExample);
        List<GoodsVo> list = goodsNativeRepository.findByCondition(goods,pager);
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),count);
    }

    @Override
    public boolean updateImg(String id, String imgs) {
        try {
            Goods one = goodsRepository.getOne(id);
            one.setImg(imgs);
            goodsRepository.saveAndFlush(one);
        }catch (Exception e){
            throw new CommonException(DefinedCode.NOTFOUND,"商品已经不存在，请刷新页面");
        }
        return true;
    }

    @Override
    public List<GoodsVoHome> findByConditionHome(Goods goods) {
        goods.setStatus(1);     // 上架
        List<GoodsVoHome> list = goodsNativeRepository.findByConditionHome(goods);
        return list;
    }
}
