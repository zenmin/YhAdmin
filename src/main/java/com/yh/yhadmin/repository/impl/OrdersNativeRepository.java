package com.yh.yhadmin.repository.impl;

import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.vo.IndexInfoVo;
import com.yh.yhadmin.domain.vo.OrdersCencus;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<OrdersCencus> getCencusByJDBC() {
        String sql = "select COUNT(*)as total,COUNT(status = 1) as complete,(select count(*) from orders where DATE(createDate) = CURDATE()) as total_today,(select count(*) from cardpassword) as total_card from orders";
        List<OrdersCencus> ordersCencus = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdersCencus.class));
        return ordersCencus;
    }

    /**
     * @param pager
     * @param orders
     * @return 订单查询
     */
    public Page<Orders> getAll(Pager pager, Orders orders) {
        StringBuffer params = new StringBuffer("");
        if (orders != null) {
            String orderNo = orders.getOrderNo();
            if (StringUtils.isNotBlank(orderNo))
                params.append(" and o.orderNo = '" + orderNo + "'");
            Integer status = orders.getStatus();
            if (status != null)
                params.append(" and o.status = " + status);
            String goodsId = orders.getGoodsId();
            if (StringUtils.isNotBlank(goodsId))
                params.append(" and o.goodsId = '" + goodsId + "'");
            String userContact = orders.getUserContact();
            if (StringUtils.isNotBlank(userContact))
                params.append(" and o.userContact like '%" + userContact + "%'");
            Integer phone = orders.getPhone();
            if (phone != null)
                params.append(" and o.phone = '" + phone + "'");
            String cardPwds = orders.getCardPwds();
            if (StringUtils.isNotBlank(cardPwds))
                params.append(" and o.cardPwds like '%" + cardPwds + "%'");
            String beginTime = orders.getBeginTime();
            if(StringUtils.isNotBlank(beginTime))
                params.append(" and o.createDate >= '"+beginTime+"'");
            String endTime = orders.getEndTime();
            if(StringUtils.isNotBlank(endTime))
                params.append(" and o.createDate <= '"+endTime+"'");
        }
        String sql = String.format("SELECT o.*,g.name as goodsName FROM orders o LEFT JOIN goods g on o.goodsId = g.id where 1=1 %s ORDER BY o.createDate desc limit %s,%s"
                , params.toString(), pager.getStart() * pager.getSize(), pager.getSize());
        List<Orders> ordersList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Orders.class));
        String countSql = String.format("SELECT count(1) FROM orders o LEFT JOIN goods g on o.goodsId = g.id where 1=1 %s ORDER BY o.createDate "
                , params.toString());
        Long count = jdbcTemplate.queryForObject(countSql, Long.class);
        return new PageImpl<>(ordersList, new PageRequest(pager.getStart(), pager.getSize()), count);
    }

    /**
     * @return
     * 1、下单用户数
     * 2、库存卡密
     * 3、今日入账金额
     * 4、今日订单数量
     * 5、七日成功订单和总价
     */
    public IndexInfoVo getIndexInfo(){
        String indexSql = "SELECT count( DISTINCT ( ip ) ) AS orderUsers, ( SELECT count( 1 ) FROM cardpassword WHERE STATUS = 0 ) AS cardPwds, ( SELECT IFNULL( ROUND( sum( allPrice ), 2 ), 0 ) from orders WHERE date( createDate ) = CURRENT_DATE ) AS nowPrice, ( SELECT count(1) from orders WHERE date( createDate ) = CURRENT_DATE ) AS orderNum FROM orders";
        IndexInfoVo indexInfoVo = jdbcTemplate.queryForObject(indexSql, new BeanPropertyRowMapper<>(IndexInfoVo.class));
        String sevenOrdersSql = String.format("SELECT count(1) as num,IFNULL( ROUND( sum( allPrice ), 2 ), 0 ) as price,date(createDate) as date from orders GROUP BY date order by date asc limit 7");
        List<Map<String, Object>> hashMaps = jdbcTemplate.queryForList(sevenOrdersSql);
        indexInfoVo.setSevenOrders(hashMaps);
        return indexInfoVo;
    }


    /**
     * @return
     * 6、支付方式分析
     * 7、商品卖出订单分析
     * 查最近一周
     */
    public Object getPayway() {
        IndexInfoVo indexInfoVo = new IndexInfoVo();
        String paysql = "SELECT count(1) as value,payWay as name FROM `orders` where `status` = 1 GROUP BY payWay";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(paysql);
        indexInfoVo.setPayAlias(maps);
        String finishOrderSql = "SELECT count(1) as count,date(createDate) as date FROM `orders` where `status` = 1 GROUP BY date order by date asc LIMIT 7";
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList(finishOrderSql);
        indexInfoVo.setFinishOrderAlias(maps1);
        return indexInfoVo;
    }
}
