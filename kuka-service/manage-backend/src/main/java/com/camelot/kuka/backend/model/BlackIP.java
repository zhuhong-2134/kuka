package com.camelot.kuka.backend.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * IP黑名单
 * 
 * @author 崔春松
 *
 */
@Data
public class BlackIP implements Serializable {

	private static final long serialVersionUID = -2064187103535072261L;

	private String ip;
	private Date createTime;
}
