package com.yh.yhadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableAsync
@PropertySource(value = {"classpath:config.properties", "file:config.properties"}, ignoreResourceNotFound = true)
@EnableScheduling
public class RunApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext run = SpringApplication.run(RunApplication.class, args);
            WebServer webServer = ((AnnotationConfigServletWebServerApplicationContext) run).getWebServer();
            System.out.println("\n \n # # # # # # # # # # # # # # # # # # # #  √ 系统启动完成 请保持程序后台 运行端口："+webServer.getPort()+" √  # # # # # # # # # # # # # # # # # # # # # # # # # \n \n");
        }catch (Exception e){
            System.out.println("\n \n 系统启动失败！请检查数据库连接配置！\n \n ");
        }
    }

}

