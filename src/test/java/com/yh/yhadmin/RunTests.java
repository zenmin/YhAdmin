package com.yh.yhadmin;

import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.Coupon;
import com.yh.yhadmin.domain.Goods;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.repository.CardPasswordRepository;
import com.yh.yhadmin.repository.CouponRepository;
import com.yh.yhadmin.repository.GoodsRepository;
import com.yh.yhadmin.service.CardPasswordService;
import com.yh.yhadmin.util.DateUtil;
import com.yh.yhadmin.util.StaticUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.NumberFormat;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RunTests {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    CardPasswordRepository cardPasswordRepository;


    @Autowired
    CardPasswordService cardPasswordService;

    @Autowired
    CouponRepository couponRepository;


    static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    static {
        numberFormat.setMaximumFractionDigits(2);
    }

    @Test
    public void contextLoads() {
        int a = 65;
        for (int i=0;i<=100;i++){
            String name = String.valueOf((char)a++);
            if(i == 50){
                a = 65;
            }
            if(i>=50)
                name += String.valueOf((char)a + 1);

            Goods goods = new Goods(name,"描述"+name,"",i%2==0?"402899816851e22d016851e2b6690004":"402899816851e3bf016851e4061e0007",1,Double.valueOf((numberFormat.format(i * 12.1)).replace(",","")),false,null);
            goodsRepository.save(goods);
        }

    }

    @Test
    public void card() {
        for (int i = 0;i<=100;i++){
            cardPasswordRepository.save(new CardPassword(StaticUtil.UUID(),"123ew123123123","zm","123123",false,null,null));
        }

    }

    @Test
    public void test2() {
        System.out.println(cardPasswordService.findAll(new Pager()));
    }

    @Test
    public void test3() {
       for (int i = 0;i<500;i++){
           couponRepository.save(new Coupon(StaticUtil.uniqueKey(), 0, DateUtil.parseToDateMilis("2019-02-01 12:23:34"), null, null, "zm", 1, 80, "描述" + (char) i));
       }
    }
}

