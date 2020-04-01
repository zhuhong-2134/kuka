package com.camelot.kuka.model.backend.order.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.order.OrderStatusEnum;
import com.camelot.kuka.model.enums.order.PayTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [订单请求实体]</p>
 * Created on 2020/1/20
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class OrderReq implements Serializable {


    private static final long serialVersionUID = -5418380181214207936L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 应用联系人
     */
    private String contactBy;

    /**
     * 应用联系人电话
     */
    private String contactPhone;

    /**
     * 应用联系邮箱
     */
    private String contactMail;

    /**
     * 总额
     */
    private Double sunPrice;

    /**
     * 订单状态;0:待支付;1已完成
     */
    private OrderStatusEnum status;

    /**
     * 留言
     */
    private String remarks;

    /**
     * 订单联电话
     */
    private String orderPhone;

    /**
     * 订单联邮箱
     */
    private String orderMail;

    /**
     * 支付方式
     */
    private PayTypeEnum payType;

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 删除标识0:未删除;1已删除
     */
    private DeleteEnum delState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;
}
