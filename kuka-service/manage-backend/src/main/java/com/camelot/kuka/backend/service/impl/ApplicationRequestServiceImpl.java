package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationRequestDao;
import com.camelot.kuka.backend.feign.user.UserClient;
import com.camelot.kuka.backend.model.ApplicationRequest;
import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.backend.service.ApplicationRequestService;
import com.camelot.kuka.backend.service.ApplicationService;
import com.camelot.kuka.backend.service.MailMouldService;
import com.camelot.kuka.backend.service.MessageService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.backend.applicationrequest.req.AppRequestPageReq;
import com.camelot.kuka.model.backend.applicationrequest.req.ApplicationRequestReq;
import com.camelot.kuka.model.backend.applicationrequest.resp.ApplicationRequestResp;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldPageReq;
import com.camelot.kuka.model.backend.message.req.MessageReq;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.MailReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.CommunicateEnum;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.backend.JumpStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageTypeEnum;
import com.camelot.kuka.model.enums.mailmould.MailTypeEnum;
import com.camelot.kuka.model.enums.user.WhetherEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.resp.UserResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [应用商请求业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("applicationRequestService")
public class ApplicationRequestServiceImpl implements ApplicationRequestService {

    @Resource
    private ApplicationRequestDao applicationRequestDao;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;
    @Resource
    private MailMouldService mailMouldService;
    @Resource
    private UserClient userClient;
    @Resource
    private MessageService messageService;

    @Override
    public List<ApplicationRequest> queryList(AppRequestPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<ApplicationRequest> applicationRequests = applicationRequestDao.pageList(req);
        return applicationRequests;
    }

    @Override
    public Result<ApplicationRequestResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        ApplicationRequest query = new ApplicationRequest();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        ApplicationRequest applicationRequest = applicationRequestDao.queryInfo(query);
        if (null == applicationRequest) {
            return Result.error("获取失败, 刷新后重试");
        }
        return Result.success(BeanUtil.copyBean(applicationRequest, ApplicationRequestResp.class));
    }

    @Override
    public Result updateStatus(ApplicationRequestReq req, String loginUserName) {
        if (null == req || req.getId() == null) {
            return Result.error("ID不能为空");
        }
        if (null == req.getStatus()) {
            return Result.error("状态不能为空");
        }
        ApplicationRequest applicationRequest = BeanUtil.copyBean(req, ApplicationRequest.class);
        applicationRequest.setUpdateBy(loginUserName);
        applicationRequest.setUpdateTime(new Date());
        int update = applicationRequestDao.update(applicationRequest);
        if (0 == update) {
            return Result.error("修改失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result addApprequest(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req.getId()) {
            return Result.error("应用ID不能为空");
        }
        CommonReq query = new CommonReq();
        query.setId(req.getId());
        Result<QyeryUpdateResp> appRespResult = applicationService.qyeryUpdateById(query);
        if (!appRespResult.isSuccess()) {
            return Result.error("获应用失败");
        }
        QyeryUpdateResp data = appRespResult.getData();
        // 新增对象
        ApplicationRequest request = new ApplicationRequest();
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_SUPPLIER_REQUEST);
        request.setId(id);
        if (null != data.getCoverUrls() && data.getCoverUrls().length > 0) {
            request.setAppUrl(data.getCoverUrls()[0]);
        }
        request.setAppId(data.getId());
        request.setAppName(data.getAppName());
        request.setClassType(data.getClassType());
        request.setSupplierId(data.getSupplierId());

        // 集成商信息
        if (null != data.getSupplier()) {
            request.setSupplierName(data.getSupplier().getSupplierlName());
            request.setDutyName(data.getSupplier().getUserName());
            request.setDutyId(data.getSupplier().getUserId());
            request.setDutyPhone(data.getSupplier().getUserPhone());
        }

        request.setAppContactName(data.getContactBy());
        request.setAppPhone(data.getContactPhone());
        request.setMail(data.getContactMail());
        request.setAddress(null);
        request.setStatus(CommunicateEnum.NO);
        request.setUserId(loginAppUser.getId());
        request.setUserName(loginAppUser.getUserName());
        request.setUserPhotoUrl(loginAppUser.getPhotoUrl());
        request.setUserPhone(loginAppUser.getPhone());
        request.setUserMail(loginAppUser.getMail());
        request.setDelState(DeleteEnum.NO);
        request.setCreateBy(loginAppUser.getUsername());
        request.setCreateTime(new Date());
        int con = applicationRequestDao.addBatch(Arrays.asList(request));
        if (con == 0) {
            return Result.error("新增失败");
        }
        return Result.success(id);
    }

    @Override
    public Result sendMail(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req.getId()) {
            return Result.error("id不能为空");
        }
        if (null == req.getStatus()) {
            return Result.error("通过不通过状态不能为空");
        }
        // 获取请求
        ApplicationRequest query = new ApplicationRequest();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        ApplicationRequest applicationRequest = applicationRequestDao.queryInfo(query);
        if (null == applicationRequest) {
            return Result.error("获取数据失败");
        }

        // 获取应用
        CommonReq appReq = new CommonReq();
        appReq.setId(applicationRequest.getAppId());
        Result<QyeryUpdateResp> qyeryUpdateRespResult = applicationService.qyeryUpdateById(appReq);
        if (!qyeryUpdateRespResult.isSuccess() || null == qyeryUpdateRespResult.getData()) {
            return Result.error("获取应用失败");
        }

        // 获取应用魔板
        List<MailMould> mailMoulds = mailMouldService.pageList(new MailMouldPageReq());
        if (mailMoulds.isEmpty()) {
            return Result.error("模板为空");
        }

        // 获取创建者的用户信息
        Result<UserResp> userRespResult = userClient.queryByUserName(applicationRequest.getCreateBy());
        if (!userRespResult.isSuccess()) {
            return Result.error("获取发送人信息失败");
        }

        // 不通过模板
        if (req.getStatus() == WhetherEnum.NO) {
            return messageNoSend(userRespResult.getData(), qyeryUpdateRespResult.getData(), mailMoulds, loginAppUser);
        }
        // 通过模板
        if (req.getStatus() == WhetherEnum.YES) {
            return messageYesSend(userRespResult.getData(), qyeryUpdateRespResult.getData(), mailMoulds, loginAppUser);
        }
        return Result.success();
    }

    /**
     * 应用通过模板发送
     * @param app
     * @param mailMoulds
     * @return
     */
    private Result messageYesSend(UserResp user, QyeryUpdateResp app, List<MailMould> mailMoulds, LoginAppUser loginAppUser) {
        MailMould mould = new MailMould();
        for (MailMould m : mailMoulds) {
            if (m.getId().compareTo(1L) == 0) {
                mould = m;
            }
        }
        // 组装消息内容
        String message = mould.getMessage();
        // 应用名称
        message = message.replace("{1}", app.getAppName());
        if (null != app.getSupplier()) {
            // 集成商名称
            message = message.replace("{2}", app.getSupplier().getSupplierlName());
            // 总负责人
            message = message.replace("{3}", app.getSupplier().getUserName());
            // 总负责人联系方式
            message = message.replace("{4}", app.getSupplier().getUserPhone());
            // 集成商详细地址
            message = message.replace("{8}", app.getSupplier().getSupplierAddress());
        }
        // 应用联系人
        message = message.replace("{5}", null != app.getContactBy() ? app.getContactBy(): null);
        // 应用联系人联系方式
        message = message.replace("{6}", null != app.getContactPhone() ? app.getContactPhone() : "");
        // 应用邮箱
        message = message.replace("{7}", null != app.getContactMail() ? app.getContactMail() : " ");

        // 全发
        if (mould.getType() == MailTypeEnum.ALL) {


            Result resultMessage = saveMessage(user, "应用请求通过", message, app.getId(), loginAppUser, JumpStatusEnum.YES);
            if (!resultMessage.isSuccess()) {
                return Result.error("发送站内消息失败");
            }

            Result resultSend = staSendMail(user.getMail(), "kuka审核通过", message);
            if (!resultSend.isSuccess()) {
                return Result.error("发送邮件失败");
            }
        }


        // 站内消息
        if (mould.getType() == MailTypeEnum.MESSAGE) {
            Result resultMessage = saveMessage(user, "应用请求通过", message, app.getId(), loginAppUser, JumpStatusEnum.YES);
            if (!resultMessage.isSuccess()) {
                return Result.error("发送站内消息失败");
            }
        }

        // 邮件
        if (mould.getType() == MailTypeEnum.MAIL) {
            Result resultSend = staSendMail(user.getMail(), "kuka审核通过", message);
            if (!resultSend.isSuccess()) {
                return Result.error("发送邮件失败");
            }
        }
        return Result.success();
    }


    /**
     * 应用不通过模板发送
     * @param app
     * @param mailMoulds
     * @return
     */
    private Result messageNoSend(UserResp user, QyeryUpdateResp app, List<MailMould> mailMoulds, LoginAppUser loginAppUser) {
        MailMould mould = new MailMould();
        for (MailMould m : mailMoulds) {
            if (m.getId().compareTo(2L) == 0) {
                mould = m;
            }
        }

        // 组装消息内容
        String message = mould.getMessage();
        // 应用名称
        message = message.replace("{1}", app.getAppName());

        // 全发
        if (mould.getType() == MailTypeEnum.ALL) {


            Result resultMessage = saveMessage(user, "应用请求不通过", message, app.getId(), loginAppUser, JumpStatusEnum.NO);
            if (!resultMessage.isSuccess()) {
                return Result.error("发送站内消息失败");
            }

            Result resultSend = staSendMail(user.getMail(), "kuka审核求通过", message);
            if (!resultSend.isSuccess()) {
                return Result.error("发送邮件失败");
            }
        }


        // 站内消息
        if (mould.getType() == MailTypeEnum.MESSAGE) {
            Result resultMessage = saveMessage(user, "应用请求通过", message, app.getId(), loginAppUser, JumpStatusEnum.NO);
            if (!resultMessage.isSuccess()) {
                return Result.error("发送站内消息失败");
            }
        }

        // 邮件
        if (mould.getType() == MailTypeEnum.MAIL) {
            Result resultSend = staSendMail(user.getMail(), "kuka审核通过", message);
            if (!resultSend.isSuccess()) {
                return Result.error("发送邮件失败");
            }
        }

        return Result.success();
    }

    /**
     * 保存站内消息
     * @param title
     * @param message
     * @return
     */
    private Result saveMessage(UserResp user, String title, String message ,
                               Long appId, LoginAppUser loginAppUser, JumpStatusEnum jumpStatus) {
        MessageReq req = new MessageReq();
        req.setUserId(user.getId());
        req.setTitle(title);
        req.setMessage(message);
        req.setType(MessageTypeEnum.APP);
        req.setJumpStatus(jumpStatus);
        req.setSourceId(appId);
        req.setStatus(MessageStatusEnum.UNREAD);
        return messageService.addMessage(req, loginAppUser.getUserName());
    }


    /**
     * 发送邮件
     * @param mail
     * @param title
     * @param message
     * @return
     */
    private Result staSendMail(String mail, String title, String message) {
        MailReq req = new MailReq();
        req.setMail(mail);
        req.setMessage(message);
        req.setTitle(title);
        Result result = mailMouldService.sendMail(req);
        return result;
    }
}
