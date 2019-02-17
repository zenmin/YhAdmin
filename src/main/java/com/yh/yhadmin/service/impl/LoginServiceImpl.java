package com.yh.yhadmin.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.LoginService;
import com.yh.yhadmin.util.MapConvertUtil;
import com.yh.yhadmin.util.StaticUtil;
import com.yh.yhadmin.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 18:12
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    UserInfoUtil userInfoUtil;

    /**
     * @param u
     * @param p
     * @return 登录
     */
    @Override
    public Object login(String u, String p) {
        String token = StaticUtil.getToken();
        Map<String,Object> map = new HashMap<>();
        map.put("avatar","/src/icons/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("introduction","超级管理员");
        map.put("name","Super Admin");
        map.put("roles",Lists.newArrayList("admin"));
        map.put("token",token);
        return userInfoUtil.addSession(token,map);
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
}
