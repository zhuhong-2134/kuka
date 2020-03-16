package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.SupplierDao;
import com.camelot.kuka.backend.dao.SupplierRequestDao;
import com.camelot.kuka.backend.feign.user.UserClient;
import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.model.SupplierRequest;
import com.camelot.kuka.backend.service.MailMouldService;
import com.camelot.kuka.backend.service.MessageService;
import com.camelot.kuka.backend.service.SupplierRequestService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldPageReq;
import com.camelot.kuka.model.backend.message.req.MessageReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestPageReq;
import com.camelot.kuka.model.backend.supplierrequest.req.SupplierRequestReq;
import com.camelot.kuka.model.backend.supplierrequest.resp.SupplierRequestResp;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [集成商请求业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("supplierRequestService")
public class SupplierRequestServiceImpl implements SupplierRequestService {

    @Resource
    private SupplierRequestDao supplierRequestDao;
    @Resource
    private SupplierService supplierService;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;
    @Resource
    private MessageService messageService;
    @Resource
    private SupplierDao supplierDao;
    @Resource
    private MailMouldService mailMouldService;
    @Resource
    private UserClient userClient;

    @Override
    public List<SupplierRequest> queryList(SupplierRequestPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<SupplierRequest> list = supplierRequestDao.pageList(req);
        return list;
    }

    @Override
    public Result<SupplierRequestResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("ID不能为空");
        }
        SupplierRequest query = new SupplierRequest();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        SupplierRequest supplierRequest = supplierRequestDao.queryInfo(query);
        if (null == supplierRequest) {
            return Result.error("获取失败, 刷新后重试");
        }
        return Result.success(BeanUtil.copyBean(supplierRequest, SupplierRequestResp.class));
    }

    @Override
    public Result updateStatus(SupplierRequestReq req, String loginUserName) {
        if (null == req || req.getId() == null) {
            return Result.error("ID不能为空");
        }
        if (null == req.getStatus()) {
            return Result.error("状态不能为空");
        }
        SupplierRequest supplierRequest = BeanUtil.copyBean(req, SupplierRequest.class);
        supplierRequest.setUpdateBy(loginUserName);
        supplierRequest.setUpdateTime(new Date());
        int update = supplierRequestDao.update(supplierRequest);
        if (0 == update) {
            return Result.error("修改失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result addSupplierequest(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req.getId()) {
            return Result.error("集成商ID不能为空");
        }
        CommonReq query = new CommonReq();
        query.setId(req.getId());
        Result<SupplierResp> supplierRespResult = supplierService.queryById(query);
        if (!supplierRespResult.isSuccess()) {
            return Result.error("获取集成商失败");
        }
        SupplierResp supplier = supplierRespResult.getData();
        // 新增对象
        SupplierRequest request = new SupplierRequest();
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_SUPPLIER_REQUEST);
        request.setId(id);
        request.setSupplierUrl(supplier.getCoverUrl());
        request.setSupplierId(supplier.getId());
        request.setSupplierName(supplier.getSupplierlName());

        request.setLocation(supplier.getSupplierAddress());
        request.setDutyName(supplier.getUserName());
        request.setDutyId(supplier.getUserId());
        request.setDutyPhone(supplier.getUserPhone());
        request.setMail(supplier.getUserMali());
        request.setAddress(supplier.getUserAddress());
        request.setStatus(CommunicateEnum.NO);
        request.setUserId(loginAppUser.getId());
        request.setUserName(loginAppUser.getUserName());
        request.setUserPhotoUrl(loginAppUser.getPhotoUrl());
        request.setUserPhone(loginAppUser.getPhone());
        request.setUserMail(loginAppUser.getMail());
        request.setDelState(DeleteEnum.NO);
        request.setCreateBy(loginAppUser.getUsername());
        request.setCreateTime(new Date());
        int con = supplierRequestDao.addBatch(Arrays.asList(request));
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
        SupplierRequest queryRequest = new SupplierRequest();
        queryRequest.setId(req.getId());
        queryRequest.setDelState(DeleteEnum.NO);
        SupplierRequest supplierRequest = supplierRequestDao.queryInfo(queryRequest);
        if (null == supplierRequest) {
            return Result.error("获取数据失败");
        }


        // 获取集成商
        Supplier query = new Supplier();
        query.setId(supplierRequest.getSupplierId());
        query.setDelState(DeleteEnum.NO);
        Supplier supplier = supplierDao.queryById(query);
        if (null == supplier) {
            return Result.error("获取集成商失败");
        }


        // 获取应用魔板
        List<MailMould> mailMoulds = mailMouldService.pageList(new MailMouldPageReq());
        if (mailMoulds.isEmpty()) {
            return Result.error("模板为空");
        }

        // 获取创建者的用户信息
        Result<UserResp> userRespResult = userClient.queryByUserName(supplierRequest.getCreateBy());
        if (!userRespResult.isSuccess()) {
            return Result.error("获取发送人信息失败");
        }

        // 不通过模板
        if (req.getStatus() == WhetherEnum.NO) {
            return messageNoSend(userRespResult.getData(), supplier, mailMoulds, loginAppUser);
        }
        // 通过模板
        if (req.getStatus() == WhetherEnum.YES) {
            return messageYesSend(userRespResult.getData(), supplier, mailMoulds, loginAppUser);
        }
        return Result.success();
    }

    /**
     * 集成商通过模板发送
     * @param supplier
     * @param mailMoulds
     * @return
     */
    private Result messageYesSend(UserResp user, Supplier supplier, List<MailMould> mailMoulds, LoginAppUser loginAppUser) {
        MailMould mould = new MailMould();
        for (MailMould m : mailMoulds) {
            if (m.getId().compareTo(3L) == 0) {
                mould = m;
            }
        }
        // 组装消息内容
        String message = mould.getMessage();
        // 集成商名称
        message = message.replace("{1}", supplier.getSupplierlName());
        // 集成商名称
        message = message.replace("{2}", supplier.getSupplierlName());
        // 总负责人
        message = message.replace("{3}", supplier.getUserName());
        // 总负责人联系方式
        message = message.replace("{4}", supplier.getUserPhone());
        // 联系邮箱
        message = message.replace("{5}", supplier.getUserMali());
        // 集成商详细地址
        formatAddress(supplier);
        message = message.replace("{6}", supplier.getSupplierAddress());


        // 全发
        if (mould.getType() == MailTypeEnum.ALL) {


            Result resultMessage = saveMessage(user, "集成商请求通过", message, supplier, loginAppUser, JumpStatusEnum.YES);
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
            Result resultMessage = saveMessage(user, "集成商请求通过", message, supplier, loginAppUser, JumpStatusEnum.YES);
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
     * 集成商不通过模板发送
     * @param supplier
     * @param mailMoulds
     * @return
     */
    private Result messageNoSend(UserResp user, Supplier supplier, List<MailMould> mailMoulds, LoginAppUser loginAppUser) {
        MailMould mould = new MailMould();
        for (MailMould m : mailMoulds) {
            if (m.getId().compareTo(4L) == 0) {
                mould = m;
            }
        }

        String message = mould.getMessage();
        // 集成商名称
        message = message.replace("{1}", supplier.getSupplierlName());

        // 全发
        if (mould.getType() == MailTypeEnum.ALL) {

            Result resultMessage = saveMessage(user, "集成商请求不通过", message, supplier, loginAppUser, JumpStatusEnum.NO);
            if (!resultMessage.isSuccess()) {
                return Result.error("发送站内消息失败");
            }

            Result resultSend = staSendMail(user.getMail(), "kuka审核不通过", message);
            if (!resultSend.isSuccess()) {
                return Result.error("发送邮件失败");
            }
        }

        // 站内消息
        if (mould.getType() == MailTypeEnum.MESSAGE) {
            Result resultMessage = saveMessage(user, "集成商请求不通过", message, supplier, loginAppUser, JumpStatusEnum.NO);
            if (!resultMessage.isSuccess()) {
                return Result.error("发送站内消息失败");
            }
        }

        // 邮件
        if (mould.getType() == MailTypeEnum.MAIL) {
            Result resultSend = staSendMail(user.getMail(), "kuka审核不通过", message);
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
    private Result saveMessage(UserResp user, String title, String message,
                               Supplier supplier, LoginAppUser loginAppUser,
                               JumpStatusEnum jumpStatus) {
        MessageReq req = new MessageReq();
        req.setUserId(user.getId());
        req.setTitle(title);
        req.setMessage(message);
        req.setType(MessageTypeEnum.SUPPLIER);
        req.setJumpStatus(jumpStatus);
        req.setSourceId(supplier.getId());
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


    /**
     *  格式化地址信息
     * @param supplier
     * @return
     */
    private void formatAddress(Supplier supplier) {
        // 格式化地址
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isNoneBlank(supplier.getProvinceName())) {
            stringBuffer.append(supplier.getProvinceName());
        }
        if (StringUtils.isNoneBlank(supplier.getCityName())) {
            stringBuffer.append(supplier.getCityName());
        }
        if (StringUtils.isNoneBlank(supplier.getDistrictName())) {
            stringBuffer.append(supplier.getDistrictName());
        }
        supplier.setSupplierAddress(stringBuffer.toString());
    }
}
