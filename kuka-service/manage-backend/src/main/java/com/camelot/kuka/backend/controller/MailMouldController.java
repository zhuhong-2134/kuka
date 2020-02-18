package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.backend.service.MailMouldService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldPageReq;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldReq;
import com.camelot.kuka.model.backend.mailmould.resp.MailMouldResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.mailmould.MailTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@Api(value = "消息模板", tags = { "消息模板接口" })
public class MailMouldController extends BaseController {

    @Resource
    private MailMouldService mailMouldService;

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/mailMould/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("typeEnum", EnumVal.getEnumList(MailTypeEnum.class));
        return page;
    }

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/mailMould/pageList")
    public PageResult<List<MailMouldResp>> pageList(MailMouldPageReq req){
        try {
            // 开启分页
            startPage();
            // 返回分页
            List<MailMould> mailMould = mailMouldService.pageList(req);
            PageResult<List<MailMouldResp>> page = getPage(mailMould, MailMouldResp.class);
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            page.putEnumVal("typeEnum", EnumVal.getEnumList(MailTypeEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 邮件模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }


    /**
     * 新增
     * @param req
     * @return
     */
    @ApiOperation(value = "新增")
    @PostMapping("/mailMould/add")
    public Result addMailMould(MailMouldReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return mailMouldService.addMailMould(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 邮件模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addMailMould", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取单条数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/mailMould/queryById")
    public Result<MailMouldResp> queryById(CommonReq req){
        try {
            return mailMouldService.queryById(req);
        } catch (Exception e) {
            log.error("\n 邮件模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /**
     * 修改
     * @param req
     * @return
     */
    @ApiOperation(value = "修改")
    @PostMapping("/mailMould/update")
    public Result updateMailMould(MailMouldReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return mailMouldService.updateMailMould(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 邮件模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateMailMould", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }


    /**
     * 修改
     * @param req
     * @return
     */
    @ApiOperation(value = "删除")
    @PostMapping("/mailMould/del")
    public Result delMailMould(MailMouldReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return mailMouldService.delMailMould(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 邮件模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "delMailMould", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

}
