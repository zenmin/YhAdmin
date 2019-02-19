package com.yh.yhadmin.domain.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/19 18:38
 */
@Data
public class AdminUserVo {

    private String avatar = "/src/icons/f778738c-e4f8-4870-b634-56703b4acafe.gif";

    private List<String> roles = Lists.newArrayList("admin");

    private String name ;

    private String token;

    private String phone;

    private String qq;

    private Integer status;

    public AdminUserVo(String name, String token, String phone, String qq, Integer status) {
        this.name = name;
        this.token = token;
        this.phone = phone;
        this.qq = qq;
        this.status = status;
    }

    public AdminUserVo() {
    }
}
