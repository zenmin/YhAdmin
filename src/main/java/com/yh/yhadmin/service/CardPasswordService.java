package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.query.Pager;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 20:12
 */
public interface CardPasswordService {

    Page<CardPassword> findByStatusIsFalseAndGoodsId(String goodsId,Pager pageable);

    Page<CardPassword> findAll(Pager pager);

    Object save(CardPassword cardPassword);

    boolean delete(String id);

    Page<CardPassword> findByCondition(CardPassword cardPassword,Pager pager);

    boolean deleteBatch(Integer type,String cid ,String goodsId);

    void saveAll(List<CardPassword> cardPasswords);

    boolean deleteBatchTwo(String ids);
}
