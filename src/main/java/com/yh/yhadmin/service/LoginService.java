package com.yh.yhadmin.service;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 18:11
 */
public interface LoginService {

    Object login(String u,String p);

    Object info(String token);

    boolean loginOut(String token);
}
