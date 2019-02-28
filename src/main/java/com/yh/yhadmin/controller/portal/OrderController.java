package com.yh.yhadmin.controller.portal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.WebConfig;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.domain.vo.*;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.*;
import com.yh.yhadmin.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Describle This Class Is 前台订单处理
 * @Author ZengMin
 * @Date 2019/2/23 11:47
 */
@Controller
public class OrderController {

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

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    SmsUtil smsUtil;

    @Autowired
    CardPasswordService cardPasswordService;

    @Autowired
    UserInfoUtil userInfoUtill;

    /**
     * @return 创建订单
     */
    @ResponseBody
    @PostMapping("/order/createOrder")
    public ResponseEntity createOrder(OrderVo orderVo, HttpServletRequest request) {
        String ipAddr = IpHelper.getRequestIpAddr(request);
        orderVo.setIp(ipAddr);
        OrderVo orderVo1 = ordersService.createOrder(orderVo);
        return ResponseEntity.success(orderVo1);
    }

    /**
     * @return 到支付页面
     */
    @GetMapping("/order/pay/{orderNo}")
    public String createOrder(@PathVariable String orderNo, RedirectAttributes model, HttpServletResponse response) {
        // 验证订单编号
        boolean b = StaticUtil.checkNum(orderNo);
        if (!b) {
            model.addAttribute("error", "参数异常，已记录IP，请勿触碰法律红线！");
            return "errorPage";
        }
        Orders orders = ordersService.findByOrderNo(orderNo);
        if (orders == null) {
            model.addAttribute("error", "订单有误！");
            return "errorPage";
        }
        if (orders.getStatus() == CommonConstant.STATUS_OK) {
            model.addAttribute("error", "订单已完成！");
            return "errorPage";
        }
        if (orders.getPayStatus() == 2) {
            model.addAttribute("error", "支付超时，请重新下单！");
            return "errorPage";
        }

        /**
         * 接收参数 创建订单
         */
        Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(CommonConstant.InterfaceConfig.PAY_TYPE.getCode()));
        PayConfigVo payConfig = (PayConfigVo) byType;
        WebConfig webConfig = webConfigService.findAll();


        String codepay_id = payConfig.getApp_id();//记得更改 http://codepay.fateqq.com 后台可获得
        String token = payConfig.getApp_key(); //记得更改 http://codepay.fateqq.com 后台可设置

        String price = orders.getAllPrice().toString(); //表单提交的价格
        String type = CommonConstant.PayWay.getCode(orders.getPayWay()).toString(); //支付类型  1：支付宝 2：QQ钱包 3：微信
        String pay_id = orders.getOrderNo(); //支付人的唯一标识 订单号

        String notify_url = "http://" + webConfig.getWebUrl() + "/order/callback";//通知地址

        String return_url = "http://" + webConfig.getWebUrl() + "/order/query/callback/" + orders.getOrderNo();//支付后同步跳转地址

