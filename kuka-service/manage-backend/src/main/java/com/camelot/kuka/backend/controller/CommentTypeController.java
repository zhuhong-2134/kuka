package com.camelot.kuka.backend.controller;


import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.service.CommentTypeService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.comment.resp.CommentTypeResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [评论分类控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "评论分类API", tags = { "评论分类控制层接口" })
public class CommentTypeController extends BaseController {

    @Resource
    private CommentTypeService commentTypeService;

    /***
     * <p>Description:[获取列表]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/commentType/list")
    public Result<List<CommentTypeResp>> queryList(){
        try {
            return commentTypeService.queryList();
        } catch (Exception e) {
            log.error("\n 评论分类模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryList", JSON.toJSONString(null), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/commentType/add")
    public Result addCommentType(CommentTypeResp req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            if (null == loginUserName) {
                return Result.error("用户未登录");
            }
            return commentTypeService.addCommentType(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 评论分类模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addCommentType", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }


    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/commentType/delete")
    public Result deleteCommentType(CommonReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            if (null == loginUserName) {
                return Result.error("用户未登录");
            }
            return commentTypeService.deleteCommentType(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 评论分类模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "deleteCommentType", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}
