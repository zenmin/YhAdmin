package com.yh.yhadmin.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.NumberFormat;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/16 22:04
 */
public class StaticUtil {

    public static NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
    }

}
