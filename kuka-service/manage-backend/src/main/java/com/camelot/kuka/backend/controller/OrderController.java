package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.service.OrderService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.order.req.OrderPageReq;
import com.camelot.kuka.model.backend.order.req.OrderReq;
import com.camelot.kuka.model.backend.order.resp.OrderResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.order.OrderBusinessEnum;
import com.camelot.kuka.model.enums.order.OrderPageEnum;
import com.camelot.kuka.model.enums.order.OrderStatusEnum;
import com.camelot.kuka.model.enums.order.PayTypeEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.shopcart.req.ShopCartReq;
import com.camelot.kuka.model.user.LoginAppUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [订单控制层]</p>
 * Created on 2020/2/12
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "订单API", tags = { "订单接口" })
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;
    @Resource
    private SupplierService supplierService;

    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("statusEnum", EnumVal.getEnumList(OrderStatusEnum.class));
        page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(OrderPageEnum.class));
        page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
        page.putEnumVal("payTypeEnum", EnumVal.getEnumList(PayTypeEnum.class));
        return page;
    }

    /***
     * <p>Description:[kuka用户-分页查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/pageList")
    public PageResult<List<OrderResp>> pageList(OrderPageReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (null == loginAppUser) {
                return PageResult.error("用户未登陆");
            }
            // 集成商
            if (loginAppUser.getType() == UserTypeEnum.SUPPILER && req.getBusiness() == OrderBusinessEnum.SELL) {
                // 获取当前用户拥有的集成商
                req.setSupplierIds(supplierService.queryLoginSupplierIds(loginAppUser.getUserName()));
            }
            // 开启分页
            startPage();
            List<Order> order = orderService.pageList(req, loginAppUser);
            // 返回分页
            PageResult<List<OrderResp>> page = getPage(order, OrderResp.class);
            page.putEnumVal("statusEnum", EnumVal.getEnumList(OrderStatusEnum.class));
            page.putEnumVal("queryTypeEnum", EnumVal.getEnumList(OrderPageEnum.class));
            page.putEnumVal("delStateEnum", EnumVal.getEnumList(DeleteEnum.class));
            page.putEnumVal("payTypeEnum", EnumVal.getEnumList(PayTypeEnum.class));
            return page;
        } catch (Exception e) {
            log.error("\n 订单模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "pageList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }


    /***
     * <p>Description:[通过ID获取单条数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/queryById")
    public Result<OrderResp> queryById(CommonReq req){
        try {
            return orderService.queryById(req);
        } catch (Exception e) {
            log.error("\n 订单模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "queryById", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[通过ID获取单条数据]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/update")
    public Result updateOrder(OrderReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            if (null == loginUserName) {
                return Result.error("用户未登录");
            }
            return orderService.updateOrder(req, loginUserName);
        } catch (Exception e) {
            log.error("\n 订单模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateOrder", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[创建订单]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/createOrder")
    public Result<OrderResp> createOrder(CommonReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginUser();
            if (null == loginAppUser) {
                return Result.error("用户未登录");
            }
            Result<OrderResp> order = orderService.createOrder(req, loginAppUser);
            order.putEnumVal("payTypeEnum", EnumVal.getEnumList(PayTypeEnum.class));
            return order;
        } catch (Exception e) {
            log.error("\n 订单模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "createOrder", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[直接创建订单]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/order/createInstantly")
    public Result<OrderResp> createInstantly(ShopCartReq req){
        try {
            LoginAppUser loginAppUser = AppUserUtil.getLoginUser();
            if (null == loginAppUser) {
                return Result.error("用户未登录");
            }
            Result<OrderResp> order = orderService.createInstantly(req, loginAppUser);
            order.putEnumVal("payTypeEnum", EnumVal.getEnumList(PayTypeEnum.class));
            return order;
        } catch (Exception e) {
            log.error("\n 订单模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "createInstantly", JSON.toJSONString(req), e);
            return Result.error("网络异常, 请稍后再试");
        }
    }
}
