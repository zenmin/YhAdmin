package com.yh.yhadmin.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Title ShellUtil
 * @author Zm
 * @date 2018年4月17日下午1:36:50
 */
@Slf4j
@Component
public class ShellUtil {
    /**
     * @Title: execShell
     * @param shell 要执行的命令
     */
    public static String execShell(String shell) {
        if(StringUtils.isBlank(shell))
            shell = "systeminfo";
        log.debug("===========执行命令开始==========");
        try {
            log.debug(shell);
            Process ps = Runtime.getRuntime().exec(shell);
            ps.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = new String(sb.toString().getBytes("UTF-8"),"UTF-8");
            log.debug("===========执行命令结束======\n结果：" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("异常信息：" + e.getMessage());
        }
        return null;
    }

    public static void main(String args[]){
        System.out.println(ShellUtil.execShell(null));
    }
}
