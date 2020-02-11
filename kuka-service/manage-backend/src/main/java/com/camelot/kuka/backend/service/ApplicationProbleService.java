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
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ApplicationProbleService {

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    Result addProbleApplication(ApplicationProblemReq req, String loginUserName);

}
