package com.yh.yhadmin.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    //常规UUID
    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //UUID的hashcode +默认随机数  还是有可能重复 1000000万数据重复263个
    public static String uniqueKey() {
        int abs = Math.abs(Integer.parseInt(String.valueOf(StaticUtil.UUID().hashCode())));
        int random = (int) Math.random() * 1000;
        String temp = String.valueOf(random + abs);
        while (temp.length() < 10) {
            temp += "0";
        }
        if (temp.length() > 10) {
            temp = temp.substring(0, 10);
        }
        return temp;
    }
    public static void main(String a[]){
        Set<String> set = new HashSet<>();
        for (int i = 0; i <= 1000000;i++){
            String s = uniqueKey();
            set.add(s);
            System.out.println(s);
        }
        System.out.println(set.size());
    }


}
