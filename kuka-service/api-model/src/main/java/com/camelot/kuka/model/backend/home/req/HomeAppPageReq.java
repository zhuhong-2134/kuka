package com.camelot.kuka.model.backend.home.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.application.AppStatusEnum;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.home.HomeQueryEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [应用查询实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class HomeAppPageReq implements Serializable {


	private static final long serialVersionUID = 5219194047516952089L;

	/**
	 * 集成商的ID
	 */
	private Long supplierId;

	/**
	 * 分类
	 */
	private AppTypeEnum classType;

	/**
	 * 查询分类
	 */
	private HomeQueryEnum queryType;
	private Integer queryTypeCode;

	/**
	 * 查询值
	 */
	private String queryName;


	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;
	/**
	 * 审核状态
	 */
	private AppStatusEnum appStatus;

}
