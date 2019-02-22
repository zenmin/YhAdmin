package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.Coupon;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.domain.vo.AdminUserVo;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.CouponService;
import com.yh.yhadmin.util.DateUtil;
import com.yh.yhadmin.util.UserInfoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/19 18:00
 */
@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    UserInfoUtil userInfoUtil;

    @RequestMapping("/getAll")
    public ResponseEntity findAll(Pager pager){
        return ResponseEntity.success(couponService.findAll(pager));
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(Coupon coupon, Pager pager){
        return ResponseEntity.success(couponService.findByCondition(coupon,pager));
    }

    @RequestMapping("/save")
    public ResponseEntity saveOrUpdate(Coupon coupon, @RequestHeader String token){
        String disDateField = coupon.getDisDateField();
        if(StringUtils.isNotBlank(disDateField))
            coupon.setDisDate(DateUtil.parseToDateMilis(disDateField));

        //如果是创建  设置创建人
        if(StringUtils.isBlank(coupon.getId())){
            AdminUserVo userInfo = userInfoUtil.getUserInfo(token);
            coupon.setCreateUser(userInfo.getName());
        }

        return ResponseEntity.success(couponService.save(coupon));
    }

    @RequestMapping("/delete")
    public ResponseEntity delete(String id){
        return ResponseEntity.success(couponService.delete(id));
    }

}
