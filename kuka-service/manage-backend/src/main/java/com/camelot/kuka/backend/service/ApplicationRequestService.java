package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.ApplicationRequest;
import com.camelot.kuka.model.backend.applicationrequest.req.AppRequestPageReq;
import com.camelot.kuka.model.backend.applicationrequest.req.ApplicationRequestReq;
import com.camelot.kuka.model.backend.applicationrequest.resp.ApplicationRequestResp;
import com.camelot.kuka.model.backend.message.resp.MessageResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;

import java.util.List;

/**
 * <p>Description: [应用商请求]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ApplicationRequestService {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return list
     *
     */
    List<ApplicationRequest> queryList(AppRequestPageReq req);

    /***
     * <p>Description:[根据ID获取数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return resultr
     *
     */
    Result<ApplicationRequestResp> queryById(CommonReq req);

    /***
     * <p>Description:[修改状态]</p>
     * Created on 2020/1/20
     * @param req
     * @return resultr
     *
     */
    Result updateStatus(ApplicationRequestReq req, String loginUserName);

    /***
     * <p>Description:[新增应用请求]</p>
     * Created on 2020/1/20
     * @param req
     * @return resultr
     *
     */
    Result addApprequest(CommonReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[发送邮件]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     *
     */
    Result sendMail(CommonReq req, LoginAppUser loginAppUser);

    /***
     * <p>Description:[预览邮件信息]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     *
     */
    Result<MessageResp> previewMessage(CommonReq req, LoginAppUser loginAppUser);
}
