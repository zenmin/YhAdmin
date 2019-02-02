package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.OrdersRepository;
import com.yh.yhadmin.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/1/29 0029 15:15
 * @Company
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    public Page<Orders> findAll(Pager pager){
        List<Orders> list = ordersRepository.findAll();
        long count = ordersRepository.count();
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),count);
    }


    @HandlerMethod(optName = "定单管理",optDesc = "生成订单")
    @Transactional
    public Orders save(Orders orders){return ordersRepository.save(orders);}

    @HandlerMethod(optName = "定单管理",optDesc = "取消订单")
    @Transactional
    public boolean delete(String id){
        ordersRepository.deleteById(id);
        return true;
    }
    public Page<Orders> getByCondition(Orders orders,Pager pager){
        orders.setCreateDate(null);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("status",ExampleMatcher.GenericPropertyMatchers.contains())//状态
                .withMatcher("price",ExampleMatcher.GenericPropertyMatchers.contains())//价格
                .withMatcher("allPrice",ExampleMatcher.GenericPropertyMatchers.contains())//总价
                .withMatcher("payWay",ExampleMatcher.GenericPropertyMatchers.contains())//付款方式
                .withMatcher("payStatus",ExampleMatcher.GenericPropertyMatchers.contains())//付款状态
                .withMatcher("payPrice",ExampleMatcher.GenericPropertyMatchers.contains())//实付金额
                .withMatcher("phone",ExampleMatcher.GenericPropertyMatchers.contains())//联系方式
                .withIgnoreCase("id","createDate");
        Example<Orders> ex = Example.of(orders,exampleMatcher);
        List<Orders> list = ordersRepository.findAll(ex);
        long count = ordersRepository.count(ex);
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),count);
    }
}
