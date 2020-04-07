package com.camelot.kuka.backend.service;

import com.camelot.kuka.model.backend.application.req.ApplicationProblemReq;
import com.camelot.kuka.model.backend.application.resp.ApplicationProblemResp;
import com.camelot.kuka.model.common.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>Description: [应用常见问题接口]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ApplicationProbleService {

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     *
     */
    Result addProbleApplication(ApplicationProblemReq req, String loginUserName);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     *
     */
    Result updateProbleApplication(ApplicationProblemReq req, String loginUserName);

    /***
     * <p>Description:[根据应用ID获取]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     *
     */
    Result<List<ApplicationProblemResp>> queryByAppId(ApplicationProblemReq req, String loginUserName);
}
