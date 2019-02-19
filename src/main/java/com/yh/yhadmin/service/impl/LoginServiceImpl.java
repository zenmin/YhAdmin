package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.AdminUser;
import com.yh.yhadmin.domain.vo.AdminUserVo;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.AdminUserService;
import com.yh.yhadmin.service.LoginService;
import com.yh.yhadmin.util.StaticUtil;
import com.yh.yhadmin.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 18:12
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    UserInfoUtil userInfoUtil;

    @Autowired
    AdminUserService adminUserService;

    /**
     * @param u
     * @param p
     * @return 登录
     */
    @Override
    public Object login(String u, String p) {
        // 验证用户名密码
        AdminUser adminUser = adminUserService.findByUP(u, p);
        if(adminUser == null)
            throw new CommonException(DefinedCode.AUTHERROR,"用户名或密码错误！");
        Integer status = adminUser.getStatus();
        if(status == CommonConstant.STATUS_ERROR)
            throw new CommonException(DefinedCode.AUTHERROR_DISABLED,"用户已被禁用！");
        String token = StaticUtil.getToken();
        AdminUserVo adminUserVo = new AdminUserVo(adminUser.getRealName(), token, adminUser.getPhone(), adminUser.getQq(), adminUser.getStatus());
        return userInfoUtil.addSession(token,adminUserVo);
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Object info(String token) {
        Object userInfo = userInfoUtil.getUserInfo(token);
        return userInfo;
    }

    @Override
    public boolean loginOut(String token) {
        userInfoUtil.removeSession(token);
        return true;
    }
}
