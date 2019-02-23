package com.yh.yhadmin.service;

import com.yh.yhadmin.domain.WebConfig;

import java.util.List;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 16:07
 * @Company
 */
public interface WebConfigService {

    WebConfig findAll();

    WebConfig save(WebConfig webConfig);

    void updateAdminEmail(String adminEmail);
}
