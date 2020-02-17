package com.camelot.kuka.backend.controller;

import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.backend.service.MailMouldService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.mailmould.resp.MailMouldResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.mail.Mail;
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

    /**
     * 查询列表
     * @param commonReq
     * @return
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/mailMould/findList")
    public Result<List<MailMouldResp>> findList(CommonReq commonReq){
        List<MailMould> allList = mailMouldService.findAllList(commonReq.getId().intValue());
        List<MailMouldResp> mailMouldResps = BeanUtil.copyList(allList, MailMouldResp.class);
        return Result.success(mailMouldResps);
    }

    /**
     * 新增
     * @param mailMouldResp
     * @return
     */
    @ApiOperation(value = "新增")
    @PostMapping("/mailMould/add")
    public Result<Integer> add(MailMouldResp mailMouldResp){
        MailMould mailMould = BeanUtil.copyBean(mailMouldResp, MailMould.class);
        Integer insert = mailMouldService.insert(mailMould);
        return Result.success(insert);
    }

    /**
     * 修改
     * @param mailMouldResp
     * @return
     */
    @ApiOperation(value = "修改")
    @PostMapping("/mailMould/update")
    public Result<Integer> update(MailMouldResp mailMouldResp){
        MailMould mailMould = BeanUtil.copyBean(mailMouldResp, MailMould.class);
        Integer insert = mailMouldService.update(mailMould);
        return Result.success(insert);
    }

    /**
     * 删除
     * @param commonReq
     * @return
     */
    @ApiOperation(value = "删除")
    @PostMapping("/mailMould/delete")
    public Result<Integer> delete(CommonReq commonReq){
        Integer delete = mailMouldService.delete(commonReq.getId().intValue());
        return Result.success(delete);
    }
}
