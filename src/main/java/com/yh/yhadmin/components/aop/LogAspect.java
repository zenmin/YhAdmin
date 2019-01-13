package com.yh.yhadmin.components.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.foundation.constant.RequestConstant;
import com.yh.yhadmin.service.OperateLogsService;
import com.yh.yhadmin.util.IpHelper;
import com.yh.yhadmin.util.MapConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Describle This Class Is 全局操作切面类
 * @Author ZengMin
 * @Date 2019/1/13 16:40
 * @Company Matt
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    OperateLogsService operateLogsService;

    @Pointcut("execution(* com.yh.yhadmin.service.*Service.*(..))")
    private void pointCut() {
    }

    @Around("@annotation(handlerMethod) && pointCut()")
    public Object execAspect(ProceedingJoinPoint joinPoint, HandlerMethod handlerMethod) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        this.saveLogs(joinPoint, request, servletRequestAttributes);
        return joinPoint.proceed();
    }

    private void saveLogs(ProceedingJoinPoint joinPoint, HttpServletRequest request, ServletRequestAttributes servletRequestAttributes) {
        log.debug("监听到全局操作 正在写入日志...");
        OperateLogs operateLogs = new OperateLogs();
        //取操作名称和描述
        Object[] args = joinPoint.getArgs();
        //取方法入参
        String params = "";
        try {
            params = MapConvertUtil.objectMapper.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        operateLogs.setOptParams(params);
        //取请求ip
        String ip = IpHelper.getRequestIpAddr(request);
        operateLogs.setOptIp(ip);
        //取操作人
        String optUserName = "";
        Object optUserNameTemp = null;
        optUserNameTemp = request.getSession().getAttribute(RequestConstant.TOKEN);
        if (Objects.isNull(optUserNameTemp)) {
            //session里面取不到token  说明是普通用户  从请求参数里面取联系方式
            optUserNameTemp = servletRequestAttributes.getAttribute("userContact", RequestAttributes.SCOPE_REQUEST);
            if (Objects.isNull(optUserNameTemp)) {
                //如果还是为空  说明可能是非管理员和用户下单操作
                log.debug("没有取到当前用户信息！");
                optUserName = "NoUser";
            } else {
                optUserName = optUserNameTemp.toString();
            }
        } else {
            //取到了的话  转为用户信息实体
            optUserName = optUserNameTemp.toString();
            operateLogs.setOptUserId("");
        }
        operateLogs.setOptUser(optUserName);
        Object aThis = joinPoint.getSignature();
        if (aThis instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) aThis;
            Method method = methodSignature.getMethod();
            HandlerMethod annotation = method.getAnnotation(HandlerMethod.class);
            String optDesc = annotation.optDesc();
            String optName = annotation.optName();
            operateLogs.setOptDesc(optDesc);
            operateLogs.setOptName(optName);
        } else {
            throw new IllegalArgumentException("该注解仅用于Service实现方法上，请勿乱用！");
        }
        //异步保存
        operateLogsService.saveLogs(operateLogs);
    }


}
