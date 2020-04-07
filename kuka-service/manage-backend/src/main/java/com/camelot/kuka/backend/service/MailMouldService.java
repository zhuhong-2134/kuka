package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldPageReq;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldReq;
import com.camelot.kuka.model.backend.mailmould.resp.MailMouldResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.MailReq;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * 邮件模板
 *
 * @date 2020/02/17
 */
public interface MailMouldService {

    /**
     * 分页查询
     * @param req
     * @return list
     */
    List<MailMould> pageList(MailMouldPageReq req);

    /**
     * 新增
     */
    Result addMailMould(MailMouldReq req, String loginUserName);

    /**
     * 根据主键查询
     * @param req
     * @return
     */
    Result<MailMouldResp> queryById(CommonReq req);

    /**
     * 更新
     */
    Result updateMailMould(MailMouldReq req, String loginUserName);

    /**
     * 删除
     */
    Result delMailMould(MailMouldReq req, String loginUserName);

    /**
     * 发送邮件
     */
    Result sendMail(MailReq req);
}
