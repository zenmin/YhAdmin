package com.yh.yhadmin.controller;

import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.ResponseEntity;
import com.yh.yhadmin.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/1/29 0029 14:50
 * @Company
 */
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @RequestMapping("/getAll")
    public ResponseEntity findAll(Pager pager) {
        return ResponseEntity.success(ordersService.findAll(pager));
    }

    @RequestMapping("/save")
    public ResponseEntity save(Orders orders) {
        return ResponseEntity.success(ordersService.save(orders));
    }

    @RequestMapping("/getByCondition")
    public ResponseEntity getByCondition(Orders orders, Pager pager) {
        return ResponseEntity.success(ordersService.getByCondition(orders, pager));
    }

    @PostMapping("/getcCensus")
    public ResponseEntity getcCensus() {
        return ResponseEntity.success(ordersService.getCencus());
    }
}
