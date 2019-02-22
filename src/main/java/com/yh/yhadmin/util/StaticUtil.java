package com.yh.yhadmin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;
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

    public static void main(String args[]){
    }

}
