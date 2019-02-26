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

    @Bean
    AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor())
                .addPathPatterns("/api/**").excludePathPatterns("/api/index/login","/api/index/logOut");

        registry.addInterceptor(sqlInterceptor()).addPathPatterns("/order/**");

        registry.addInterceptor(authInterceptor()).addPathPatterns("/api/webConfig/save","/api/admin/save");
    }

}