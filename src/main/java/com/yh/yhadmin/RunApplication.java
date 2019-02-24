package com.yh.yhadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
@PropertySource(value = {"classpath:config.properties", "file:config.properties"}, ignoreResourceNotFound = true)
public class RunApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RunApplication.class, args);
        WebServer webServer = ((AnnotationConfigServletWebServerApplicationContext) run).getWebServer();
        System.out.println("\n \n # # # # # # # # # # # # # # # # # # # #  √ 系统启动完成 请保持程序后台 运行端口："+webServer.getPort()+" √  # # # # # # # # # # # # # # # # # # # # # # # # # \n \n");
    }

}

