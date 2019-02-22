package com.yh.yhadmin.util;

import com.yh.yhadmin.domain.vo.AdminUserVo;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 18:30
 */
@Component
public class UserInfoUtil {

    @Autowired
    ConcurrentMapCacheManager cacheManager;

    /**
     * @param token
     * @param userInfo
     * @return 添加用户登录信息
     */
    public Object addSession(String token,AdminUserVo userInfo){
        Cache userinfo = cacheManager.getCache(CommonConstant.CAHE_NAME);
        userinfo.put(token,userInfo);
        return userInfo;
    }

    /**
     * @param token
     * @return 移除用户登录信息
     */
    public void removeSession(String token){
        Cache userinfo = cacheManager.getCache(token);
        userinfo.put(token,null);
    }

    /**
     * @param token
     * @return 从缓存中获取用户信息
     */
    public AdminUserVo getUserInfo(String token){
        Cache userinfo = cacheManager.getCache(CommonConstant.CAHE_NAME);
        Cache.ValueWrapper valueWrapper = userinfo.get(token);
        if(valueWrapper == null)
            throw new CommonException(DefinedCode.NOTAUTH,"登录超时，请重新登录！");
        Object o = valueWrapper.get();
        AdminUserVo adminUserVo = (AdminUserVo) o;
        return adminUserVo;
    }


}
