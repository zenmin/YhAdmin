package com.yh.yhadmin.components.intercepter;

import com.yh.yhadmin.domain.CommonLog;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.service.CommonLogService;
import com.yh.yhadmin.util.IpHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yh.yhadmin.util.MapConvertUtil;
import com.yh.yhadmin.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Describle This Class Is 验证所有请求权限
 * @Author ZengMin
 * @Date 2019/1/3 19:18
 */
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    CommonLogService commonLogService;

    @Autowired
    UserInfoUtil userInfoUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            throw new CommonException(DefinedCode.NOTAUTH,"登陆超时，请重新登录！");
        }
        // 程序验证
        boolean b = userInfoUtil.checkAuth(null);
        if(!b){
            return false;
        }
        // cacheManager验证用户登录与否
        userInfoUtil.getUserInfo(token);
        Map<String, String[]> parameterMap = request.getParameterMap();
        String params = MapConvertUtil.objectMapper.writeValueAsString(parameterMap);
        log.info("客户端ip:[{}]请求URL:[{}] ,请求params:[{}]",IpHelper.getRequestIpAddr(request), request.getRequestURL(), params);
        commonLogService.saveLog(new CommonLog(IpHelper.getRequestIpAddr(request), request.getRequestURL().toString(),params));
        return true;
    }

}
