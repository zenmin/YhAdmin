package com.yh.yhadmin.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 18:11
 */
public interface LoginService {

    Object login(String u,String p,HttpServletRequest request);

    Object info(String token);

    boolean loginOut(HttpServletRequest request);
}
