package com.yh.yhadmin.controller;

import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.DevConstant;
import com.yh.yhadmin.util.DateUtil;
import com.yh.yhadmin.util.IpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Describle This Class Is 全局Controller
 * @Author ZengMin
 * @Date 2019/1/3 20:26
 */
@Controller
public class HomeController {

    @Value("${yhadmin.version}")
    private String version;

    @Value("${yhadmin.env}")
    private String env;

    @RequestMapping("/")
    public String toHome(){
        if(env.equals("dev"))
            return "api/web_api";
        else
            return "qd";
    }

    @RequestMapping("/api")
    public String toApi(){
        return "api/web_api";
    }

    @RequestMapping("/qd")
    public String testException(){
        return "qd";
    }



    @RequestMapping("/api/home/version")
    @ResponseBody
    public ResponseEntity getVersion(HttpServletRequest request){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("version",version);
        map.put("ip",IpHelper.getRequestIpAddr(request));
        map.put("time",DateUtil.getNowTime());
        map.put("host",request.getHeader("host"));
        map.put("port",request.getServerPort());
        map.put("serverinfo",request.getServletContext().getServerInfo());
        map.put("user-agent",request.getHeader("user-agent"));
        map.put("maxinactiveinterval",request.getSession().getMaxInactiveInterval()/60 + "s");
        Properties properties = System.getProperties();
        map.put("javaversion",properties.getProperty("java.version"));
        map.put("jvmversion",properties.getProperty("java.vm.version"));
        map.put("usercountry",properties.getProperty("user.country"));
        map.put("pid",properties.getProperty("PID"));
        map.put("userdir",properties.getProperty("user.dir"));
        map.put("osname",properties.getProperty("os.name"));
        map.put("javaiotmpdir",properties.getProperty("java.io.tmpdir"));
        map.put("javaclassversion",properties.getProperty("java.class.version"));
        map.put("usertimezone",properties.getProperty("user.timezone"));
        map.put("fileencoding",properties.getProperty("file.encoding"));
        map.put("javahome",properties.getProperty("java.home"));
        map.put("suncpuisalist",properties.getProperty("sun.cpu.isalist"));
        map.put("updateurl", DevConstant.UPDATEURL);
        return ResponseEntity.success(map);
    }
}
