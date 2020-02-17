package com.camelot.kuka.model.user.resp;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.SexEnum;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [用户所有字段返回实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class UserResp implements Serializable {

	private static final long serialVersionUID = 425407306134549430L;
	/**
	 * 主键不能为空
	 */
	private Long id;

	/**
	 * 登陆名
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 头像地址
	 */
	private String photoUrl;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 性别:0:男; 1:女
	 */
	private SexEnum sex;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String mail;

	/**
	 * 0:注册;1后台出创建
	 */
	private CreateSourceEnum source;

	/**
	 * 省名称
	 */
	private String provinceName;

	/**
	 * 省编码
	 */
	private String provinceCode;

	/**
	 * 市名称
	 */
	private String cityName;

	/**
	 * 市编码
	 */
	private String cityCode;

	/**
	 * 区名称
	 */
	private String districtName;

	/**
	 * 区编码
	 */
	private String districtCode;

	/**
	 * 地址json
	 */
	private String addressJson;

	/**
	 * 结构化地址
	 */
	private String address;

	/**
	 * 公司名称
	 */
	private String company;

	/**
	 * 待确定
	 */
	private Long type;

	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;

	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 修改时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 修改人
	 */
	private String updateBy;
}
