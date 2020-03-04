package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.backend.order.resp.OrderDetailedResp;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.order.OrderStatusEnum;
import com.camelot.kuka.model.enums.order.PayTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [订单实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 7182521177493835857L;
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
     * 应用联系邮箱
     */
    private String contactMail;

    /**
     * 应用联系人电话
     */
    private String contactPhone;

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

    /**********************************/
    /*****以下字段,不是数据库字段******/
    /**********************************/


    /**
     * 订单明细
     */
    private List<OrderDetailedResp> detaileList;
}
