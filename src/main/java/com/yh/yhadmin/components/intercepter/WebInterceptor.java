package com.yh.yhadmin.components.intercepter;

import com.yh.yhadmin.components.filter.JsonPFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {

    @Bean
    RequestInterceptor requestInterceptor(){
        return new RequestInterceptor();
    }

    @Bean
    SqlInterceptor sqlInterceptor(){
        return new SqlInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor())
                .addPathPatterns("/api/**").excludePathPatterns("/api/index/login","/api/index/logOut");

        registry.addInterceptor(sqlInterceptor()).addPathPatterns("/order/**");
    }

    //注册JsonpFilter
    // @Bean  前后端未分离情况下关闭
    public FilterRegistrationBean jsonpFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JsonPFilter());   //设置拦截器
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*")); //拦截路径
        return filterRegistrationBean;
    }

}