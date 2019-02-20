package com.yh.yhadmin.repository.impl;

import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.domain.vo.GoodsVo;
import com.yh.yhadmin.domain.vo.GoodsVoHome;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/26 20:06
 */
@Repository
@Slf4j
public class GoodsNativeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<GoodsVo> findAllAndCateGory(Pager pager) {
        String sql = "SELECT g.*,c.name as cname,(SELECT count(1) FROM cardpassword c where c.goodsId = g.id  and c.status = 0) as kmCount from goods g LEFT JOIN category c on g.cid = c.id ORDER BY g.createDate desc LIMIT ?,?";
        List<GoodsVo> goodsVos = jdbcTemplate.query(sql,new Object[]{pager.getStart()*pager.getSize(),pager.getSize()} ,new BeanPropertyRowMapper<>(GoodsVo.class));
        return goodsVos;
    }

    public List<GoodsVo> findByCondition(Goods goods, Pager pager) {
        StringBuffer sql = new StringBuffer("SELECT g.*,c.name as cname,(SELECT count(1) FROM cardpassword c where c.goodsId = g.id  and c.status = 0) as kmCount from goods g LEFT JOIN category c on g.cid = c.id where 1=1 ");
        if (StringUtils.isNotBlank(goods.getName()))
            sql.append(" and g.name like '%" + goods.getName() + "%'");
        if (StringUtils.isNotBlank(goods.getCid()))
            sql.append(" and g.cid = '" + goods.getCid() + "'");
        if (goods.getStatus() != null)
            sql.append(" and g.status = " + goods.getStatus());
        if (goods.getId() != null)
            sql.append(" and g.id = '" + goods.getId() + "'");
        sql.append(" ORDER BY g.createDate desc LIMIT ?,?");
        List<GoodsVo> goodsVos = jdbcTemplate.query(sql.toString(), new Object[]{pager.getStart() * pager.getSize(), pager.getSize()}, new BeanPropertyRowMapper<>(GoodsVo.class));
        log.info(sql.toString());
        return goodsVos;
    }

    public List<GoodsVoHome> findByConditionHome(Goods goods) {
        StringBuffer sql = new StringBuffer("SELECT g.*,c.name as cname,(SELECT count(1) FROM cardpassword c where c.goodsId = g.id  and c.status = 0) as kmCount from goods g LEFT JOIN category c on g.cid = c.id where 1=1 ");
        if (StringUtils.isNotBlank(goods.getName()))
            sql.append(" and g.name like '%" + goods.getName() + "%'");
        if (StringUtils.isNotBlank(goods.getCid()))
            sql.append(" and g.cid = '" + goods.getCid() + "'");
        if (goods.getStatus() != null)
            sql.append(" and g.status = " + goods.getStatus());
        if (goods.getId() != null)
            sql.append(" and g.id = '" + goods.getId() + "'");
        sql.append(" ORDER BY g.createDate");
        List<GoodsVoHome> goodsVos = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(GoodsVoHome.class));
        log.info(sql.toString());
        return goodsVos;
    }
}
