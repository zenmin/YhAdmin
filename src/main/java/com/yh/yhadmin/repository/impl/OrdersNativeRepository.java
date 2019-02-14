package com.yh.yhadmin.repository.impl;

import com.yh.yhadmin.domain.OrdersCencus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/13 0013 17:24
 * @Company
 */
@Repository
@Slf4j
public class OrdersNativeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<OrdersCencus> getCencusByJDBC(){
        String sql = "select COUNT(*)as total,COUNT(status = 1) as complete,(select count(*) from orders where DATE(createDate) = CURDATE()) as total_today,(select count(*) from cardpassword) as total_card from orders";
        List<OrdersCencus> ordersCencus = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(OrdersCencus.class));
        return ordersCencus;
    }
}
