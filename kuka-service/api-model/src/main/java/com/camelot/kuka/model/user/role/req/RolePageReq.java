package com.camelot.kuka.model.user.role.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.user.role.RolePageEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [角色查询实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class RolePageReq implements Serializable {

	private static final long serialVersionUID = -8359193017155677693L;

	/**
	 * 0 全部 1 名称 2 ID
	 */
	private RolePageEnum queryType;

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
}
