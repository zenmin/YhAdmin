package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.AdminUser;
import com.yh.yhadmin.domain.query.Pager;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/19 18:46
 */
public interface AdminUserService {

    Object findAll(Pager pager);

    Object findByCondition(AdminUser coupon, Pager pager);

    Object save(AdminUser coupon);

    boolean delete(String id);

    AdminUser findByUP(String u , String p);

}
