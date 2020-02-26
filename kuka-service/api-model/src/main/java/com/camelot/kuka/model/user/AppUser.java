package com.camelot.kuka.model.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AppUser implements Serializable {

	private static final long serialVersionUID = 611197991672067628L;

	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String headImgUrl;
	private String phone;
	private Integer sex;

	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 头像地址
	 */
	private String photoUrl;
	/**
	 * 状态
	 */
	private Boolean enabled;
	private String type;
	private Date createTime;
	private Date updateTime;

}
