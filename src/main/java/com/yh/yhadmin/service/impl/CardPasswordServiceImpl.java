package com.yh.yhadmin.service.impl;

import com.google.common.collect.Lists;
import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.CardPasswordRepository;
import com.yh.yhadmin.repository.impl.CardPasswordNativeRepository;
import com.yh.yhadmin.service.CardPasswordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 20:12
 */
@Service
public class CardPasswordServiceImpl implements CardPasswordService {

    @Autowired
    CardPasswordRepository cardPasswordRepository;

    @Autowired
    CardPasswordNativeRepository cardPasswordNativeRepository;

    @Override
    public synchronized Page<CardPassword> findByStatusIsFalseAndGoodsId(String goodsId,Pager pager) {
        Pageable pageable = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.ASC, "createDate");
        return cardPasswordRepository.findByStatusIsFalseAndGoodsId(goodsId,pageable);
    }

    @Override
    public Page<CardPassword> findAll(Pager pager) {
        return cardPasswordNativeRepository.findAllAndGoods(pager,null);
    }

    @HandlerMethod(optName = "卡密管理",optDesc = "批量加卡")
    @Override
    @Transactional
    public Object save(CardPassword cardPassword) {
        List<CardPassword> cardPasswords = Lists.newArrayList();
        //批量加卡
        String cardNo = cardPassword.getCardNo();
        String[] split = cardNo.split("\n");
        for (String c : split){
            if(StringUtils.isNotBlank(c))
                cardPasswords.add(new CardPassword(c,cardPassword.getGoodsId(),null,null,false));
        }
        List<CardPassword> cardPasswords1 = cardPasswordRepository.saveAll(cardPasswords);
        return cardPasswords1 != null;
    }

    @HandlerMethod(optName = "卡密管理",optDesc = "删除卡密")
    @Override
    @Transactional
    public boolean delete(String id) {
        cardPasswordRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<CardPassword> findByCondition(CardPassword cardPassword, Pager pager) {
        Page<CardPassword> allAndGoods = cardPasswordNativeRepository.findAllAndGoods(pager, cardPassword);
        return allAndGoods;
    }

    @HandlerMethod(optName = "卡密管理",optDesc = "批量删除卡密")
    @Override
    @Transactional
    public boolean deleteBatch(Integer type,String cid ,String goodsId) {
        if (type >= 0 && type <= 2) {
            cardPasswordNativeRepository.deleteBatch(type,cid,goodsId);
        } else {
            throw new CommonException(DefinedCode.PARAMS_ERROR, "参数异常！");
        }
        return true;
    }

    @Override
    @Transactional
    @HandlerMethod(optName = "卡密管理",optDesc = "卡密售出 系统更新卡密信息")
    @Async
    public void saveAll(List<CardPassword> cardPasswords) {
        cardPasswordRepository.saveAll(cardPasswords);
    }

    @HandlerMethod(optName = "卡密管理",optDesc = "批量删除卡密")
    @Override
    @Transactional
    public boolean deleteBatchTwo(String ids) {
        List<CardPassword> list = Lists.newArrayList();
        if(ids.indexOf(",") == -1){
            list.add(new CardPassword(ids));
        }else {
            String[] split = ids.split(",");
            for (String id : split){
                list.add(new CardPassword(id));
            }
        }
        cardPasswordRepository.deleteAll(list);
        return true;
    }

}
