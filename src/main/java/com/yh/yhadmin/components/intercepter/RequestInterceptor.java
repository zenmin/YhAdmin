package com.yh.yhadmin.components.intercepter;

import com.yh.yhadmin.domain.CommonLog;
import com.yh.yhadmin.service.CommonLogService;
import com.yh.yhadmin.util.IpHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    CommonLogService commonLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        String token = request.getParameter("token");
        Map<String, String[]> parameterMap = request.getParameterMap();
        String params = objectMapper.writeValueAsString(parameterMap);
        //此处连接redis执行鉴权 或者Session
        log.info("客户端ip:[{}]请求URL:[{}] ,请求params:[{}]",IpHelper.getRequestIpAddr(request), request.getRequestURL(), params);
        commonLogService.saveLog(new CommonLog(IpHelper.getRequestIpAddr(request), request.getRequestURL().toString(),params));
        return true;
    }

}
