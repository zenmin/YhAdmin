package com.yh.yhadmin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Coupon;
import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.vo.GoodsVo;
import com.yh.yhadmin.domain.vo.MailVo;
import com.yh.yhadmin.domain.vo.OrderVo;
import com.yh.yhadmin.domain.vo.OrdersCencus;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.OrdersRepository;
import com.yh.yhadmin.repository.impl.OrdersNativeRepository;
import com.yh.yhadmin.service.CouponService;
import com.yh.yhadmin.service.GoodsService;
import com.yh.yhadmin.service.InterfaceConfigService;
import com.yh.yhadmin.service.OrdersService;
import com.yh.yhadmin.util.EmailUtil;
import com.yh.yhadmin.util.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/1/29 0029 15:15
 * @Company
 */
@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrdersNativeRepository ordersNativeRepository;

    @Autowired
    GoodsService goodsService;

    @Autowired
    CouponService couponService;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    InterfaceConfigService interfaceConfigService;

    public Page<Orders> findAll(Pager pager) {
        return ordersNativeRepository.getAll(pager, null);
    }

    @HandlerMethod(optName = "订单管理", optDesc = "生成订单")
    @Transactional
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    public Page<Orders> getByCondition(Orders orders, Pager pager) {
        return ordersNativeRepository.getAll(pager, orders);
    }

    public List<OrdersCencus> getCencus() {
        List<OrdersCencus> list = ordersNativeRepository.getCencusByJDBC();
        return list;
    }

    /**
     * 前台下单
     * 1、检查库存是否足够
     * 2、是否需要提取密码 密码是否正确
     * 3、下单数量是否大于库存
     * 4、是否使用优惠券  优惠券是否有效
     * 5、取下单数量计算价格
     * 6、生成订单
     * 7、如果是一次性优惠券  设置他为无效
     * @param orderVo
     * @return
     */
    @Override
    @HandlerMethod(optName = "订单管理", optDesc = "用户下单")
    @Transactional
    public OrderVo createOrder(OrderVo orderVo) {

            // 基本验证
            Integer number = orderVo.getNumber();
            if (number <= 0)
                throw new CommonException(DefinedCode.PARAMSERROR, "下单数量必须大于0！");
            String userContact = orderVo.getUserContact();
            if (StringUtils.isBlank(userContact) || !userContact.matches("^\\d{5,}$"))
                throw new CommonException(DefinedCode.PARAMSERROR, "联系方式必须为QQ或手机号！");
            String email = orderVo.getEmail();
            if (orderVo.getIsSendEmail() == CommonConstant.STATUS_OK &&
                    (StringUtils.isBlank(email) || !email.matches("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$")))
                throw new CommonException(DefinedCode.PARAMSERROR, "邮箱格式不正确！");
            // 业务验证
            // 检查库存是否足够
            List<GoodsVo> content = goodsService.findByCondition(new Goods(orderVo.getGoodsId()), new Pager(0, 1)).getContent();
            if (content.size() == 0)
                throw new CommonException(DefinedCode.NOTFOUND, "商品已经不存在，请刷新页面！");
            GoodsVo goodsVoHome = content.get(0);
            if (goodsVoHome.getKmCount() <= 0)
                throw new CommonException(DefinedCode.NOSTOCK, "库存不足！");
            if (orderVo.getNumber() > goodsVoHome.getKmCount())
                throw new CommonException(DefinedCode.NOSTOCK, "下单数量大于库存！");
            // 提取密码是否正确
            if (goodsVoHome.getNeedPwd() && !goodsVoHome.getPullPwd().equals(orderVo.getPullPwd()))
                throw new CommonException(DefinedCode.PASSWORDERROR, "提取密码错误");
            // 检查优惠券是否有效
            String coupon = orderVo.getCoupon();
            Integer saleRate = 100;                // 折扣
            Coupon byCouponNO = new Coupon();
            if (StringUtils.isNotBlank(coupon)) {
                byCouponNO = couponService.findByCouponNO(coupon);
                if (byCouponNO == null)
                    throw new CommonException(DefinedCode.NOTCANUSE, "优惠券无效！");
                if(byCouponNO.getStatus() == CommonConstant.STATUS_ERROR)
                    throw new CommonException(DefinedCode.NOTCANUSE, "优惠券无效！");
                //是否是长期有效优惠券
                if (byCouponNO.getValidLong() == CommonConstant.STATUS_ERROR || byCouponNO.getDisDate().getTime() < new Date().getTime())
                    throw new CommonException(DefinedCode.DATEDIS, "优惠券已过期！");
                saleRate = byCouponNO == null ? 100 : byCouponNO.getSaleRate();
                // 设置一次性优惠券无效
                byCouponNO.setUseDate(new Date());
                byCouponNO.setUseUser(orderVo.getUserContact());
                orderVo.setCoupon(byCouponNO.getSaleRate().toString());
            }
        try {
            Integer number1 = orderVo.getNumber();
            // 生成总价
            double allPrice = StaticUtil.computePrice(goodsVoHome.getPrice(), number1, saleRate);
            // 生成订单
            String orderNo = StaticUtil.uniqueKeyByTime(new Date());
            Orders orders = new Orders(orderNo, CommonConstant.STATUS_ERROR, goodsVoHome.getId(), goodsVoHome.getPrice(), allPrice, orderVo.getNumber(), orderVo.getPayType(), CommonConstant.STATUS_ERROR, orderVo.getCoupon(), orderVo.getIp(), orderVo.getUserContact(), orderVo.getIsSendMsg(), orderVo.getIsSendEmail(), orderVo.getEmail(), new Date(),goodsVoHome.getName(),StaticUtil.md5Hex(StaticUtil.uniqueKey()));
            Orders save = ordersRepository.save(orders);
            orderVo.setAllPrice(allPrice);
            orderVo.setId(orderNo);
            orderVo.setCreateDate(save.getCreateDate());
            orderVo.setGoodsName(goodsVoHome.getName());
            orderVo.setPrice(goodsVoHome.getPrice());
            couponService.checkCoupon(byCouponNO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(DefinedCode.ERROR, "订单生成失败！");
        }
        return orderVo;
    }

    @Override
    public Orders findByOrderNo(String orderNo) {
        Orders byOrderNo = ordersRepository.findByOrderNo(orderNo);
        return byOrderNo;
    }

    @Override
    @Transactional
    public Orders saveAll(Orders orders) {
        return ordersRepository.save(orders);
    }

    /**
     * @param orderNo
     * @return 通过订单编号或联系方式查询
     */
    @Override
    public Orders findByOrderNoOrUser(String orderNo,String ip) {
        return ordersNativeRepository.findByOrderOrUser(orderNo,ip);
    }
}
