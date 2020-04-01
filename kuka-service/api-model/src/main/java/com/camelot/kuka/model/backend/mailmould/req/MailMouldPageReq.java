package com.camelot.kuka.model.backend.mailmould.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.mailmould.MailTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [邮件请求实体]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class MailMouldPageReq implements Serializable {


	private static final long serialVersionUID = 161749265238270660L;

	/**
	 * 发送方式
	 */
	private MailTypeEnum type;

	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;
}
