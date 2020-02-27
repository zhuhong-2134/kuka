package com.camelot.kuka.model.backend.home.req;

import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.backend.IndustryTypeEnum;
import com.camelot.kuka.model.enums.backend.SkilledAppEnum;
import com.camelot.kuka.model.enums.backend.SuppliePageEnum;
import com.camelot.kuka.model.enums.home.HomeQueryEnum;
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
public class HomeSupplierPageReq implements Serializable {

	private static final long serialVersionUID = -8359193017155677693L;

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
	 * 行业
	 */
	private IndustryTypeEnum industry;
	private Integer industryCode;

	/**
	 * 应用
	 */
	private SkilledAppEnum appType;
	private Integer appTypeCode;

	/**
	 * 省编码
	 */
	private String provinceCode;

	/**
	 * 市编码
	 */
	private String cityCode;

	/**
	 * 区编码
	 */
	private String districtCode;

	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;
}
