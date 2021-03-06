package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.vo.OrderVo;
import com.yh.yhadmin.domain.vo.OrdersCencus;
import com.yh.yhadmin.domain.query.Pager;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/1/29 0029 15:03
 * @Company
 */
public interface OrdersService {

     Page<Orders> findAll(Pager pager);

     Orders save(Orders orders);

     Page<Orders> getByCondition(Orders orders,Pager pager);

    OrdersCencus getCencus();

    OrderVo createOrder(OrderVo orderVo);

    Orders findByOrderNo(String orderNo);

    Orders saveAll(Orders orders);

    List<Orders> findByOrderNoOrUser(String orderNo);

    void sendMsg(Orders save);

    void checkKmCount(String goodsName, Long count);

}
