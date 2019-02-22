package com.yh.yhadmin.controller;

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
        model.addAttribute("pay",pay);
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

    /**
     * @return 按分类id查询商品
     */
    @ResponseBody
    @PostMapping("/order/createOrder")
    public ResponseEntity createOrder(OrderVo orderVo, HttpServletRequest request){
        String ipAddr = IpHelper.getRequestIpAddr(request);
        orderVo.setIp(ipAddr);
        OrderVo orderVo1 = ordersService.createOrder(orderVo);
        return ResponseEntity.success(orderVo1);
    }

    /**
     * @return 到支付页面
     */
    @GetMapping("/order/pay/{orderNo}")
    public String createOrder(@PathVariable String orderNo, RedirectAttributes model, HttpServletResponse response) throws IOException {
        Orders orders = ordersService.findByOrderNo(orderNo);
        if(orders == null){
            model.addAttribute("error","订单有误！");
            return "index";
        }
        if(orders.getStatus() == CommonConstant.STATUS_OK){
            model.addAttribute("error","订单已完成！");
            return "index";
        }
        if(orders.getPayStatus() == 2){
            model.addAttribute("error","支付超时，请重新下单！");
            return "index";
        }

        /**
         * 接收参数 创建订单
         */
        Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(CommonConstant.InterfaceConfig.PAY_TYPE.getCode()));
        PayConfigVo payConfig = (PayConfigVo) byType;
        WebConfig webConfig = webConfigService.findAll();


        String codepay_id = payConfig.getApp_id() ;//记得更改 http://codepay.fateqq.com 后台可获得
        String token = payConfig.getApp_key(); //记得更改 http://codepay.fateqq.com 后台可设置

        String price= orders.getAllPrice().toString(); //表单提交的价格
        String type = CommonConstant.PayWay.getCode(orders.getPayWay()).toString(); //支付类型  1：支付宝 2：QQ钱包 3：微信
        String pay_id = orders.getOrderNo(); //支付人的唯一标识

        String notify_url= "http://"+webConfig.getWebUrl()+"/order/callback" ;//通知地址

        String return_url="http://"+webConfig.getWebUrl()+"/order/query/"+orders.getOrderNo();//支付后同步跳转地址

        if(StringUtils.isBlank(price)){
            price="1";
        }
        //参数有中文则需要URL编码
        //码支付接口
        String payInterface = "http://api2.fateqq.com:52888/creat_order";
        Map<String,Object> map = new HashMap<>();
        map.put("id",codepay_id);
        map.put("pay_id",pay_id);
        map.put("price",price);
        map.put("type",type);
        map.put("token",token);
        map.put("notify_url",notify_url);
        map.put("return_url",return_url);
//        String url = "id=" + codepay_id + "&pay_id=" + pay_id + "&price=" + price + "&type=" + type + "&token=" + token + "&param=&notify_url=" + notify_url + "&return_url=" + return_url;
        try {
            String forObject = restTemplate.getForObject(payInterface, String.class, map);
            System.out.println(forObject);
            response.getWriter().write(forObject);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error","支付接口异常，请稍后再试！");
            return "index";
        }
        return null;
    }

    /**
     * @return 设置订单状态
     */
    @RequestMapping("/order/callback")
    public String createOrder(@RequestParam Map<String,Object> map, Model model){
        // 判断是否支付成功

        return null;
    }

    /**
     * @return 到订单查询页面
     */
    @RequestMapping("/order/query/{orderNo}")
    public String orderQuery(@PathVariable String orderNo, Model model){
        //取webStyle
        String byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(CommonConstant.InterfaceConfig.PAY_TYPE.getCode())).toString();
        model.addAttribute("orderNo",orderNo);
        return byType + "/query";
    }


}
