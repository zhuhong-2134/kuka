package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.application.req.ApplicationCurrencyReq;
import com.camelot.kuka.model.backend.application.req.ApplicationEditReq;
import com.camelot.kuka.model.backend.application.req.ApplicationProblemReq;
import com.camelot.kuka.model.backend.application.resp.QyeryUpdateResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [产品接口]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ApplicationService {

    /***
     * <p>Description:[库卡后台-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> queryList(AppPageReq req);

    /***
     * <p>Description:[集成商-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> supplierPageList(AppPageReq req);

    /***
     * <p>Description:[来访者-分页查询]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    List<Application> visitorPageList(AppPageReq req);

    /***
     * <p>Description:[新增产品信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result addApplication(ApplicationEditReq req, String loginUserName);

    /***
     * <p>Description:[通过ID获取修改的产品信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result<QyeryUpdateResp> qyeryUpdateById(CommonReq req);

    /***
     * <p>Description:[修改产品信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result updateApplication(ApplicationEditReq req, String loginUserName);

    /***
     * <p>Description:[新增适用产品信息]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result addCurrency(ApplicationCurrencyReq req);

    /***
     * <p>Description:[修改应用状态]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result updateAppStatus(ApplicationEditReq req, String loginUserName);

    /***
     * <p>Description:[删除应用]</p>
     * Created on 2020/2/5
     * @param req
     * @return List<Supplier>
     * @author 谢楠
     */
    Result deleteApplication(CommonReq req, String loginUserName);

}
