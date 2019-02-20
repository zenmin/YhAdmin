package com.yh.yhadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/20 21:52
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * @param registry
     * 模板静态文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webtemps/**")
                .addResourceLocations("classpath:/templates/webtemps/");
    }


}
