package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.CardPassword;
import com.yh.yhadmin.domain.query.Pager;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.CardPasswordRepository;
import com.yh.yhadmin.repository.impl.CardPasswordNativeRepository;
import com.yh.yhadmin.service.CardPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<CardPassword> findByStatusIsFalse(Pager pager) {
        Pageable pageable = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.ASC, "goodsId");
        return cardPasswordRepository.findByStatusIsFalse(pageable);
    }

    @Override
    public Page<CardPassword> findAll(Pager pager) {
        return cardPasswordNativeRepository.findAllAndGoods(pager);
    }

    @Override
    @Transactional
    public CardPassword save(CardPassword cardPassword) {
        return cardPasswordRepository.saveAndFlush(cardPassword);
    }

    @Override
    @Transactional
    public boolean delete(List<CardPassword> id) {
        cardPasswordRepository.deleteAll(id);
        return true;
    }

    @Override
    public Page<CardPassword> findByCondition(CardPassword cardPassword, Pager pager) {
        cardPassword.setCreateDate(null);
        Example<CardPassword> of = Example.of(cardPassword);
        Pageable pageable = new PageRequest(pager.getStart(), pager.getSize(), Sort.Direction.DESC, "goodsId");
        Page<CardPassword> all = cardPasswordRepository.findAll(of, pageable);
        return all;
    }

    @Override
    @Transactional
    public boolean deleteBatch(Integer type) {
        if (type == CommonConstant.DELETE_TYPE_ALL) {
            cardPasswordRepository.deleteAll();
        } else if (type == CommonConstant.DELETE_TYPE_TRUE) {
            cardPasswordRepository.deleteByStatus(true);
        } else if (type == CommonConstant.DELETE_TYPE_FALSE) {
            cardPasswordRepository.deleteByStatus(false);
        } else {
            throw new CommonException(DefinedCode.PARAMS_ERROR, "参数异常！");
        }
        return true;
    }

}
