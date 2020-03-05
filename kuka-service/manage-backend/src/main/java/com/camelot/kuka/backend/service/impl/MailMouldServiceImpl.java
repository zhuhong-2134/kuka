package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.MailMouldDao;
import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.backend.service.MailMouldService;
import com.camelot.kuka.backend.service.SendMailService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldPageReq;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldReq;
import com.camelot.kuka.model.backend.mailmould.resp.MailMouldResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.MailReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>Description: [消息模板]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("mailMouldService")
public class MailMouldServiceImpl implements MailMouldService {

    @Resource
    private MailMouldDao mailMouldDao;

    @Resource
    private CodeGenerateUtil codeGenerateUtil;


    @Override
    public List<MailMould> pageList(MailMouldPageReq req) {
        // 未删除
        req.setDelState(DeleteEnum.NO);
        return mailMouldDao.pageList(req);
    }

    @Override
    public Result addMailMould(MailMouldReq req, String loginUserName) {
        if (null == req || StringUtils.isBlank(req.getMouldName())) {
            return Result.error("模板名称不能为空");
        }
        if (null == req.getType()) {
            return Result.error("发送方式不能为空");
        }
        if (StringUtils.isBlank(req.getMessage())) {
            return Result.error("消息内容不能为空");
        }
        MailMould mailMould = BeanUtil.copyBean(req, MailMould.class);
        Date date = new Date();
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_MAIL_MOULD);
        mailMould.setId(id);
        mailMould.setCreateBy(loginUserName);
        mailMould.setCreateTime(date);
        mailMould.setDelState(DeleteEnum.NO);
        int add = mailMouldDao.addBatch(Arrays.asList(mailMould));
        if (add == 0) {
            return Result.error("新增失败, 联系管理员");
        }
        return Result.success(id);
    }

    @Override
    public Result<MailMouldResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        MailMould mailMould = new MailMould();
        mailMould.setId(req.getId());
        mailMould.setDelState(DeleteEnum.NO);
        MailMould info = mailMouldDao.findInfo(mailMould);
        if (null == info) {
            return Result.error("获取失败, 请刷新后重试");
        }
        return Result.success(BeanUtil.copyBean(info, MailMouldResp.class));
    }

    @Override
    public Result updateMailMould(MailMouldReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        MailMould mailMould = BeanUtil.copyBean(req, MailMould.class);
        mailMould.setUpdateBy(loginUserName);
        mailMould.setUpdateTime(new Date());
        int update = mailMouldDao.update(mailMould);
        if (update == 0) {
            return Result.error("修改失败, 联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result delMailMould(MailMouldReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        MailMould mailMould = BeanUtil.copyBean(req, MailMould.class);
        mailMould.setUpdateBy(loginUserName);
        mailMould.setUpdateTime(new Date());
        mailMould.setDelState(DeleteEnum.YES);
        int update = mailMouldDao.update(mailMould);
        if (update == 0) {
            return Result.error("删除失败, 联系管理员");
        }
        return Result.success();
    }

    @Resource
    private SendMailService sendMailService;

    @Override
    public Result sendMail(MailReq req) {
        if (null == req || StringUtils.isBlank(req.getMail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(req.getTitle())) {
            return Result.error("标题不能为空");
        }
        if (StringUtils.isBlank(req.getMessage())) {
            return Result.error("消息不能为空");
        }
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                boolean status = sendMailService.sendMail(req.getMail(), req.getTitle(), req.getMessage());
            } catch (Exception e) {
            }
        });
        return Result.success();
    }

}
