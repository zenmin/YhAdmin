package com.yh.yhadmin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

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

    public static String uniqueKeyByTime(Date date){
        String dateTime = DateUtil.millisToDateTime(date.getTime(), "yyyyMMddHHmmssSS");
        dateTime =  dateTime + "" + StaticUtil.uniqueKey();
        return dateTime;
    }

    public static String getToken(){
        return  new Date().getTime()+""+StaticUtil.uniqueKey();
    }

    public static String uploadFile(byte[] file, String filePath, String fileName) {
        try {
            File targetFile = new File(filePath);
            boolean b = targetFile.canWrite();
            if(!b)
                throw new CommonException(DefinedCode.NOTWRITEABLE,"文件夹：" + targetFile.getAbsolutePath() + " 没有写入权限，请给予权限！");
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath + fileName;
    }

    public static String convertMailContent(String conent,String km,String orderNo) {
        conent = conent.replace("${km}", " " + km + " ");
        conent = conent.replace("${orderNo}", " " + orderNo + " ");
        return conent;
    }

    public static String md5Hex(String code){
        return DigestUtils.md5Hex(code);
    }

    public static String sha1512Hex(String code){
        return DigestUtils.sha512Hex(code);
    }

    public static Double divide(Double dividend, Double divisor) {
        return dividend == 0.0D && divisor == 0.0D ? 0.0D : divisor == 0.0D ? 1.0D : BigDecimal.valueOf(dividend).divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public static Double multiply(Double multiplicand, Double multiplier) {
        return multiplicand == 0.0D && multiplier == 0.0D ? 0.0D : multiplier == 0.0D ? 1.0D : BigDecimal.valueOf(multiplicand).multiply(BigDecimal.valueOf(multiplier)).doubleValue();
    }

    public static String compCode(){
        String localMacAddr = IpHelper.getLocalMacAddr() + CommonConstant.AUTH_KEY;
        return String.valueOf(Math.abs(localMacAddr.hashCode()));

    }
    /**
     * @param price 单价
     * @param num   数量
     * @param saleRate 折扣 100
     * @return 计算价格
     */
    public static double computePrice(double price, int num, int saleRate) {
        if(saleRate <= 0)
            saleRate = 100;
        if(num <= 0)
            num = 1;
        double sale = 0.0;
        sale = StaticUtil.divide(Double.valueOf(saleRate),Double.valueOf(100));
        Double multiply = StaticUtil.multiply(price, Double.valueOf(num));
        Double allPrice = StaticUtil.multiply(multiply, sale);
        double v = BigDecimal.valueOf(allPrice).setScale(2,RoundingMode.UP).doubleValue();
        return v;
    }

    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    public static boolean checkNum(String orderNo) {
        return orderNo.matches("^[0-9]*$");
    }

    public static String joinQuota(List<String> list){
        if(list.size() == 1){
            return list.get(0);
        }
        final String[] temp = {""};
        list.stream().forEach(s -> temp[0] += s + ",");
        String result = temp[0].substring(0,temp[0].length()-1);
        return result;
    }

    public static boolean checkAuth(String code) {
        try {
            String localMacAddr = IpHelper.getLocalMacAddr()+ CommonConstant.AUTH_KEY;
            String s = StaticUtil.sha1512Hex(String.valueOf(Math.abs(localMacAddr.hashCode()))).toUpperCase();
            return s.equals(code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String args[]){
        for(int i = 0;i <= 100 ;i++){
            System.out.println(StaticUtil.UUID());
        }
    }

}
