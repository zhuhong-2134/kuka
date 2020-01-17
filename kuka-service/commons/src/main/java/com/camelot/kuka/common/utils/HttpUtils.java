package com.camelot.kuka.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.Charset;

@Slf4j
public class HttpUtils {

    /**
     * Description: [编码]
     **/
    private static final String DEFAULT_CHARSET = "UTF-8";


//    public static void main(String[] args) {
//        String s = HttpUtils.get("https://www.baidu.com", new HttpProxy(true, "shproxy.fehorizon.com", 8080, "shebei6", "Sinochem1"));
//        System.out.println(s);
//    }

    /**
     * Description: [get请求]
     * @date: url
     * @date: proxy1 代理对象可为空
     * Created on 2019年09月11日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static String get(String url, HttpProxy proxy1){
        final StopWatch sw = new StopWatch();
        sw.start("http-get:" + url);
        // 设置代理HttpHost
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse resp = null;
        String resultString = "";
        try {
            RequestConfig config = null;
            // 是否开启代理
            if (null != proxy1 && proxy1.getProxy()) {
                // 设置认证
                CredentialsProvider provider = new BasicCredentialsProvider();
                HttpHost proxy = new HttpHost(proxy1.getProxyUrl(), proxy1.getProxyPort(), Proxy.Type.HTTP.toString());
                provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(proxy1.getUserName(), proxy1.getPassword()));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
                config = RequestConfig.custom().setProxy(proxy).build();
            } else {
                httpClient = HttpClients.createDefault();
                config = RequestConfig.custom().build();
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            resp = httpClient.execute(httpGet);
            String retStr = EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            log.info("\nhttps-get请求url:{}, \n请求结果:{}", url, retStr);
            // 判断返回状态是否为200
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = retStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resp != null) {
                    resp.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sw.stop();
            log.info(sw.prettyPrint());
        }
        return resultString;
    }


    /**
     * Description: [post请求]
     * @date: url
     * @date: proxy1 代理对象可为空
     * @date: param 参数
     * Created on 2019年09月11日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static String post(String url, HttpProxy proxy1, String jsonParam){
        final StopWatch sw = new StopWatch();
        sw.start("http-post:" + url);
        // 设置代理HttpHost
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse resp = null;
        String resultString = "";
        try {
            RequestConfig config = null;
            // 是否开启代理
            if (null != proxy1 && proxy1.getProxy()) {
                // 设置认证
                CredentialsProvider provider = new BasicCredentialsProvider();
                HttpHost proxy = new HttpHost(proxy1.getProxyUrl(), proxy1.getProxyPort(), Proxy.Type.HTTP.toString());
                provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(proxy1.getUserName(), proxy1.getPassword()));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
                config = RequestConfig.custom().setProxy(proxy).build();
            } else {
                httpClient = HttpClients.createDefault();
                config = RequestConfig.custom().build();
            }
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            // 创建参数列表
            if (StringUtils.isNotBlank(jsonParam)) {
                httpPost.setEntity(new StringEntity(jsonParam, Charset.forName("UTF-8")));
            }
            httpPost.setConfig(config);
            resp = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            String resStr = EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            log.info("\nhttps-get请求url:{}, \n请求结果:{}", url, resStr);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = resStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resp != null) {
                    resp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sw.stop();
            log.info(sw.prettyPrint());
        }
        return resultString;
    }
}
