package com.yh.yhadmin.repository.impl;

import com.yh.yhadmin.domain.Orders;
import com.yh.yhadmin.domain.vo.IndexInfoVo;
import com.yh.yhadmin.domain.vo.OrdersCencus;
import com.yh.yhadmin.domain.query.Pager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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

    /**
     * 订单总数
     * 成功订单数
     * 总销售金额
     * 卡密总数
     * 售出卡密
     * 总购买客户数
     * 支付方式分析
     * @return
     */
    public OrdersCencus getCencusByJDBC() {
        OrdersCencus ordersCencus = new OrdersCencus();
        String sql = " SELECT count(1) as allCount,(SELECT count(1) from orders where `status` =1 ) as successCount,(SELECT IFNULL(ROUND(SUM(price),2),0.00) from orders where `status` = 1 ) as allPrice," +
                "(SELECT count(1) from cardpassword) as allCardPwds,(SELECT count(1) from cardpassword where `status` = 1) as successCardPwds,(SELECT count(DISTINCT(ip)) from orders) as consumerCount from orders";
        List<OrdersCencus> ordersCencuses = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdersCencus.class));
        if(ordersCencuses.size() > 0){
            ordersCencus = ordersCencuses.get(0);
        }
        String paysql = "SELECT count(1) as value,payWay as name FROM `orders` where `status` = 1 GROUP BY payWay";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(paysql);
        ordersCencus.setPayAlias(maps);
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
    public IndexInfoVo getIndexInfo() {
        String indexSql = "SELECT count( DISTINCT ( ip ) ) AS orderUsers, ( SELECT count( 1 ) FROM cardpassword WHERE STATUS = 0 ) AS cardPwds, ( SELECT IFNULL( ROUND( sum( allPrice ), 2 ), 0 ) FROM orders WHERE date( createDate ) = CURRENT_DATE and `status` = 1) AS nowPrice, ( SELECT count( 1 ) FROM orders WHERE date( createDate ) = CURRENT_DATE ) AS orderNum FROM orders";
        IndexInfoVo indexInfoVo = jdbcTemplate.queryForObject(indexSql, new BeanPropertyRowMapper<>(IndexInfoVo.class));
        String sevenOrdersSql = "SELECT * from(SELECT count( 1 ) AS num, IFNULL(ROUND(SUM(allPrice),2),0.00) as price, date( createDate ) AS date FROM orders where `status` = 1 GROUP BY date ORDER BY date desc LIMIT 7)as _ ORDER BY date asc";
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
        String finishOrderSql = "SELECT * from( SELECT count(1) as count,date(createDate) as date FROM `orders` where `status` = 1 GROUP BY date order by date desc LIMIT 7) as _ order by date asc";
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList(finishOrderSql);
        indexInfoVo.setFinishOrderAlias(maps1);
        return indexInfoVo;
    }

    /**
     * @param orderNo
     * @return 前台查询最近一个月订单
     */
    public List<Orders> findByOrderOrUser(String orderNo) {
        String sql = "select * from orders where orderNo = ? or userContact = ? order by lastModifyDate desc limit 31";
        log.info(sql);
        List<Orders> query = jdbcTemplate.query(sql, new Object[]{orderNo, orderNo}, new BeanPropertyRowMapper<>(Orders.class));
        return query;
    }
}
