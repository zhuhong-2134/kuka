package com.camelot.kuka.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.DateUtils;
import com.camelot.kuka.common.utils.ServletUtils;
import com.camelot.kuka.model.common.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: [上传信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "上传API", tags = { "上传接口" })
public class UploadController extends BaseController {

    // 设置上传文件夹
    File uploadPath = null;

    // IP
    @Value("${files.ip}")
    private String fileIp;

    // 单文件上传
    @PostMapping("/file/upload")
    public Result<JSONObject> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws Exception{
        // 定义返回客户端json对象
        JSONObject returnData = new JSONObject();
        // 定义处理流对象
        BufferedOutputStream out = null;

        // 获取上传对象
        // 获取上传文件的类型 1:图片 2:其他
        String upload_type = ServletUtils.getParameter("upload_type");

        //判断上传文件类型并设置前置路径
        File uploadPath = null;
        // String basePath = "D:\\images\\";                  // win 基础文件上传路径
        String basePath = "/data/file";                  // linux 基础文件上传路径
        String yyyyMMdd = DateUtils.dateToStr(new Date(), "yyyyMMdd");
        // 图片
        String inspection = "/imgs/" + yyyyMMdd;
        //其他文件
        String maintenance = "/files/" + yyyyMMdd;

        switch (upload_type){
            case "1":
                uploadPath = new File(basePath + inspection);
                break;
            case "2":
                uploadPath = new File(basePath + maintenance);
                break;
            default:
                uploadPath = new File(basePath);
        }
        //判断服务器上传文件夹是否存在
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }
        //判断上传的文件是否为空
        if (file!=null) {
            //获取上传文件后缀
            String houzhui = file.getOriginalFilename().split("\\.")[1];
            //拼接上传文件保存路径(当前用户id+设备id+时间戳.后缀名)
            File fil = new File(uploadPath+"/" + new Date().getTime() + "." + houzhui);
            try {
                //将上传文件保存到服务器上传文件夹目录下
                out = new BufferedOutputStream(new FileOutputStream(fil));
                out.write(file.getBytes());
                out.flush();
                out.close();
                //返回上传文件的访问路径   getAbsolutePath()返回文件上传的绝对路径
                if (upload_type.equals("1")) {
                    returnData.put("message", fileIp + basePath + inspection + "/" + fil.getName());
                }
                if (upload_type.equals("2")) {
                    returnData.put("message", fileIp + basePath + maintenance + "/" + fil.getName());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                returnData.put("message", "文件上传失败:" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                returnData.put("message", "文件上传失败:" + e.getMessage());
            }finally {
                //关闭处理流
                if(out!=null){out.close();}
            }
        } else {
            returnData.put("message", "文件上传失败,文件为空");
        }
        return Result.success(returnData);
    }

    // 多文件上传
    @PostMapping("/file/batchUpload")
    public Result<JSONObject> handleFileUpload(HttpServletRequest request) throws Exception{
        //定义返回客户端json对象
        JSONObject returnData = new JSONObject();
        //定义处理流对象,处理文件上传
        BufferedOutputStream stream = null;
        //定义map存储返回结果集
        Map<String,String> returnfileMap = new HashMap<String, String>();

        //获取前端上传的文件列表
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;

        // 获取上传对象
        // 获取上传文件的类型 1:图片 2:其他
        String upload_type = ServletUtils.getParameter("upload_type");

        //判断上传文件类型并设置前置路径
        File uploadPath = null;
        // String basePath = "D:\\images\\";                  // win 基础文件上传路径
        String basePath = "/data/file";                  // linux 基础文件上传路径
        String yyyyMMdd = DateUtils.dateToStr(new Date(), "yyyyMMdd");
        String inspection = "/imgs/" + yyyyMMdd;              // 图片
        String maintenance = "/files/ + yyyyMMdd";            // 文件

        switch (upload_type){
            case "1":
                uploadPath = new File(basePath+inspection);
                break;
            case "2":
                uploadPath = new File(basePath+maintenance);
                break;
            default:
                uploadPath = new File(basePath);
        }
        // 判断服务器上传文件夹是否存在
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        //遍历客户端上传文件列表
        for (int i = 0; i < files.size(); ++i) {
            //获取到每个文件
            file = files.get(i);
            try {
                //获取上传文件后缀
                String houzhui = file.getOriginalFilename().split("\\.")[1];
                //拼接上传文件保存在服务器的路径(当前用户id+设备id+时间戳.后缀名)
                File fil = new File(uploadPath+"/" + new Date().getTime() + "." + houzhui);
                //将上传文件保存到服务器上传文件夹目录下
                byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(new FileOutputStream(fil));
                stream.write(bytes);
                stream.close();
                //每成功上传一个文件,将上传文件名作为key,服务器保存路径作为value存入returnfileMap中
                switch (upload_type){
                    case "1":
                        returnfileMap.put(file.getOriginalFilename(), fileIp + basePath + inspection + "/" +  fil.getName());
                        break;
                    case "2":
                        returnfileMap.put(file.getOriginalFilename(), fileIp + basePath + maintenance + "/" + fil.getName());
                        break;
                }
            } catch (Exception e) {
                stream = null;
                //保存上传失败的文件信息,将上传文件名作为key,value值为"fail",存入returnfileMap中
                returnfileMap.put(file.getOriginalFilename(), "fail");
            } finally {
                //关闭处理流
                if(stream!=null){stream.close();}
            }
        }
        // 返回returnfileMap集合到客户端
        returnData.put("message",returnfileMap);
        return Result.success(returnData);
    }
}
