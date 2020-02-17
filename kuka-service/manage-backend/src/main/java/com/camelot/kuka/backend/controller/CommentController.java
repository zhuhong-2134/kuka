package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.backend.service.CommentService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.comment.req.CommentPageReq;
import com.camelot.kuka.model.backend.comment.req.CommentReq;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.comment.CommentPageEnum;
import com.camelot.kuka.model.enums.comment.CommentStatusEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [评论控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "评论API", tags = { "评论接口" })
public class CommentController extends BaseController {

    @Resource
    private CommentService commentService;

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/comment/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("statusEnum", EnumVal.getEnumList(CommentStatusEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(CommentPageEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        return page;
    }

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/comment/pageList")
    public PageResult<List<CommentResp>> pageList(CommentPageReq req){
        try {
            // 开启分页
            startPage();
            List<Comment> order = commentService.pageList(req);
            // 返回分页
            PageResult<List<CommentResp>> page = getPage(order, CommentResp.class);
            page.putEnumVal("statusEnum", EnumVal.getEnumList(CommentStatusEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(CommentPageEnum.class));
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 评论模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/comment/del")
    public Result delSupplier(CommonReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return commentService.delSupplier(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 评论模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "delSupplier", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/comment/queryById")
    public Result<CommentResp> queryById(CommonReq req) {
        try {
            return commentService.queryById(req);
        } catch (Exception e) {
            log.error("\n 评论模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[审核]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/comment/toExamine")
    public Result toExamine(CommentReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return commentService.toExamine(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 评论模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "toExamine", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}
