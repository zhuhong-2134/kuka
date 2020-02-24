package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.ApplicationRequest;
import com.camelot.kuka.model.backend.applicationrequest.req.AppRequestPageReq;
import com.camelot.kuka.model.backend.applicationrequest.req.ApplicationRequestReq;
import com.camelot.kuka.model.backend.applicationrequest.resp.ApplicationRequestResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [应用商请求]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ApplicationRequestService {

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return list
     * @author 谢楠
     */
    List<ApplicationRequest> queryList(AppRequestPageReq req);

    /***
     * <p>Description:[根据ID获取数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return resultr
     * @author 谢楠
     */
    Result<ApplicationRequestResp> queryById(CommonReq req);

    /***
     * <p>Description:[修改状态]</p>
     * Created on 2020/1/20
     * @param req
     * @return resultr
     * @author 谢楠
     */
    Result updateStatus(ApplicationRequestReq req, String loginUserName);
}
