package com.camelot.kuka.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Description 客户端文件上传至服务器
 * @Author xiongyong
 * @Date 2019/12/30
 */
@Slf4j
public class FileUploadUtil {

    /**
     * 默认文件上传url
     */
    public static final String DEFAULT_UPLOAD_URL = "http://a.fehorizon.com/HXJFwpsTEST/api-f/fast/files/upload";

    /**
     * 文件上传公共方法
     * @param fileSource: 文件路径
     * @param uploadUrl: 上传url
     * @return
     */
    public static String uploadFile(String fileSource, String uploadUrl){
        String uploadPath = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse =null;
        try {
            File file = new File(fileSource);
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给以下服务器地址
            HttpPost httpPost = new HttpPost(uploadUrl);
            // 把文件转为文件流对象FileBody
            FileBody fileBody = new FileBody(file);
            // 模拟表单提交文件
            HttpEntity entity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
            httpPost.setEntity(entity);
            //发起请求，返回响应
            httpResponse = httpClient.execute(httpPost);
            HttpEntity respEntity = httpResponse.getEntity();
            String resultStr = EntityUtils.toString(respEntity);
            log.info("{} 文件上传返回参数 {} ： "+ resultStr);
            uploadPath = resultStr;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
                try {
                    if(httpResponse !=null) {
                        httpResponse.close();
                    }
                    if (httpClient!=null){
                        httpClient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return uploadPath;
    }

//    public static void main(String[] args) {
//
//        String path = uploadFile("d:\\Users\\xiongyong\\Desktop\\FileUploadUtil.java", DEFAULT_UPLOAD_URL);
//        System.out.println(path);
//
//    }



}
