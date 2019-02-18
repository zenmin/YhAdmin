package com.yh.yhadmin.controller;

import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.DevConstant;
import com.yh.yhadmin.service.IndexService;
import com.yh.yhadmin.service.LoginService;
import com.yh.yhadmin.util.DateUtil;
import com.yh.yhadmin.util.IpHelper;
import com.yh.yhadmin.util.StaticUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Describle This Class Is 后台首页
 * @Author ZengMin
 * @Date 2019/1/15 19:53
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Value("${application.version}")
    private String version;

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    IndexService indexService;

    @Autowired
    LoginService loginService;

    /**
     * @param u
     * @param p
     * @return
     */
    @RequestMapping("/login")
    public Object login(String u,String p,String token){
        if(StringUtils.isNotBlank(token)){
            return loginService.info(token);
        }else{
            return loginService.login(u,p);
        }
    }

    @RequestMapping("/info")
    public Object getUserInfo(String token){
        return loginService.info(token);
    }

    @RequestMapping("/loginOut")
    public ResponseEntity loginOut(String token) {
        return ResponseEntity.success(loginService.loginOut(token));
    }

    /**
     * @return
     * 1、下单用户数
     * 2、库存卡密
     * 3、今日入账金额
     * 4、今日订单数量
     * 5、七日成功订单和总价
     */
    @RequestMapping("/getDashboard")
    public ResponseEntity getNow(){
        return ResponseEntity.success(indexService.getNow());
    }

    /**
     * @return
     * 6、支付方式分析
     * 7、商品卖出订单分析
     */
    @RequestMapping("/getPayway")
    public ResponseEntity getPayway(){
        return ResponseEntity.success(indexService.getPayway());
    }

    /**
     * @return
     * 最新八条订单
     */
    @RequestMapping("/getNowOrder")
    public ResponseEntity getNowOrder(){
        return ResponseEntity.success(indexService.getNowOrder());
    }

    @RequestMapping("/version")
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
