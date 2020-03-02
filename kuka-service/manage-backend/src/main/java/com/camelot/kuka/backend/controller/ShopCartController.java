package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.service.ShopCartService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.shopcart.req.ShopCartReq;
import com.camelot.kuka.model.shopcart.resp.ShopCartResp;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [购物车控制层]</p>
 * Created on 2020/2/12
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "购物车API", tags = { "购物车接口" })
public class ShopCartController extends BaseController {

    @Resource
    private ShopCartService shopCartService;

    /***
     * <p>Description:[获取当前登录人的购物车]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @GetMapping("/shopCart/queryList")
    public Result<List<ShopCartResp>> queryList(){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return shopCartService.queryList(loginUserName);
        } catch (Exception e) {
            log.error("\n \"购物车模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryList", JSON.toJSONString(null), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[新增购物车]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/shopCart/add")
    public Result addShopCart(ShopCartReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return shopCartService.addShopCart(req, loginUserName);
        } catch (Exception e) {
            log.error("\n \"购物车模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "addShopCart", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[修改购物车]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/shopCart/update")
    public Result updateShopCart(ShopCartReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return shopCartService.updateShopCart(req, loginUserName);
        } catch (Exception e) {
            log.error("\n \"购物车模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateShopCart", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[删除购物车]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/shopCart/del")
    public Result delShopCart(CommonReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            return shopCartService.delShopCart(req, loginUserName);
        } catch (Exception e) {
            log.error("\n \"购物车模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "delShopCart", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}
