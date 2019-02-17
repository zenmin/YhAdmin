package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.components.annotation.HandlerMethod;
import com.yh.yhadmin.domain.InterfaceConfig;
import com.yh.yhadmin.domain.vo.MailVo;
import com.yh.yhadmin.domain.vo.PayConfigVo;
import com.yh.yhadmin.domain.vo.PaySwitchVo;
import com.yh.yhadmin.domain.vo.SmsVo;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.repository.InterfaceConfigRepository;
import com.yh.yhadmin.service.InterfaceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:10
 * @Company
 */
@Service
@CacheConfig(cacheNames = "InterfaceConfig")
public class InterfaceConfigServiceImpl implements InterfaceConfigService {

    @Autowired
    InterfaceConfigRepository interfaceConfigRepository;

    public List<InterfaceConfig> findAll() {
        return interfaceConfigRepository.findAll();
    }

    @HandlerMethod(optName = "interfaceConfig 接口配置", optDesc = "更新配置")
    @Transactional
    @CacheEvict(allEntries = true)
    public InterfaceConfig save(InterfaceConfig interfaceConfig) {
        Integer type = interfaceConfig.getType();
        String value = CommonConstant.InterfaceConfig.getValue(type);
        InterfaceConfig byInterfaceType = interfaceConfigRepository.findByInterfaceType(value);
        if (byInterfaceType != null) {
            interfaceConfig.setId(byInterfaceType.getId());
        }
        interfaceConfig.setInterfaceType(value);
        return interfaceConfigRepository.saveAndFlush(interfaceConfig);
    }

    @Override
    @Cacheable
    public Object findByType(String typeCode) {
        InterfaceConfig i = interfaceConfigRepository.findByInterfaceType(typeCode);
        //1 短信接口
        if (typeCode.equals(CommonConstant.InterfaceConfig.PHONE_TYPE.getValue())) {
            return new SmsVo(CommonConstant.InterfaceConfig.PHONE_TYPE.getCode(), i.getApp_id(), i.getApp_key()
                    , i.getSmsTemplateCode(), i.getSmsTemplate(), i.getSmsSignName(), i.getStatus());
        }
        //2 邮件发送
        if (typeCode.equals(CommonConstant.InterfaceConfig.MAIL_TYPE.getValue())) {
            return new MailVo(i.getStatus(), i.getMailSMTP(), i.getMailAccount(), i.getMailPwd(), i.getMailTitle(), i.getMailContent());
        }
        //3 支付接口
        if (typeCode.equals(CommonConstant.InterfaceConfig.PAY_TYPE.getValue())) {
            return new PayConfigVo(i.getPayType(), i.getApp_id(), i.getApp_key());
        }
        //4 支付接口开关
        if (typeCode.equals(CommonConstant.InterfaceConfig.PAY_SWITCH_TYPE.getValue())) {
            return new PaySwitchVo(i.getSwitch_alipay(), i.getSwitch_qq(), i.getSwitch_wx());
        }
        //5 首页风格
        if (typeCode.equals(CommonConstant.InterfaceConfig.INDEX_STYLE.getValue())) {
            return i.getIndex_style();
        }
        throw new CommonException(DefinedCode.PARAMS_ERROR, "该类型不存在");
    }
}
