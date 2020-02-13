package com.camelot.kuka.model.backend.order.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.order.OrderPageEnum;
import com.camelot.kuka.model.enums.order.OrderStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [订单所有字段请求实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class OrderPageReq implements Serializable {

	private static final long serialVersionUID = -8359193017155677693L;

	/**
	 * 订单状态
	 */
	private OrderStatusEnum status;

	/**
	 * 0 全部 1 产品名称 2 订单编号
	 */
	private OrderPageEnum queryType;

	private Integer queryTypeCode;

	/**
	 * 查询值
	 */
	private String queryName;

	/**
	 * 创建开始时间
	 */
	private Date createTimeSta;

	/**
	 * 创建结束时间
	 */
	private Date createTimeEnd;

	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;
}
