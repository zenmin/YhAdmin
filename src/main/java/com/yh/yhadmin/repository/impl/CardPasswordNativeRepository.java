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
            String cid = cardPassword.getCid();
            if(StringUtils.isNotBlank(goodsId)){
                params.append(" and c.goodsId = '"+ goodsId + "'");
            }
            if(StringUtils.isNotBlank(cardNo)){
                params.append(" and c.cardNo = '"+ cardNo + "'");
            }
            if(status != null ){
                params.append(" and c.status = "+ status);
            }
            if(StringUtils.isNotBlank(cid)){
                params.append(" and y.id = '"+ cid + "'");
            }
        }
        String sql = String.format("select c.*,g.name as goodsName,y.name as cname from cardpassword c left join goods g on c.goodsId = g.id LEFT JOIN category y on g.cid = y.id where 1=1 %s order by c.createDate desc limit %s,%s",params.toString(),pager.getStart()*pager.getSize(),pager.getSize());
        List<CardPassword> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardPassword.class));
        String countSql = String.format("select count(1) from cardpassword c left join goods g on c.goodsId = g.id LEFT JOIN category y on g.cid = y.id where 1=1 %s order by c.createDate desc ",params.toString());
        Long count = jdbcTemplate.queryForObject(countSql, Long.class);
        return new PageImpl<>(list,new PageRequest(pager.getStart(),pager.getSize()),count);
    }

    public boolean deleteBatch(Integer type,String cid,String goodsId){
        StringBuffer sql = new StringBuffer("DELETE from cardpassword where 1=1 ");
        switch (type){
            case 0:     // 全部
                break;
            case 1:     // 删除已使用的卡密
                sql.append(" and status = 1");
                break;
            case 2:     // 删除未使用的卡密
                sql.append(" and status = 0");
                break;
            default:
                break;
        }
        if(StringUtils.isNotBlank(cid)){
            if(StringUtils.isBlank(goodsId))
                sql.append(String.format(" and goodsId in (select id from goods where cid = '%s')",cid));
        }
        if(StringUtils.isNotBlank(goodsId)){
            sql.append(String.format(" and goodsId = '%s'",goodsId));
        }
        int update = jdbcTemplate.update(sql.toString());
        return update > 0;
    }


}
