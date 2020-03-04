package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationDao;
import com.camelot.kuka.backend.dao.ApplicationImgDao;
import com.camelot.kuka.backend.dao.ShopCartDao;
import com.camelot.kuka.backend.model.Application;
import com.camelot.kuka.backend.model.ApplicationImg;
import com.camelot.kuka.backend.model.ShopCart;
import com.camelot.kuka.backend.service.ShopCartService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.shopcart.req.ShopCartReq;
import com.camelot.kuka.model.shopcart.resp.ShopCartResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [购物车信息]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("shopCartService")
public class ShopCartServiceImpl implements ShopCartService {

    @Resource
    private ShopCartDao shopCartDao;
    @Resource
    private ApplicationDao applicationDao;
    @Resource
    private ApplicationImgDao applicationImgDao;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public Result<List<ShopCartResp>> queryList(String loginUserName) {
        ShopCart query = new ShopCart();
        query.setDelState(DeleteEnum.NO);
        query.setCreateBy(loginUserName);
        List<ShopCart> shopCarts = shopCartDao.queryList(query);
        return Result.success(BeanUtil.copyBeanList(shopCarts, ShopCartResp.class));
    }

    @Override
    public Result addShopCart(ShopCartReq req, String loginUserName) {
        if (null == req ||  null == req.getAppId()) {
            return Result.error("应用ID不能为空");
        }
        if (null == req.getNum()) {
            return Result.error("数量不能为空");
        }
        // 获取应用信息
        Application application = queryApplication(req.getAppId());
        if (null == application) {
            return Result.error("没有应用信息");
        }
        // 查找是否已经新增
        ShopCart query = new ShopCart();
        query.setAppId(req.getAppId());
        query.setCreateBy(loginUserName);
        query.setDelState(DeleteEnum.NO);
        ShopCart oldShopCart = shopCartDao.queryInfo(query);

        // 修改数量总价
        if (null != oldShopCart) {
            Integer newNum = req.getNum() + oldShopCart.getNum();
            // 总价
            oldShopCart.setSunPrice(application.getPrice() * newNum);
            oldShopCart.setNum(newNum);
            oldShopCart.setUpdateBy(loginUserName);
            oldShopCart.setUpdateTime(new Date());
            int con = shopCartDao.update(oldShopCart);
            if (con == 0) {
                return Result.error("新增购物车失败");
            }
            return Result.success();
        }

        // 新增
        ShopCart shopCart = BeanUtil.copyBean(req, ShopCart.class);
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_SHOP_CART);
        shopCart.setId(id);
        shopCart.setAppUrl(application.getCoverUrl());
        shopCart.setAppName(application.getAppName());
        shopCart.setSupplierId(application.getSupplierId());
        shopCart.setPrice(application.getPrice());
        // 总价
        shopCart.setSunPrice(application.getPrice() * req.getNum());
        shopCart.setDelState(DeleteEnum.NO);
        shopCart.setCreateBy(loginUserName);
        shopCart.setCreateTime(new Date());
        int con = shopCartDao.insertBatch(Arrays.asList(shopCart));
        if (con == 0) {
            return Result.error("新增失败");
        }
        return Result.success(id);
    }

    @Override
    public Result updateShopCart(ShopCartReq req, String loginUserName) {
        if (null == req ||  null == req.getAppId()) {
            return Result.error("应用ID不能为空");
        }
        if (null == req.getId()) {
            return Result.error("主键不能为空");
        }
        if (null == req.getNum()) {
            return Result.error("数量不能为空");
        }
        // 获取应用信息
        Application application = queryApplication(req.getAppId());
        if (null == application) {
            return Result.error("没有应用信息");
        }
        ShopCart shopCart = BeanUtil.copyBean(req, ShopCart.class);
        // 总价
        shopCart.setSunPrice(application.getPrice() * req.getNum());
        shopCart.setUpdateBy(loginUserName);
        shopCart.setUpdateTime(new Date());
        int con = shopCartDao.update(shopCart);
        if (con == 0) {
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @Override
    public Result delShopCart(CommonReq req, String loginUserName) {
        if (null == req || null == req.getIds()) {
            return Result.error("主键不能为空");
        }
        ShopCart shopCart = new ShopCart();
        shopCart.setDelState(DeleteEnum.YES);
        shopCart.setUpdateBy(loginUserName);
        shopCart.setUpdateTime(new Date());
        shopCart.setIds(req.getIds());
        int con = shopCartDao.updateDel(shopCart);
        if (con == 0) {
            return Result.error("删除失败");
        }
        return Result.success();
    }


    /**
     * 获取应用信息
     * @param appId
     * @return
     */
    private Application queryApplication(Long appId) {
        Application qeruy = new Application();
        qeruy.setDelState(DeleteEnum.NO);
        qeruy.setId(appId);
        Application application = applicationDao.selectById(qeruy);
        if (null == application) {
            return null;
        }
        // 封面图
        setAppImg(Arrays.asList(application));
        return application;
    }

    /**
     * 获取封面图的第一张
     * @param list
     */
    private void setAppImg(List<Application> list){
        // 获取封面图片
        Long[] appIds = list.stream().map(Application::getId).toArray(Long[]::new);
        List<ApplicationImg> appImgs = applicationImgDao.selectList(appIds);
        for (Application application : list) {
            for (ApplicationImg appImg : appImgs) {
                if (application.getId() == appImg.getAppId()) {
                    application.setCoverUrl(appImg.getUrl());
                    break;
                }
            }
        }
    }
}
