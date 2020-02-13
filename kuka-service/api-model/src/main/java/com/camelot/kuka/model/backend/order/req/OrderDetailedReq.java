package com.camelot.kuka.model.backend.order.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [订单明细实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class OrderDetailedReq implements Serializable {


    private static final long serialVersionUID = -2106577406691979027L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 集成商id
     */
    private Long supplierId;

    /**
     * 产品id
     */
    private Long appId;

    /**
     * 产品图片
     */
    private String appUrl;

    /**
     * 产品名称
     */
    private String appName;

    /**
     * 单价
     */
    private Double price;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 订单总额
     */
    private Double sumPrice;

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
