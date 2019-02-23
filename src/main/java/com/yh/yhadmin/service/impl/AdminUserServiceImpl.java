package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.AdminUser;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.AdminUserRepository;
import com.yh.yhadmin.repository.WebConfigRepository;
import com.yh.yhadmin.service.AdminUserService;
import com.yh.yhadmin.service.WebConfigService;
import com.yh.yhadmin.util.StaticUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/19 18:46
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Autowired
    WebConfigService webConfigService;

    @Override
    public Object findAll(Pager pager) {
        Page<AdminUser> created = adminUserRepository.findAll(new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.ASC, "createDate"));
        created.getContent().stream().forEach(o->o.setPassWord(null));
        return created;
    }

    @Override
    public Object findByCondition(AdminUser coupon, Pager pager) {
        coupon.setCreateDate(null);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("realName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<AdminUser> of = Example.of(coupon, exampleMatcher);
        PageRequest pageRequest = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.ASC, "createDate");
        Page<AdminUser> all = adminUserRepository.findAll(of, pageRequest);
        all.getContent().stream().forEach(o->o.setPassWord(null));
        return all;
    }

    @Override
    public Object save(AdminUser adminUser) {
        //检查用户名是否存在
        AdminUser byUserName = adminUserRepository.findByUserName(adminUser.getUserName());
        //新增
        if(StringUtils.isBlank(adminUser.getId())){
            if(byUserName != null){
                throw new CommonException(DefinedCode.USER_EXISTS,"用户名已经存在！");
            }
            adminUser.setIsAdministrator(0);
            adminUser.setPassWord(StaticUtil.md5Hex(adminUser.getPassWord()));
        }else{
            //更新
            if(byUserName.getIsAdministrator() == 1 && adminUser.getStatus() == 0){
                throw new CommonException(DefinedCode.AUTHERROR,"禁止将超级管理员设置为禁用！");
            }
            adminUser.setUserName(byUserName.getUserName());
            adminUser.setCreateDate(byUserName.getCreateDate());
            adminUser.setIsAdministrator(byUserName.getIsAdministrator());
            adminUser.setLastloginIP(byUserName.getLastloginIP());
            adminUser.setLastloginTime(byUserName.getLastloginTime());
            if(StringUtils.isBlank(adminUser.getPassWord())){
                adminUser.setPassWord(byUserName.getPassWord());
            }
        }
        AdminUser saved = adminUserRepository.saveAndFlush(adminUser);
        if(saved.getIsAdministrator() == CommonConstant.STATUS_OK){
            String adminEmail = saved.getAdminEmail();
            webConfigService.updateAdminEmail(adminEmail);
        }
        adminUser.setPassWord(null);
        return saved;
    }

    @Override
    public boolean delete(String id) {
        AdminUser one = adminUserRepository.getOne(id);
        Integer isAdministrator = one.getIsAdministrator();
        if(isAdministrator == 1){
            throw new CommonException(DefinedCode.AUTHERROR,"禁止删除超级管理员！");
        }
        adminUserRepository.deleteById(id);
        return true;
    }

    @Override
    public AdminUser findByUP(String u, String p) {
        return adminUserRepository.findByUserNameAndPassWord(u,StaticUtil.md5Hex(p));
    }

    @Override
    @Async
    public void updateLoginTime(AdminUser adminUser) {
        adminUserRepository.saveAndFlush(adminUser);
    }
}
