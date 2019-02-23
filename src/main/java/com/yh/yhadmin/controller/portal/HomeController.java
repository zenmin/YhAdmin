package com.yh.yhadmin.controller.portal;

import com.yh.yhadmin.domain.Category;
import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.domain.vo.GoodsVoHome;
import com.yh.yhadmin.domain.vo.OrderVo;
import com.yh.yhadmin.domain.vo.PayConfigVo;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.*;
import com.yh.yhadmin.util.HttpClientUtil;
import com.yh.yhadmin.util.IpHelper;
import com.yh.yhadmin.util.StaticUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Describle This Class Is 全局Controller 发卡首页
 * @Author ZengMin
 * @Date 2019/1/3 20:26
 */
@Controller
public class HomeController {

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    WebConfigService webConfigService;

    @Autowired
    InterfaceConfigService interfaceConfigService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/zadmin")
    public String toAdmin(){
        return "admin";
    }

    @RequestMapping({"/", "index", "index.html", "index.php", "index.jsp"})
    public String toHome(Model model){
        WebConfig webConfig = webConfigService.findAll();
        // 全局配置
        model.addAttribute("config",webConfig);
        // 支付接口开关
        Object pay = interfaceConfigService.findByType(CommonConstant.ALL_INTERFACE_CONFIG.get(3));
        model.addAttribute("ps",pay);
        // 首页风格
        String style = interfaceConfigService.findByType(CommonConstant.ALL_INTERFACE_CONFIG.get(4)).toString();
        model.addAttribute("tempPath",style.substring(0,style.lastIndexOf("/")));
        model.addAttribute("tempDefaultPath",CommonConstant.DEFAULT_TEMP_STATIC_PATH);
        //商品分类
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return style;
    }

    @RequestMapping("/api")
    public String toApi(){
        if(env.equals("dev"))
            return "api/web_api";
        else
            return "redirect:index";
    }

    /**
     * @param temp
     * @param page
     * @return 到模板下的某个视图
     */
    @RequestMapping("/webtemps/{temp}/{page}")
    public String toPage(@PathVariable String temp, @PathVariable String page,Model model) {
        WebConfig all = webConfigService.findAll();
        model.addAttribute("config",all);
        String style = interfaceConfigService.findByType(CommonConstant.ALL_INTERFACE_CONFIG.get(4)).toString();
        model.addAttribute("tempPath",style.substring(0,style.lastIndexOf("/")));
        model.addAttribute("tempDefaultPath",CommonConstant.DEFAULT_TEMP_STATIC_PATH);
        return "/webtemps/" + temp + "/" + page;
    }

    /**
     * @param goods
     * @return 按分类id查询商品
     */
    @ResponseBody
    @RequestMapping("/goods/getByCondition")
    public ResponseEntity getByConditionGoods(Goods goods){
        List<GoodsVoHome> byCondition = goodsService.findByConditionHome(goods);
        return ResponseEntity.success(byCondition);
    }

}
