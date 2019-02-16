package com.yh.yhadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RunApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RunApplication.class, args);
        WebServer webServer = ((AnnotationConfigServletWebServerApplicationContext) run).getWebServer();
        System.out.println("\n \n # # # # # # # # # # # # # # # # # # # # # # # # √ 系统启动完成 运行端口："+webServer.getPort()+" √ # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # \n \n");
    }

}

