package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/1/29 0029 15:08
 * @Company
 */
public interface OrdersRepository extends JpaRepository<Orders,String>{

    Orders findByOrderNo(String orderNo);

}
