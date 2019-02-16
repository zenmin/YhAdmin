package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.impl.OrdersNativeRepository;
import com.yh.yhadmin.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/16 14:52
 */
@Service
public class IndexServerImpl implements IndexService {

    @Autowired
    OrdersNativeRepository ordersNativeRepository;

    @Override
    public Object getNow() {
        return ordersNativeRepository.getIndexInfo();
    }

    @Override
    public Object getPayway() {
        return ordersNativeRepository.getPayway();
    }

    @Override
    public Object getNowOrder() {
        Page<Orders> all = ordersNativeRepository.getAll(new Pager(0, 8), null);
        List<Orders> content = all.getContent();
        return content;
    }
}
