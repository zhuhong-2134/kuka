package com.camelot.kuka.model.backend.applicationrequest.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.AppRequestPageEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [应用请求查询实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class AppRequestPageReq implements Serializable {

	private static final long serialVersionUID = 6225342464205309139L;
	/**
	 * 0 全部 1 用户名 2 ID 3 应用名称
	 */
	private AppRequestPageEnum queryType;
	private Integer queryTypeCode;

	/**
	 * 查询值
	 */
	private String queryName;

	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;
}