        if (StringUtils.isBlank(price)) {
            price = "1";
        }
        //参数有中文则需要URL编码
        //码支付接口
        String payInterface = "http://api2.fateqq.com:52888/creat_order?";
        Map<String, Object> map = new HashMap<>();
        map.put("id", codepay_id);
        map.put("pay_id", pay_id);
        map.put("price", price);
        map.put("type", type);
        map.put("token", token);
        map.put("param", orders.getOrderKey());
        map.put("notify_url", notify_url);
        map.put("return_url", return_url);
        String urlParams = StaticUtil.getUrlParamsByMap(map);
        try {
            String forObject = restTemplate.getForObject(payInterface + urlParams, String.class);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(forObject);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "支付接口异常，请稍后再试！");
            return "index";
        }
        return null;
    }

    /**
     * @return 码支付回调 设置订单状态
     */
    @RequestMapping("/order/callback")
    @ResponseBody
    public String createOrder(OrderCallBackDo orderCallBackDo) {
        if (orderCallBackDo == null) {
            return "";
        }
        try {
            // 订单号
            String orderNo = orderCallBackDo.getPay_id();
            Orders orders = ordersService.findByOrderNo(orderNo);
            // 验证orderKey 防止hack恶意调接口
            String orderKey = orders.getOrderKey();
            if (!orderKey.equals(orderCallBackDo.getParam().trim()))
                return "errorPage";
            // 验证订单状态
            if (orders.getStatus() == CommonConstant.STATUS_OK)
                return "error";
            // 判断是否支付成功
            String pay_no = orderCallBackDo.getPay_no();
            if (StringUtils.isBlank(pay_no)) {
                // 未支付成功
                orders.setPayStatus(CommonConstant.PAY_STATUS_TIMEOUT);
                orders.setLastModifyDate(new Date());
                ordersService.saveAll(orders);
            } else {
                // 支付成功
                //验证金额  支付金额小于订单金额
                String money = orderCallBackDo.getMoney();
                if (Double.valueOf(money) < orders.getAllPrice()) {
                    return "error";
                }
                orders.setPayStatus(CommonConstant.PAY_STATUS_OK);
                orders.setPayId(pay_no);
                orders.setStatus(CommonConstant.STATUS_OK);
                orders.setLastModifyDate(DateUtil.millisToDate(Long.valueOf(orderCallBackDo.getPay_time())));
                orders.setPayPrice(Double.valueOf(orderCallBackDo.getMoney()));

                // 取出订单数量的卡密 设置进去
                List<String> list = Lists.newArrayList();
                Page<CardPassword> page = cardPasswordService.findByStatusIsFalseAndGoodsId(orders.getGoodsId(), new Pager(0, orders.getNum()));
                List<CardPassword> cardPasswords = page.getContent();
                cardPasswords.stream().forEach(c -> {
                    c.setStatus(true);
                    c.setUseDate(new Date());
                    c.setUseUser(orders.getUserContact());
                    list.add(c.getCardNo());
                });
                String cs = StaticUtil.joinQuota(list);
                orders.setCardPwds(cs);
                cardPasswordService.saveAll(cardPasswords);
                Orders save = ordersService.saveAll(orders);
                if (save != null) {
                    // 发送邮件或短信
                    this.sendMsg(save);
                    // 检查库存
                    this.checkKmCount(save.getGoodsName(), page.getTotalElements() - save.getNum());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String paramsErr = "";
            try {
                paramsErr = StaticUtil.objectMapper.writeValueAsString(orderCallBackDo);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            String content = e.getMessage() + " " + e.getStackTrace()[0];
            emailUtil.sendErrorToAdmin("支付接口回调失败", paramsErr, content);
        }

        return "ok";
    }

    /**
     * @return 到订单查询页面
     */
    @GetMapping({"/order/query/{orderNo}", "/order/query"})
    public String orderQuery(@PathVariable(required = false) String orderNo, Model model, HttpServletRequest request) {
        //取webStyle
        String byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(CommonConstant.InterfaceConfig.INDEX_STYLE.getCode())).toString().replace("index", "");
        // 验证订单编号
        if (StringUtils.isNotBlank(orderNo)) {
            boolean b = StaticUtil.checkNum(orderNo);
            if (!b) {
                model.addAttribute("error", "你提交的参数异常，已记录IP，请勿触碰法律红线！");
                return "errorPage";
            }
        }
        WebConfig webConfig = webConfigService.findAll();
        // 全局配置
        model.addAttribute("config", webConfig);
        // 首页风格
        String style = interfaceConfigService.findByType(CommonConstant.ALL_INTERFACE_CONFIG.get(4)).toString();
        model.addAttribute("tempPath", "/" + style.substring(0, style.lastIndexOf("/")));
        model.addAttribute("tempDefaultPath", "/" + CommonConstant.DEFAULT_TEMP_STATIC_PATH);
        if (StringUtils.isNotBlank(orderNo)) {
            model.addAttribute("orderNo", orderNo);
            List<Orders> byOrderNo = ordersService.findByOrderNoOrUser(orderNo);
            if(byOrderNo.size()>0)
                model.addAttribute("orderList", byOrderNo);
            else
                model.addAttribute("orderList", null);
        } else {
            model.addAttribute("orderList", null);
        }
        return byType + "query";
    }

    @GetMapping("/order/queryd")
    public String toDefaultOrderQqery(Model model) {
        WebConfig webConfig = webConfigService.findAll();
        // 全局配置
        model.addAttribute("config", webConfig);
        // 首页风格
        String style = interfaceConfigService.findByType(CommonConstant.ALL_INTERFACE_CONFIG.get(4)).toString();
        model.addAttribute("tempPath", "/" + style.substring(0, style.lastIndexOf("/")));
        model.addAttribute("tempDefaultPath", "/" + CommonConstant.DEFAULT_TEMP_STATIC_PATH);
        return "webtemps/default/query";
    }

    /**
     * @param orderNo
     * @return 回调跳转
     */
    @GetMapping("/order/query/callback/{orderNo}")
    public String orderQueryCallBack(@PathVariable String orderNo) {
        return "redirect:/order/query/" + orderNo;
    }

    @Async
    public void sendMsg(Orders save) throws JsonProcessingException {
        // 检查是否发送邮件或者短信通知
        if (save.getIsSendEmail() == CommonConstant.STATUS_OK) {
            Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(CommonConstant.InterfaceConfig.MAIL_TYPE.getCode()));
            MailVo mailVo = (MailVo) byType;
            String mailContent = mailVo.getMailContent();
            String content = StaticUtil.convertMailContent(mailContent, save.getCardPwds(), save.getOrderNo());
            emailUtil.sendMail(null, save.getEmail(), content);
        }
        // 是否发送短信
        if (save.getIsSendMsg() == CommonConstant.STATUS_OK) {
            Object byType = interfaceConfigService.findByType(CommonConstant.InterfaceConfig.getValue(CommonConstant.InterfaceConfig.PHONE_TYPE.getCode()));
            SmsVo smsVo = (SmsVo) byType;
            String smsTemplate = smsVo.getSmsTemplate();
            Map<String, Object> map = new HashMap<>();
            if (smsTemplate.indexOf(",") == -1) {
                map.put(smsTemplate, save.getCardPwds());
            } else {
                String[] split = smsTemplate.split(",");
                for (String key : split) {
                    if (CommonConstant.ALL_SENDSMS_CONFIG.get(0).equals(key)) {
                        map.put(key, save.getCardPwds());
                    }
                    if (CommonConstant.ALL_SENDSMS_CONFIG.get(1).equals(key)) {
                        map.put(key, save.getOrderNo());
                    }
                }
            }
            String content = MapConvertUtil.objectMapper.writeValueAsString(map);
            smsUtil.sendSms(save.getUserContact(), content);
        }
    }


    @Async
    public void checkKmCount(String goodsName, Long count) {
        WebConfig webConfig = webConfigService.findAll();
        Integer kmNotice = webConfig.getKmNotice();
        if (kmNotice > 0) {
            if (count < kmNotice) {
                // 低于警戒线  邮件提醒
                emailUtil.sendMsgToAdmin("商品卡密低于库存警戒值，请及时加卡", "商品： <b>" + goodsName + "</b> 目前库存已经低与您设置的警戒值，请及时加卡！");
            }
        }
    }

}
