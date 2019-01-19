package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.Coupon;
import com.yh.yhadmin.domain.query.Pager;
import org.springframework.data.domain.Page;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/19 17:49
 */
public interface CouponService {

    Page<Coupon> findAll(Pager pager);

    Coupon save(Coupon coupon);

    boolean delete(String id);

    Page<Coupon> findByCondition(Coupon goods,Pager pager);
}
