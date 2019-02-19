package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.AdminUser;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.AdminUserService;
import com.yh.yhadmin.util.StaticUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/19 18:45
 */
@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    @RequestMapping("/getAll")
    public ResponseEntity findAll(Pager pager){
        return ResponseEntity.success(adminUserService.findAll(pager));
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(AdminUser adminUser, Pager pager){
        return ResponseEntity.success(adminUserService.findByCondition(adminUser,pager));
    }

    @RequestMapping("/save")
    public ResponseEntity saveOrUpdate(AdminUser adminUser,Integer isResetPwd){
        // 更新
        if(StringUtils.isNotBlank(adminUser.getId())){
            if(isResetPwd == 1){
                //重置密码
                adminUser.setPassWord(StaticUtil.md5Hex(CommonConstant.INIT_PASSWORD));
            }
        }
        return ResponseEntity.success(adminUserService.save(adminUser));
    }

    @RequestMapping("/delete")
    public ResponseEntity delete(String id){
        return ResponseEntity.success(adminUserService.delete(id));
    }


}
