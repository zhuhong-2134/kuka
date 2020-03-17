package com.camelot.kuka.model.backend.supplier.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.SuppliePageEnum;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [集成商所有字段请求实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class SupplierPageReq implements Serializable {

	private static final long serialVersionUID = -8359193017155677693L;

	/**
	 * 0 全部 1 姓名 2 id
	 */
	private SuppliePageEnum queryType;

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
	 * 当前登录人标识
	 */
	private String loginName;

	/**
	 * 登录人拥有的集成商ID
	 */
	private Long[] supplierIds;

	/**
	 * 0:注册;1后台出创建
	 */
	private CreateSourceEnum source;
}
