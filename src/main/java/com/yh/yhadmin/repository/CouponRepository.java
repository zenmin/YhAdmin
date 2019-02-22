package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/19 17:50
 */
public interface CouponRepository extends JpaRepository<Coupon,String> {

    // 查询有效优惠券
    Coupon findByCouponNo(String coupon);

    @Modifying
    @Transactional
    @Query("update Coupon c set c.status = 0 where c.couponNo = ?1")
    int updateStatusByCouponNo(String conponNo);
}
