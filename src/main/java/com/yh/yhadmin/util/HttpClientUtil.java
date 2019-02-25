package com.yh.yhadmin.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * HTTP请求客户端，提供GET和POST两种方式发送请求。
 *
 * @author Administrator
 *
 */
@Slf4j
public class HttpClientUtil {

    public static ObjectMapper objectMapper = new ObjectMapper();


    public static String sendPostJson(String url, String params, String charset) throws IOException {

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/json; charset="+charset);
        if(params != null && !params.trim().equals("")) {
            RequestEntity requestEntity = new StringRequestEntity(params,"application/json",charset);
            postMethod.setRequestEntity(requestEntity);
        }
        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        String soapResponseData = postMethod.getResponseBodyAsString();

        return soapResponseData;
    }

    public static String sendPost(String url, String params) throws IOException {

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        if(params != null && !params.trim().equals("")) {
            RequestEntity requestEntity = new StringRequestEntity(params,"text/xml","UTF-8");
            postMethod.setRequestEntity(requestEntity);
        }

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        InputStream soapResponseData = postMethod.getResponseBodyAsStream();
        return getString(soapResponseData);
    }

    public static String sendPost(String url, Map params, String ticket) throws IOException {

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        postMethod.setRequestHeader("ticket", ticket);
        List<NameValuePair> data = new ArrayList<NameValuePair>();

        if (Objects.nonNull(params)){
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                data.add(new NameValuePair(name, value));
            }
        }


        NameValuePair[] nvps = new NameValuePair[data.size()];
        for (int i = 0; i<data.size(); i ++){
            nvps[i] = data.get(i);
        }
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(nvps);

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        InputStream soapResponseData = postMethod.getResponseBodyAsStream();
        return getString(soapResponseData);
    }


    public static String sendPost(String url, Map params) throws IOException {

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        List<NameValuePair> data = new ArrayList<NameValuePair>();


        for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String value = String.valueOf(params.get(name));
            data.add(new NameValuePair(name, value));
        }

        NameValuePair[] nvps = new NameValuePair[data.size()];
        for (int i = 0; i<data.size(); i ++){
            nvps[i] = data.get(i);
        }
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(nvps);

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        InputStream soapResponseData = postMethod.getResponseBodyAsStream();
        return getString(soapResponseData);
    }

    public static String sendGet(String url) throws IOException {

        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Apache-HttpClient/4.1.1 (java 1.5)");
        httpClient.executeMethod(getMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        String soapResponseData = getMethod.getResponseBodyAsString();
        soapResponseData  = new String(soapResponseData.trim().getBytes("utf-8"), "utf-8");
        return soapResponseData;
    }


    public static String sendGetJson(String url) throws IOException {

        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Content-Type", "application/json; charset=utf-8");

        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Apache-HttpClient/4.1.1 (java 1.5)");
        httpClient.executeMethod(getMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        String soapResponseData = getMethod.getResponseBodyAsString();
        soapResponseData  = new String(soapResponseData.trim().getBytes("ISO-8859-1"), "utf-8");
        return soapResponseData;
    }

    public static String getString(InputStream inputStream)throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        while((str = reader.readLine())!=null){
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }
}