package com.yh.yhadmin.components.intercepter;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Describle This Class Is 防止sql注入拦截器
 * @Author ZengMin
 * @Date 2019/2/23 16:27
 */
public class SqlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
        String requestURI = request.getRequestURI();
        String params = requestURI.substring(requestURI.lastIndexOf("/")+1);
        if(inj_str.indexOf(params) != -1){
            throw new CommonException(DefinedCode.ERROR,"检测到SQL注入攻击，已记录IP，再次攻击将封禁IP，永久不得访问此站！");
        }
        return true;
    }

}
