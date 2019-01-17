package com.yh.yhadmin.repository.impl;

import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.CardPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Describle This Class Is 卡密查询 jdbc
 * @Author ZengMin
 * @Date 2019/1/17 21:50
 */
@Repository
public class CardPasswordNativeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CardPasswordRepository cardPasswordRepository;

    public Page<CardPassword> findAllAndGoods(Pager pager){
        String sql = String.format("select c.*,g.name as goodsName from cardpassword c left join goods g on  c.goodsId = g.id order by c.goodsId asc limit %s,%s",pager.getStart(),pager.getSize());
        List<CardPassword> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardPassword.class));
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),cardPasswordRepository.count());
    }


}
