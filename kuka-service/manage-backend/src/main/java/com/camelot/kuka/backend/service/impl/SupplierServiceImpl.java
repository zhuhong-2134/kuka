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
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    public List<Supplier> pageList(SupplierPageReq req, LoginAppUser loginAppUser) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Supplier> list = null;
        // kuka用户
        if (loginAppUser.getType() == UserTypeEnum.KUKA) {
            list = supplierDao.pageList(req);
        }
        // 集成商
        if (loginAppUser.getType() == UserTypeEnum.SUPPILER) {
            req.setLoginName(loginAppUser.getUserName());
            list = supplierDao.supplierPageList(req);
        }
        // 来访者
        if (loginAppUser.getType() == UserTypeEnum.VISITORS) {
            req.setLoginName(loginAppUser.getUserName());
            list = supplierDao.visitorPageList(req);
        }
        list.forEach(suppliers -> {
            formatAddress(suppliers);
        });
        return list;
    }

    @Override
    public Result<List<SupplierResp>> queryList(SupplierPageReq req) {
        List<Supplier> list = supplierDao.pageList(req);
        list.forEach(suppliers -> {
            formatAddress(suppliers);
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
        supplier.setCreateTime(new Date());
        supplier.setUpdateBy(loginUserName);
        supplier.setUpdateTime(new Date());
        supplier.setDelState(DeleteEnum.NO);
        // 参数处理
        Result result = saveHandle(supplier);
        if (!result.isSuccess()) {
            return result;
        }
        // 获取省市区线名称
        setAddressName(supplier);
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
        if (null == supplier.getUserId()) {
             return Result.error("责任人ID不能为空");
        }
        Result<UserResp> userRespResult = userClient.clientById(supplier.getUserId());
        if (!userRespResult.isSuccess() || null == userRespResult.getData()) {
            return Result.error("未获取到责任人信息");
        }
        // 修改用户类型
        UserReq req = new UserReq();
        req.setId(supplier.getUserId());
        req.setUpdateBy(supplier.getUpdateBy());
        req.setType(UserTypeEnum.SUPPILER);
        Result result = userClient.clientUpDATE(req);
        if (!result.isSuccess()) {
            return Result.error("绑定责任人失败");
        }

        supplier.setUserCreateTime(userRespResult.getData().getCreateTime());
        supplier.setSource(userRespResult.getData().getSource());
        // 当前集成商创建者属于绑定的责任人
        supplier.setCreateBy(userRespResult.getData().getUserName());

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
            // 获取省市区线名称
            setAddressName(supplier);
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
        SupplierResp resp = BeanUtil.copyBean(info, SupplierResp.class);


        // 放入应用信息
        Application query = new Application();
        query.setSupplierId(info.getId());
        query.setDelState(DeleteEnum.NO);
        List<Application> appList = applicationDao.homeAppList(query);
        resp.setAppList(BeanUtil.copyList(appList, ApplicationResp.class));

        return Result.success(resp);
    }

    @Override
    public Result<SupplierResp> queryByCreateName(String userName) {
        if (StringUtils.isBlank(userName)) {

        }
        Supplier req = new Supplier();
        req.setCreateBy(userName);
        req.setDelState(DeleteEnum.NO);
        List<Supplier> list = supplierDao.findList(req);
        if (list.isEmpty()) {
            return Result.success();
        }
        return Result.success(BeanUtil.copyBean(list.get(0), SupplierResp.class));
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

    /**
     * 通过地址编码获取地址名称
     * @param supplier
     */
    private void setAddressName(Supplier supplier) {
        List<String> codes = new ArrayList<>();
        if (StringUtils.isNoneBlank(supplier.getProvinceCode())) {
            codes.add(supplier.getProvinceCode());
        }
        if (StringUtils.isNoneBlank(supplier.getCityCode())) {
            codes.add(supplier.getCityCode());
        }
        if (StringUtils.isNoneBlank(supplier.getDistrictCode())) {
            codes.add(supplier.getDistrictCode());
        }
        if (codes.isEmpty()) {
            return;
        }
        Result<Map<String, String>> mapResult = userClient.queryAddressMap(codes);
        if (!mapResult.isSuccess()) {
            log.error("/n 通过地址编码转换地址名称失败， 参数：{}", JSON.toJSONString(codes));
            return;
        }
        Map<String, String> map = mapResult.getData();
        supplier.setProvinceName(map.get(supplier.getProvinceCode()));
        supplier.setCityName(map.get(supplier.getCityCode()));
        supplier.setDistrictName(map.get(supplier.getDistrictCode()));
    }

}
