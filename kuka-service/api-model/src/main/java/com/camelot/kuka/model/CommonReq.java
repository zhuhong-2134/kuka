package com.camelot.kuka.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [公用的查询]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class CommonReq implements Serializable {

	private static final long serialVersionUID = 2612686947209943697L;

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("名称")
	private Long name;
}
