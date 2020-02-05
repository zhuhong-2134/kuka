package com.camelot.kuka.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camelot.kuka.backend.dao.SupplierDao;
import com.camelot.kuka.backend.model.Supplier;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.req.SupplierPageReq;
import com.camelot.kuka.model.backend.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
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

    @Override
    public List<Supplier> queryList(SupplierPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Supplier> list = supplierDao.queryList(req);
        list.forEach(suppliers -> {
            JSONObject addressJson = formatAddress(suppliers);
            suppliers.setAddressJson(addressJson.toJSONString());
        });
        return list;
    }

    @Override
    public Result addSupplier(SupplierResp req, String loginUserName) {
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
    public Result updateSupplier(SupplierResp req, String loginUserName) {
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
        return  zong;
    }
}
