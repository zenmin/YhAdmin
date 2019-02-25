package com.yh.yhadmin.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;

@Slf4j
public class IpHelper {
    //~ Static fields/initializers =====================================================================================

    /**
     * 通过HttpServletRequest返回IP地址
     *
     * @param request HttpServletRequest
     * @return ip String
     * @throws Exception
     */
    public static String getRequestIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 返回本机ip地址
     *
     * @return 本机ip地址
     * @throws Exception
     */
    public static String getLocalIpAddr() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }

    /**
     * 获取本机mac地址�?
     *
     * @return mac地址
     * @throws Exception
     */
    public static String getLocalMacAddr() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            //下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            return sb.toString().trim().toUpperCase() + "|";
        } catch (Exception e) {
            // 获取不到取本机mac
            try {
                String localHostRealIp = IpHelper.getLocalHostRealIp();
                return localHostRealIp + "|";
            } catch (Exception e1) {
                // 获取不到真实IP
                try {
                    String localIpAddr = IpHelper.getLocalIpAddr();
                    return localIpAddr + "|";
                } catch (Exception e2) {
                    // 获取不到内网Ip
                    return "127.0.0.1";
                }
            }
        }
    }

    /**
     * 通过IP地址获取MAC地址
     *
     * @param ip String,127.0.0.1格式
     * @return mac String
     * @throws Exception
     */
    public static String getMACAddress(String ip) throws Exception {
        String str = "", strMAC = "", macAddress = "";
        try {
            Process pp = Runtime.getRuntime().exec("nbtstat -a " + ip);
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        strMAC = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Get mac address failed : ", e);
        }
        if (strMAC.length() < 17) {
            return "Error!";
        }
        macAddress = strMAC.substring(0, 2) + ":" + strMAC.substring(3, 5) + ":" + strMAC.substring(6, 8) + ":" + strMAC.substring(9, 11) + ":"
                + strMAC.substring(12, 14) + ":" + strMAC.substring(15, 17);
        return macAddress;
    }

    /**
     * 获取本机真实外网ip
     */
    public static String getLocalHostRealIp() {
        String ip = "";
        try {
            String s = HttpClientUtil.sendGet("http://myip.ipip.net/");
            ip = s.substring(s.indexOf("IP：")+3,s.indexOf("来自于")).trim();
        } catch (Exception e) {
        }
        return ip.equals("") ? null : ip;
    }

    /**
     * 判断ip地址是否真实有效
     *
     * @param ip ip地址
     * @return true/false，有�?/无效�?
     */
    public static boolean isValidIpAddr(String ip) {
        if (ip == null || "".equals(ip))
            return false;
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        if (ip.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }
}

