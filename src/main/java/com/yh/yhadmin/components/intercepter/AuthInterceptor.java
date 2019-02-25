package com.yh.yhadmin.components.intercepter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yh.yhadmin.domain.vo.AdminUserVo;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/25 21:47
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    UserInfoUtil userInfoUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        String token = request.getHeader("token");
        // cacheManager验证用户登录与否
        AdminUserVo userInfo = userInfoUtil.getUserInfo(token);
        // 验证是否超级管理员
        if(!userInfo.getIsAdministrator()){
            throw new CommonException(DefinedCode.NOADMIN,"你不是超级管理员，没有权限编辑！");
        }
        return true;
    }
}
