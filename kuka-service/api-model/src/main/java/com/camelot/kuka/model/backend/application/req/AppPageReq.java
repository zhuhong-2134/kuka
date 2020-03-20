package com.camelot.kuka.model.backend.application.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.backend.ApplicationPageEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [应用查询实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class AppPageReq implements Serializable {

	private static final long serialVersionUID = -8359193017155677693L;

	/**
	 * 分类
	 */
	private AppTypeEnum classType;

	/**
	 * 审核状态
	 */
	private AppStatusEnum appStatus;

	/**
	 * 0 名称 1 ID 2 交易数
	 */
	private ApplicationPageEnum queryType;

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

	/**
	 * 当前登录人标识
	 */
	private String loginName;

	/**
	 * 登录人拥有的集成商ID
	 */
	private Long[] supplierIds;

	/**
	 * 前台传入的集成商
	 */
	private Long supplierId;
}
