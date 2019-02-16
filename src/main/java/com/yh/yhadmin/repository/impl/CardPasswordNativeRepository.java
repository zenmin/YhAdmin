package com.yh.yhadmin.repository.impl;

import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.CardPasswordRepository;
import org.apache.commons.lang.StringUtils;
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

    public Page<CardPassword> findAllAndGoods(Pager pager,CardPassword cardPassword){
        StringBuffer params = new StringBuffer("");
        if(cardPassword != null){
            String goodsId = cardPassword.getGoodsId();
            String cardNo = cardPassword.getCardNo();
            Boolean status = cardPassword.getStatus();
            if(StringUtils.isNotBlank(goodsId)){
                params.append(" and c.goodsId = '"+ goodsId + "'");
            }
            if(StringUtils.isNotBlank(cardNo)){
                params.append(" and c.cardNo = '"+ cardNo + "'");
            }
            if(status != null ){
                params.append(" and c.status = "+ status);
            }
        }
        String sql = String.format("select c.*,g.name as goodsName from cardpassword c left join goods g on c.goodsId = g.id where 1=1 %s order by c.goodsId asc limit %s,%s",params.toString(),pager.getStart()*pager.getSize(),pager.getSize());
        List<CardPassword> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardPassword.class));
        String countSql = String.format("select count(1) from cardpassword c left join goods g on c.goodsId = g.id where 1=1 %s order by c.goodsId asc ",params.toString());
        Long count = jdbcTemplate.queryForObject(countSql, Long.class);
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),count);
    }


}
