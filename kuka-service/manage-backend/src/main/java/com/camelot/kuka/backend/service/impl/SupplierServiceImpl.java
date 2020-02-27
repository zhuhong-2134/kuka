package com.camelot.kuka.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camelot.kuka.backend.dao.ApplicationDao;
import com.camelot.kuka.backend.dao.SupplierDao;
import com.camelot.kuka.backend.feign.user.UserClient;
import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.application.resp.ApplicationResp;
import com.camelot.kuka.model.backend.supplier.req.SupplierPageReq;
import com.camelot.kuka.model.backend.supplier.req.SupplierReq;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.user.resp.UserResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [用户信息]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierDao supplierDao;
    @Resource
    private UserClient userClient;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;
    @Resource
    private ApplicationDao applicationDao;

    @Override
    public List<Supplier> pageList(SupplierPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Supplier> list = supplierDao.pageList(req);
        list.forEach(suppliers -> {
            JSONObject addressJson = formatAddress(suppliers);
            suppliers.setAddressJson(addressJson.toJSONString());
        });
        return list;
    }

    @Override
    public List<Supplier> supplierPageList(SupplierPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Supplier> list = supplierDao.supplierPageList(req);
        list.forEach(suppliers -> {
            JSONObject addressJson = formatAddress(suppliers);
            suppliers.setAddressJson(addressJson.toJSONString());
        });
        return list;
    }

    @Override
    public List<Supplier> visitorPageList(SupplierPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Supplier> list = supplierDao.visitorPageList(req);
        list.forEach(suppliers -> {
            JSONObject addressJson = formatAddress(suppliers);
            suppliers.setAddressJson(addressJson.toJSONString());
        });
        return list;
    }

    @Override
    public Result<List<SupplierResp>> queryList(SupplierPageReq req) {
        List<Supplier> list = supplierDao.pageList(req);
        list.forEach(suppliers -> {
            JSONObject addressJson = formatAddress(suppliers);
            suppliers.setAddressJson(addressJson.toJSONString());
        });
        List<SupplierResp> supplierResps = BeanUtil.copyBeanList(list, SupplierResp.class);
        return Result.success(supplierResps);
    }

    @Override
    public Result addSupplier(SupplierReq req, String loginUserName) {
        if (null == req) {
            return Result.error("参数不能为空");
        }
        if (StringUtils.isBlank(req.getSupplierlName())) {
            return Result.error("集成商名称不能为空");
        }
        if (StringUtils.isBlank(req.getSupplierlDesc())) {
            return Result.error("集成商详情不能为空");
        }
        if (null == req.getType()) {
            return Result.error("集成商类型不能为空");
        }
        if (null == req.getIndustry()) {
            return Result.error("所属行业不能为空");
        }
        if (null == req.getAppType()) {
            return Result.error("擅长应用不能为空");
        }
        if (null == req.getPatternType()) {
            return Result.error("经营模式不能为空");
        }
        Supplier supplier = BeanUtil.copyBean(req, Supplier.class);
        // 获取ID
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_SUPPLIER);
        supplier.setId(id);
        // 参数处理
        Result result = saveHandle(supplier);
        if (!result.isSuccess()) {
            return result;
        }
        // 固定参数
        supplier.setCreateBy(loginUserName);
        supplier.setCreateTime(new Date());
        supplier.setDelState(DeleteEnum.NO);
        try {
            int con = supplierDao.addSupplier(Arrays.asList(supplier));
            if (con == 0) {
                return Result.error("新增失败");
            }
            return Result.success();
        } catch (Exception e) {
            log.error("\n 新增供应商失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("新增失败");
    }

    /**
     * 新增处理责任人
     * @param supplier
     * @return
     */
    private Result saveHandle(Supplier supplier) {
        // 新增用户信息
//        if (null == supplier.getUserId()) {
//             后期如果责任人信息不存在,新增用户,待确认
//        }
        if (null == supplier.getUserId() ) {
            // 临时的
            return Result.success();
        }
        // 获取用户信息
        CommonReq req = new CommonReq();
        req.setId(supplier.getId());
        Result<UserResp> userRespResult = userClient.queryById(req);
        if (!userRespResult.isSuccess() || null == userRespResult.getData()) {
            return Result.error("未获取到用户信息");
        }
        supplier.setUserCreateTime(userRespResult.getData().getCreateTime());
        supplier.setSource(userRespResult.getData().getSource());
        return Result.success();
    }

    @Override
    public Result<SupplierResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Supplier supplier = BeanUtil.copyBean(req, Supplier.class);
        supplier.setDelState(DeleteEnum.NO);
        try {
            Supplier info = supplierDao.queryById(supplier);
            JSONObject address = formatAddress(info);
            info.setAddressJson(address.toJSONString());
            if (null == info) {
                return Result.error("数据获取失败,刷新后重试");
            }
            return Result.success(BeanUtil.copyBean(info, SupplierResp.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n 获取供应商失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("数据获取失败");
    }

    @Override
    public Result updateSupplier(SupplierReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        try {
            Supplier supplier = BeanUtil.copyBean(req, Supplier.class);
            // 固定参数
            supplier.setUpdateBy(loginUserName);
            supplier.setUpdateTime(new Date());
            int con = supplierDao.updateSupplier(supplier);
            if (con == 0) {
                return Result.error("修改失败");
            }
            return Result.success();
        } catch (Exception e) {
            log.error("\n 修改供应商失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("修改失败");
    }

    @Override
    public Result delSupplier(CommonReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Supplier supplier = new Supplier();
        supplier.setId(req.getId());
        supplier.setDelState(DeleteEnum.YES);
        supplier.setUpdateBy(loginUserName);
        supplier.setUpdateTime(new Date());
        try {
            int con = supplierDao.delSupplier(supplier);
            if (con == 0) {
                return Result.error("删除失败");
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n 删除供应商失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("删除失败");
    }

    @Override
    public Long[] queryLoginSupplierIds(String loginUserName) {
        Supplier supplier = new Supplier();
        supplier.setDelState(DeleteEnum.NO);
        supplier.setUpdateBy(loginUserName);
        List<Supplier> list = supplierDao.findList(supplier);
        if (list.isEmpty()) {
            // 返回 -1 代表没有自己的集成商
            return new Long[]{-1L};
        }
        Long[] ids = list.stream().map(Supplier::getId).toArray(Long[]::new);
        return ids;
    }

    @Override
    public Result<SupplierResp> querySuppAndAppById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Supplier supplier = BeanUtil.copyBean(req, Supplier.class);
        supplier.setDelState(DeleteEnum.NO);
        Supplier info = supplierDao.queryById(supplier);
        if (null == info) {
            return Result.error("数据获取失败,刷新后重试");
        }
        JSONObject address = formatAddress(info);
        info.setAddressJson(address.toJSONString());
        SupplierResp resp = BeanUtil.copyBean(info, SupplierResp.class);


        // 放入应用信息
        Application query = new Application();
        query.setSupplierId(info.getId());
        query.setDelState(DeleteEnum.NO);
        List<Application> appList = applicationDao.homeAppList(query);
        resp.setAppList(BeanUtil.copyList(appList, ApplicationResp.class));

        return Result.success(resp);
    }

    /**
     *  格式化地址信息
     * @param supplier
     * @return
     */
    private JSONObject formatAddress(Supplier supplier) {
        // 区
        JSONObject qu = new JSONObject();
        qu.put("label", supplier.getDistrictName());
        qu.put("value", supplier.getDistrictCode());
        // 市
        JSONObject shi = new JSONObject();
        shi.put("label", supplier.getCityName());
        shi.put("value", supplier.getCityCode());
        shi.put("children", qu);
        // 省
        JSONObject shen = new JSONObject();
        shen.put("label", supplier.getProvinceName());
        shen.put("value", supplier.getProvinceCode());
        shen.put("children", shi);
        // 总
        JSONObject zong = new JSONObject();
        zong.put("options", shen);
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
        return  zong;
    }
}
