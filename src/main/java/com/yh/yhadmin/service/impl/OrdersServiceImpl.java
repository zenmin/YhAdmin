package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.vo.OrdersCencus;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.OrdersRepository;
import com.yh.yhadmin.repository.impl.OrdersNativeRepository;
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

    @Autowired
    OrdersNativeRepository ordersNativeRepository;

    public Page<Orders> findAll(Pager pager){
        return ordersNativeRepository.getAll(pager,null);
    }

    @HandlerMethod(optName = "订单管理",optDesc = "生成订单")
    @Transactional
    public Orders save(Orders orders){
        return ordersRepository.save(orders);
    }

    public Page<Orders> getByCondition(Orders orders,Pager pager){
        return ordersNativeRepository.getAll(pager,orders);
    }

    public List<OrdersCencus> getCencus(){
        List<OrdersCencus> list = ordersNativeRepository.getCencusByJDBC();
        return list;
    }
}
