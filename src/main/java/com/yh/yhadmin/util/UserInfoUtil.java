package com.yh.yhadmin.util;

import com.yh.yhadmin.domain.AdminUser;
import com.yh.yhadmin.domain.vo.AdminUserVo;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;
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
        Cache userinfoId = cacheManager.getCache(CommonConstant.CAHE_NAME_ID);
        userinfoId.put(userInfo.getId(),userInfo);
        return userInfo;
    }

    /**
     * @param token
     * @return 移除用户登录信息
     */
    @Async
    public void removeSession(String token){
        Cache userinfo = cacheManager.getCache(token);
        Cache userinfoId = cacheManager.getCache(CommonConstant.CAHE_NAME_ID);
        userinfo.put(token,null);
        userinfoId.put(userinfo.get("id").get().toString(),null);
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

    public Object getUserInfoByUserId(String id){
        Cache userinfo = cacheManager.getCache(CommonConstant.CAHE_NAME_ID);
        Cache.ValueWrapper valueWrapper = userinfo.get(id);
        if(valueWrapper == null){
            return null;
        }else{
            Object o = valueWrapper.get();
            AdminUserVo adminUser = (AdminUserVo) o;
            return adminUser;
        }
    }

    // 检测授权
    public boolean checkAuth(String code) {
        Cache auth = cacheManager.getCache(CommonConstant.AUTH_KEY_CACHE);
        Cache.ValueWrapper valueWrapper = auth.get(CommonConstant.AUTH_KEY_CACHE);
        if (StringUtils.isBlank(code)) {
            if (valueWrapper == null) {
                return false;
            } else {
                Object o = valueWrapper.get();
                Boolean b = (Boolean) o;
                return b;
            }
        } else {
            if (valueWrapper != null) {
                Object o = valueWrapper.get();
                Boolean b = (Boolean) o;
                if (b) {
                    return true;
                }
            }
            boolean s = StaticUtil.checkAuth(code);
            if (!s) {
                return false;
            } else {
                auth.put(CommonConstant.AUTH_KEY_CACHE, true);
                return true;
            }
        }
    }

}
