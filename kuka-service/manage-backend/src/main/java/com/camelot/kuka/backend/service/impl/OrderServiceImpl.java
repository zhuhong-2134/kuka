package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.OrderDao;
import com.camelot.kuka.backend.dao.OrderDetailedDao;
import com.camelot.kuka.backend.dao.ShopCartDao;
import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.model.OrderDetailed;
import com.camelot.kuka.backend.model.ShopCart;
import com.camelot.kuka.backend.service.CommentService;
import com.camelot.kuka.backend.service.OrderService;
import com.camelot.kuka.backend.service.ShopCartService;
import com.camelot.kuka.backend.service.SupplierService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.backend.order.req.OrderPageReq;
import com.camelot.kuka.model.backend.order.req.OrderReq;
import com.camelot.kuka.model.backend.order.resp.OrderDetailedResp;
import com.camelot.kuka.model.backend.order.resp.OrderResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.order.OrderStatusEnum;
import com.camelot.kuka.model.shopcart.req.ShopCartReq;
import com.camelot.kuka.model.user.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [订单业务层]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderDetailedDao orderDetailedDao;
    @Resource
    private CommentService commentService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private ShopCartDao shopCartDao;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;
    @Resource
    private ShopCartService shopCartService;


    @Override
    public List<Order> pageList(OrderPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Order> orders = orderDao.pageList(req);
        // 放入订单明细
        getOrderDetaileList(orders);
        return orders;
    }

    @Override
    public List<Order> supplierPageList(OrderPageReq req) {
        // 获取当前用户拥有的集成商
        Long[] supplierIds = supplierService.queryLoginSupplierIds(req.getLoginName());
        req.setSupplierIds(supplierIds);
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Order> orders = orderDao.supplierPageList(req);
        // 放入订单明细
        getOrderDetaileList(orders);
        return orders;
    }

    @Override
    public List<Order> visitorPageList(OrderPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Order> orders = orderDao.visitorPageList(req);
        // 放入订单明细
        getOrderDetaileList(orders);
        return orders;
    }

    @Override
    public Result<OrderResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("参数错误");
        }
        Order query = new Order();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        Order order = orderDao.queryById(query);
        if (null == order) {
            return Result.error("数据获取失败, 刷新后重试");
        }
        OrderResp orderResp = BeanUtil.copyBean(order, OrderResp.class);

        // 放入明细
        Long[] orderIds = new Long[]{req.getId()};
        List<OrderDetailed> deailList = orderDetailedDao.selectByOrderIds(orderIds);
        orderResp.setDetaileList(BeanUtil.copyList(deailList, OrderDetailedResp.class ));


        // 获取评论信息
        List<Comment> comments = commentService.queryByOrderIds(orderIds);
        // 放入明细
        for (OrderDetailedResp orderDetailed : orderResp.getDetaileList()) {
            for (Comment comment : comments) {
                if (comment.getAppId().compareTo(orderDetailed.getAppId()) == 0) {
                    orderDetailed.setComment(BeanUtil.copyBean(comment, CommentResp.class));
                    break;
                }
            }
        }

        return Result.success(orderResp);
    }


    @Override
    public Result updateOrder(OrderReq req, String loginUserName) {
        if (null == req || null == req.getId() || StringUtils.isBlank(req.getOrderNo())) {
            return Result.error("参数错误");
        }
        Order order = BeanUtil.copyBean(req, Order.class);
        order.setUpdateBy(loginUserName);
        order.setUpdateTime(new Date());
        int con = orderDao.update(order);
        if (con == 0) {
            return Result.error("修改失败, 联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result<OrderResp> createOrder(CommonReq req, LoginAppUser loginAppUser) {
        if (null == req.getIds()) {
            return Result.error("购物车ID不能为空");
        }
        // 获取购物车信息
        List<ShopCart> shopCartList = shopCartDao.selectIds(req.getIds());
        if (shopCartList.isEmpty()) {
            return Result.error("获取购物车信息失败");
        }
        // 获取订单号
        String orderNo = codeGenerateUtil.generateNumber();
        // 生产订单
        Order order = handleOrder(orderNo, loginAppUser, shopCartList);
        if (null == order) {
            return Result.error("新增订单失败");
        }
        // 生成订单明细
        List<OrderDetailed> orderDetaileds = handleDetaile(order.getId(), loginAppUser, shopCartList);
        if (null == orderDetaileds) {
            return Result.error("新增订单明细失败");
        }

        // 删除购物车信息
        Long[] ids = shopCartList.stream().map(ShopCart::getId).toArray(Long[]::new);
        ShopCart delShop = new ShopCart();
        delShop.setIds(ids);
        delShop.setDelState(DeleteEnum.YES);
        delShop.setUpdateTime(new Date());
        delShop.setUpdateBy(loginAppUser.getUserName());
        int con = shopCartDao.updateDel(delShop);
        if (0 == con) {
            return Result.error("清空购物车失败");
        }
        order.setDetaileList(BeanUtil.copyList(orderDetaileds, OrderDetailedResp.class));
        return Result.success(BeanUtil.copyBean(order, OrderResp.class));
    }

    @Override
    public Result<OrderResp> createInstantly(ShopCartReq req, LoginAppUser loginAppUser) {
        // 创建购物车
        Result result = shopCartService.addShopCart(req, loginAppUser.getUserName());
        if (!result.isSuccess()) {
            return Result.error(result.getMsg());
        }
        // 新增订单信息
        CommonReq com = new CommonReq();
        Long[] ids = new Long[]{(Long)result.getData()};
        com.setIds(ids);
        return this.createOrder(com, loginAppUser);
    }

    /**
     * 处理订单
     * @param orderNo
     * @return
     */
    private Order handleOrder(String orderNo, LoginAppUser loginAppUser, List<ShopCart> shopCartList) {
        Order order = new Order();
        // 获取ID
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_ORDER);
        order.setId(id);
        order.setOrderNo(orderNo);
        order.setContactMail(loginAppUser.getMail());
        order.setContactPhone(loginAppUser.getPhone());
        order.setContactBy(loginAppUser.getUsername());
        order.setOrderPhone(loginAppUser.getPhone());
        order.setOrderMail(loginAppUser.getMail());
        order.setDelState(DeleteEnum.NO);
        order.setCreateBy(loginAppUser.getUsername());
        order.setCreateTime(new Date());
        order.setStatus(OrderStatusEnum.WAIT);
        // 计算总价
        Double sunPrice = Double.valueOf(0);
        for (ShopCart shopCart : shopCartList) {
            sunPrice += null == shopCart.getSunPrice() ? Double.valueOf(0) : shopCart.getSunPrice();
        }
        order.setSunPrice(sunPrice);
        int con = orderDao.addBatch(Arrays.asList(order));
        if (0 == con) {
            return null;
        }
        return order;
    }

    /**
     * 处理订单明细
     * @param
     * @return
     */
    List<OrderDetailed> handleDetaile(Long orderId, LoginAppUser loginAppUser, List<ShopCart> shopCartList){
        List<OrderDetailed> addList = new ArrayList<>();
        for (ShopCart shopCart : shopCartList) {
            OrderDetailed detailed = new OrderDetailed();
            // 获取ID
            Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_ORDERDETAILED);
            detailed.setId(id);
            detailed.setAppId(shopCart.getAppId());
            detailed.setAppName(shopCart.getAppName());
            detailed.setAppUrl(shopCart.getAppUrl());
            detailed.setNum(shopCart.getNum());
            detailed.setPrice(shopCart.getPrice());
            detailed.setOrderId(orderId);
            detailed.setSumPrice(shopCart.getSunPrice());
            detailed.setDelState(DeleteEnum.NO);
            detailed.setCreateBy(loginAppUser.getCreateBy());
            detailed.setCreateTime(new Date());
            addList.add(detailed);
        }
        int con = orderDetailedDao.addBatch(addList);
        if (con == 0) {
            return null;
        }
        return addList;
    }

    /**
     * 获取明细信息
     * @param orders
     */
    private void getOrderDetaileList(List<Order> orders){
        if (orders.isEmpty()) {
            return;
        }
        Long[] ids = orders.stream().map(Order::getId).toArray(Long[]::new);
        // 获取明细信息
        List<OrderDetailed> deailList = orderDetailedDao.selectByOrderIds(ids);
        for (Order order : orders) {
            List<OrderDetailed> newList = new ArrayList<>();
            for (OrderDetailed orderDetailed : deailList) {
                if (order.getId().compareTo(orderDetailed.getOrderId()) == 0) {
                    newList.add(orderDetailed);
                }
            }
            order.setDetaileList(BeanUtil.copyBeanList(newList, OrderDetailedResp.class));
        }
    }

}
