package com.camelot.kuka.backend.service;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.shopcart.req.ShopCartReq;
import com.camelot.kuka.model.shopcart.resp.ShopCartResp;

import java.util.List;

/**
 * <p>Description: [购物车信息]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface ShopCartService {

    /***
     * <p>Description:[获取当前登录人的购物车]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result<List<ShopCartResp>> queryList(String loginUserName);

    /***
     * <p>Description:[新增]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result addShopCart(ShopCartReq req, String loginUserName);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result updateShopCart(ShopCartReq req, String loginUserName);

    /***
     * <p>Description:[删除]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result delShopCart(CommonReq req, String loginUserName);

    /**
     * 获取当前购物车数量
     * @param loginUserName
     * @return
     */
    Result queryCount(String loginUserName);
}
