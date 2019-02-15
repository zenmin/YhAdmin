package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Coupon;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.repository.CouponRepository;
import com.yh.yhadmin.service.CouponService;
import com.yh.yhadmin.util.StaticUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @Describle This Class Is 优惠券管理
 * @Author ZengMin
 * @Date 2019/1/19 17:49
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepository;


    @Override
    public Page<Coupon> findAll(Pager pager) {
        return couponRepository.findAll(new PageRequest(pager.getStart(),pager.getSize(),Sort.Direction.DESC,"createDate"));
    }

    @HandlerMethod(optName = "优惠券管理",optDesc = "增加/修改优惠券")
    @Override
    public Coupon save(Coupon coupon) {
        String id = coupon.getId();
        if (StringUtils.isBlank(id)) {
            //新增  生成key
            coupon.setCouponNo(StaticUtil.uniqueKey());
            try {
                coupon = couponRepository.save(coupon);
            }catch (Exception e){
                // 万一优惠券编码重复 在执行一次
                coupon.setCouponNo(StaticUtil.uniqueKey());
                coupon = couponRepository.save(coupon);
                return coupon;
            }
        } else {
            Coupon one = couponRepository.getOne(id);
            if (null == one)
                throw new CommonException(DefinedCode.NOTFOUND, "优惠券未找到");
            coupon.setCouponNo(one.getCouponNo());
            coupon.setCreateDate(one.getCreateDate());
            coupon.setCreateUser(one.getCreateUser());
            coupon.setUseDate(one.getUseDate());
            coupon.setId(id);
            coupon = couponRepository.saveAndFlush(coupon);
        }
        return coupon;
    }

    @HandlerMethod(optName = "优惠券管理",optDesc = "删除优惠券")
    @Override
    public boolean delete(String id) {
        couponRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<Coupon> findByCondition(Coupon coupon, Pager pager) {
        coupon.setCreateDate(null);
        Example<Coupon> of = Example.of(coupon);
        Page<Coupon> all = couponRepository.findAll(of, new PageRequest(pager.getStart(), pager.getSize()));
        return all;
    }
}
