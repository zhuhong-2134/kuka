package com.camelot.kuka.model.user.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [用户所有字段请求实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class UserPageReq implements Serializable {

	private static final long serialVersionUID = 6165940253000108020L;

	/**
	 * 用户来源
	 * 0:注册;1后台出创建
	 */
	private Integer source;

	/**
	 * 0 全部 1 手机号 2 姓名 3 ID
	 */
	private Integer queryType;

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
	private Integer delState;
}
