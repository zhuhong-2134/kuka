package com.camelot.kuka.backend.controller;

import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.common.utils.EnumUtil;
import com.camelot.kuka.model.backend.req.SupplierPageReq;
import com.camelot.kuka.model.backend.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: [供应商控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "供应商API", tags = { "供应商接口" })
public class SupplierController extends BaseController {

    @Autowired
    private SupplierService supplierService;

    /***
     * <p>Description:[分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/supplier/pageList")
    public PageResult<List<SupplierResp>> pageList(SupplierPageReq req){
        // 开启分页
        startPage();
        // 返回分页
        List<Supplier> suppliers = supplierService.queryList(req);
        PageResult<List<SupplierResp>> page = getPage(suppliers, SupplierResp.class);
        page.putEnumVal("typeEnum", EnumUtil.getEnumJson(SupplierTypeEnum.class));
        page.putEnumVal("industryEnum", EnumUtil.getEnumJson(IndustryTypeEnum.class));
        page.putEnumVal("appTypeEnum", EnumUtil.getEnumJson(SkilledAppEnum.class));
        page.putEnumVal("patternTypeEnum", EnumUtil.getEnumJson(PatternTypeEnum.class));
        page.putEnumVal("delStateEnum", EnumUtil.getEnumJson(DeleteEnum.class));
        page.putEnumVal("queryTypeEnum", EnumUtil.getEnumJson(SuppliePageEnum.class));
        return page;
    }

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/add")
    public Result addSupplier(SupplierResp req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return supplierService.addSupplier(req, loginUserName);
    }

    /***
     * <p>Description:[单条查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/queryById")
    public Result<SupplierResp> queryById(CommonReq req){
        return supplierService.queryById(req);
    }

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/update")
    public Result updateSupplier(SupplierResp req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return supplierService.updateSupplier(req, loginUserName);
    }

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.Result
     * @author 谢楠
     */
    @PostMapping("/supplier/del")
    public Result delSupplier(CommonReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return supplierService.delSupplier(req, loginUserName);
    }
}
