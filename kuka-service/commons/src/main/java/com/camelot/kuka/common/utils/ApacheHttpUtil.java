package com.camelot.kuka.common.utils;

import com.alibaba.fastjson.JSON;
import com.fehorizon.commonService.common.constants.SysConstant;
import com.fehorizon.commonService.model.common.HttpResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Description: [apacheHttp工具类]</p>
 * Created on 2019/9/25
 * @author <a href="mailto: hexiaobo@camelotchina.com">贺小波</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class ApacheHttpUtil {

    /**
     * TLSv1协议
     */
    private static final String TCP_TLSv1 = "TLSv1";

    /**
     * 编码字符集
     */
    private static final String defaultCharset = "utf-8";

    /**
     * socketTimeout时间
     */
    private static final int socketTimeout = 60000;

    /**
     * connectTimeout时间
     */
    private static final int connectTimeout = 60000;

    /**
     * html隔离符
     */
    private static final Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);

    /**
     * 连接符
     */
    private static final String CONNECTOR = "?";

    /**
     * <p>Description:[获取默认Client]</p>
     * Created on 2019/9/25
     * @param
     * @return org.apache.http.impl.client.CloseableHttpClient
     * @author 贺小波
     */
    public static CloseableHttpClient getDefaultClient() {
        return HttpClients.createDefault();
    }

    /**
     * <p>Description:[获取代理Client]</p>
     * Created on 2019/9/25
     * @param proxyHost 代理服务器IP
     * @param proxyPort 代理服务器端口
     * @param proxyName 代理用户
     * @param proxyPwd  代理密码
     * @return org.apache.http.impl.client.CloseableHttpClient
     * @author 贺小波
     */
    public static CloseableHttpClient getProxyClient(String proxyHost, int proxyPort, String proxyName, String proxyPwd) {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(proxyName, proxyPwd));
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        return httpClient;
    }

    /**
     * <p>Description:[获取TLSv1协议Client-若出现异常则为默认Client]</p>
     * Created on 2019/9/25
     * @param
     * @return org.apache.http.impl.client.CloseableHttpClient
     * @author 贺小波
     */
    public static CloseableHttpClient getTLSv1Client() {
        try {
            SSLConnectionSocketFactory sslsf = getTLSv1();
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return client;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    /**
     * <p>Description:[获取代理TLSv1协议Client-若出现异常则为默认Client]</p>
     * Created on 2019/9/25
     * @param proxyHost 代理服务器IP
     * @param proxyPort 代理服务器端口
     * @param proxyName 代理用户
     * @param proxyPwd  代理密码
     * @return org.apache.http.impl.client.CloseableHttpClient
     * @author 贺小波
     */
    public static CloseableHttpClient getProxyTLSv1Client(String proxyHost, int proxyPort, String proxyName, String proxyPwd) {
        try {
            SSLConnectionSocketFactory sslsf = getTLSv1();
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(proxyName, proxyPwd));
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCredentialsProvider(provider).build();
            return client;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    /**
     * <p>Description:[获取默认Config]</p>
     * Created on 2019/9/25
     * @param
     * @return org.apache.http.client.config.RequestConfig
     * @author 贺小波
     */
    public static RequestConfig getDefaultConfig() {
        return RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * <p>Description:[获取代理Config]</p>
     * Created on 2019/9/25
     * @param proxyHost 代理服务器IP
     * @param proxyPort 代理服务器端口
     * @return org.apache.http.client.config.RequestConfig
     * @author 贺小波
     */
    public static RequestConfig getProxyConfig(String proxyHost, int proxyPort) {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        return RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setProxy(proxy).build();
    }

    /**
     * <p>Description:[GET请求路径参数处理]</p>
     * Created on 2019/9/25
     * @param url    请求路径
     * @param params 参数
     * @return java.lang.String
     * @author 贺小波
     */
    public static String getHttpUrl(String url, Map<String, Object> params) {
        if (params == null || params.size() == SysConstant.ZERO) {
            return url;
        }
        int flag = url.indexOf(CONNECTOR);
        StringBuilder buf = new StringBuilder();
        buf.append(url);
        if (flag < SysConstant.ZERO) {
            buf.append("?");
        }
        for (String key : params.keySet()) {
            buf.append("&").append(key).append("=").append(params.get(key));
        }
        return buf.toString();
    }

    /**
     * <p>Description:[get请求-均使用默认Client与默认Config]</p>
     * Created on 2019/9/25
     * @param url    请求地址
     * @param params 参数
     * @return java.lang.String
     * @author 贺小波
     */
    public static String getHttp(String url, Map<String, Object> params) {
        if (StringUtils.isBlank(url)) {
            return sysError("地址为空");
        }
        try {
            CloseableHttpClient client = getDefaultClient();
            RequestConfig config = getDefaultConfig();
            String resp = httpGet(url, params, client, config, null);
            return resp;
        } catch (IOException e) {
            return sysError(e);
        }
    }

    /**
     * <p>Description:[GET请求-使用指定Client与指定Config与指定heads参数面板]</p>
     * Created on 2019/9/25
     * @param url         请求地址
     * @param params      参数
     * @param client      client服务
     * @param config      config服务
     * @param headsParams 头信息
     * @return java.lang.String
     * @author 贺小波
     */
    public static String getHttp(String url, Map<String, Object> params, CloseableHttpClient client, RequestConfig config, Map<String, String> headsParams) {
        if (StringUtils.isBlank(url)) {
            return sysError("地址为空");
        }
        try {
            CloseableHttpClient httpClient = (client == null ? getDefaultClient() : client);
            RequestConfig requestConfig = (config == null ? getDefaultConfig() : config);
            String resp = httpGet(url, params, httpClient, requestConfig, headsParams);
            return resp;
        } catch (IOException e) {
            return sysError(e);
        }
    }

    /**
     * <p>Description:[POST请求(JSON数据)-均使用默认Client与默认Config]</p>
     * Created on 2019/9/25
     * @param url    请求地址
     * @param params 参数
     * @return java.lang.String
     * @author 贺小波
     */
    public static String postHttpJson(String url, Map<String, Object> params) {
        if (StringUtils.isBlank(url)) {
            return sysError("地址为空");
        }
        try {
            CloseableHttpClient client = getDefaultClient();
            RequestConfig config = getDefaultConfig();
            String resp = httpJsonPost(url, params, client, config, null);
            return resp;
        } catch (IOException e) {
            return sysError(e);
        }
    }

    /**
     * <p>Description:[POST请求(JSON数据)-使用指定Client与指定Config与指定heads参数面板]</p>
     * Created on 2019/9/25
     * @param url         请求地址
     * @param params      参数
     * @param client      client服务
     * @param config      config服务
     * @param headsParams 头信息
     * @return java.lang.String
     * @author 贺小波
     */
    public static String postHttpJson(String url, Map<String, Object> params, CloseableHttpClient client, RequestConfig config, Map<String, String> headsParams) {
        if (StringUtils.isBlank(url)) {
            return sysError("地址为空");
        }
        try {
            CloseableHttpClient httpClient = (client == null ? getDefaultClient() : client);
            RequestConfig requestConfig = (config == null ? getDefaultConfig() : config);
            String resp = httpJsonPost(url, params, httpClient, requestConfig, headsParams);
            return resp;
        } catch (IOException e) {
            return sysError(e);
        }
    }

    /**
     * <p>Description:[POST请求(Form数据-数据已URLEncoder)-均使用默认Client与默认Config]</p>
     * Created on 2019/9/26
     * @param url    请求地址
     * @param params 参数
     * @return java.lang.String
     * @author 贺小波
     */
    public static String postHttpForm(String url, Map<String, Object> params) {
        if (StringUtils.isBlank(url)) {
            return sysError("地址为空");
        }
        try {
            CloseableHttpClient httpClient = getDefaultClient();
            RequestConfig requestConfig = getDefaultConfig();
            String resp = postFormHttp(url, params, httpClient, requestConfig, null);
            return resp;
        } catch (IOException e) {
            return sysError(e);
        }
    }

    /**
     * <p>Description:[POST请求(Form数据-数据已URLEncoder)-使用指定Client与指定Config与指定heads参数面板]</p>
     * Created on 2019/9/26
     * @param url         请求地址
     * @param params      参数
     * @param client      client服务
     * @param config      config服务
     * @param headsParams 头信息
     * @return java.lang.String
     * @author 贺小波
     */
    public static String postHttpForm(String url, Map<String, Object> params, CloseableHttpClient client, RequestConfig config, Map<String, String> headsParams) {
        if (StringUtils.isBlank(url)) {
            return sysError("地址为空");
        }
        try {
            CloseableHttpClient httpClient = (client == null ? getDefaultClient() : client);
            RequestConfig requestConfig = (config == null ? getDefaultConfig() : config);
            String resp = postFormHttp(url, params, httpClient, requestConfig, headsParams);
            return resp;
        } catch (IOException e) {
            return sysError(e);
        }
    }

    /**
     * <p>Description:[删除目标字符串中的http标记符]</p>
     * Created on 2019/9/25
     * @param param 目标字符串
     * @return java.lang.String
     * @author 贺小波
     */
    public static String delHtml(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        Matcher matcher = pattern.matcher(param);
        String name = matcher.replaceAll("");
        return name;
    }

    /**
     * <p>Description:[HTTP-POST请求(Form数据格式)]</p>
     * Created on 2019/9/26
     * @param url         请求地址
     * @param params      参数
     * @param client      client服务
     * @param config      config配置
     * @param headsParams 头信息
     * @return java.lang.String
     * @author 贺小波
     */
    private static String postFormHttp(String url, Map<String, Object> params, CloseableHttpClient client, RequestConfig config, Map<String, String> headsParams) throws IOException {
        HttpPost request = new HttpPost(url);
        UrlEncodedFormEntity formParam = new UrlEncodedFormEntity(convertParams(params), StandardCharsets.UTF_8);
        request.setEntity(formParam);
        request.setConfig(config);
        convertHeadsParams(request, headsParams);
        CloseableHttpResponse response = client.execute(request);
        String result = getHttpResp(response);
        return result;
    }

    /**
     * <p>Description:[HTTP-POST请求(JSON数据格式)]</p>
     * Created on 2019/9/25
     * @param url         请求地址
     * @param params      参数
     * @param client      client服务
     * @param config      config配置
     * @param headsParams 头信息
     * @return java.lang.String
     * @author 贺小波
     */
    private static String httpJsonPost(String url, Map<String, Object> params, CloseableHttpClient client, RequestConfig config, Map<String, String> headsParams) throws IOException {
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(params), StandardCharsets.UTF_8);
        HttpPost request = new HttpPost(url);
        request.setConfig(config);
        convertHeadsParams(request, headsParams);
        request.setEntity(stringEntity);
        CloseableHttpResponse response = client.execute(request);
        String result = getHttpResp(response);
        return result;
    }

    /**
     * <p>Description:[HTTP-GET请求]</p>
     * Created on 2019/9/25
     * @param url         请求地址
     * @param params      参数
     * @param client      client服务
     * @param config      config配置
     * @param headsParams 头信息
     * @return java.lang.String
     * @author 贺小波
     */
    private static String httpGet(String url, Map<String, Object> params, CloseableHttpClient client, RequestConfig config, Map<String, String> headsParams) throws IOException {
        HttpGet request = new HttpGet(getHttpUrl(url, params));
        request.setConfig(config);
        convertHeadsParams(request, headsParams);
        CloseableHttpResponse response = client.execute(request);
        String result = getHttpResp(response);
        return result;
    }

    /**
     * <p>Description:[参数转换]</p>
     * Created on 2019/9/26
     * @param params 参数
     * @return java.util.List<org.apache.http.NameValuePair>
     * @author 贺小波
     */
    private static List<NameValuePair> convertParams(Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        if (params == null || params.size() == SysConstant.ZERO) {
            return nvps;
        }
        for (String key : params.keySet()) {
            Object obj = params.get(key);
            BasicNameValuePair bnvp = new BasicNameValuePair(key, (obj == null ? null : obj.toString()));
            nvps.add(bnvp);
        }
        return nvps;
    }

    /**
     * <p>Description:[设置头信息]</p>
     * Created on 2019/9/25
     * @param httpGet     get头信息
     * @param headsParams 参数
     * @return void
     * @author 贺小波
     */
    private static void convertHeadsParams(HttpGet httpGet, Map<String, String> headsParams) {
        if (httpGet == null || headsParams == null || headsParams.size() == SysConstant.ZERO) {
            return;
        }
        for (String key : headsParams.keySet()) {
            httpGet.setHeader(key, headsParams.get(key));
        }
    }

    /**
     * <p>Description:[设置头信息]</p>
     * Created on 2019/9/25
     * @param httpPost    post头信息
     * @param headsParams 参数
     * @return void
     * @author 贺小波
     */
    private static void convertHeadsParams(HttpPost httpPost, Map<String, String> headsParams) {
        if (httpPost == null || headsParams == null || headsParams.size() == SysConstant.ZERO) {
            return;
        }
        for (String key : headsParams.keySet()) {
            httpPost.setHeader(key, headsParams.get(key));
        }
    }

    /**
     * <p>Description:[http返回]</p>
     * Created on 2019/9/25
     * @param response 处理对象
     * @return java.lang.String
     * @author 贺小波
     */
    private static String getHttpResp(CloseableHttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String resp = EntityUtils.toString(entity, defaultCharset);
        HttpResp httpResp = new HttpResp();
        httpResp.setHttpCode(String.valueOf(statusCode));
        httpResp.setDate(resp);
        return JSON.toJSONString(httpResp);
    }

    /**
     * <p>Description:[校验错误]</p>
     * Created on 2019/9/25
     * @param msg 错误信息
     * @return java.lang.String
     * @author 贺小波
     */
    private static String sysError(String msg) {
        HttpResp resp = new HttpResp();
        resp.setHttpCode("-1");
        resp.setErrorMsg(msg);
        return JSON.toJSONString(resp);
    }

    /**
     * <p>Description:[系统错误]</p>
     * Created on 2019/9/25
     * @param e 异常信息
     * @return java.lang.String
     * @author 贺小波
     */
    private static String sysError(Exception e) {
        HttpResp resp = new HttpResp();
        resp.setHttpCode("-1");
        resp.setErrorMsg("请求发生系统错误");
        resp.setDate(ExceptionUtil.getExceptionToString(e));
        return JSON.toJSONString(resp);
    }

    /**
     * <p>Description:[获取协议面板]</p>
     * Created on 2019/9/25
     * @param
     * @return org.apache.http.conn.ssl.SSLConnectionSocketFactory
     * @author 贺小波
     */
    private static SSLConnectionSocketFactory getTLSv1() throws NoSuchAlgorithmException, KeyManagementException {
        X509TrustManager trustManager = getX509TrustManager();
        SSLContext sc = SSLContext.getInstance(TCP_TLSv1);
        sc.init(null, new TrustManager[]{trustManager}, null);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc);
        return sslsf;
    }

    /**
     * <p>Description:[跳过安全证书]</p>
     * Created on 2019/9/25
     * @param
     * @return javax.net.ssl.X509TrustManager
     * @author 贺小波
     */
    private static X509TrustManager getX509TrustManager() {
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        return trustManager;
    }

}
