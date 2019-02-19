package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/19 18:49
 */
public interface AdminUserRepository extends JpaRepository<AdminUser,String> {

    AdminUser findByUserNameAndPassWord(String userName, String password);

    AdminUser findByUserName(String userName);
}
