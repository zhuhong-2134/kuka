package com.camelot.kuka.model.backend.comment.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.comment.CommentPageEnum;
import com.camelot.kuka.model.enums.comment.CommentStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [评论所有字段请求实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class CommentPageReq implements Serializable {

	private static final long serialVersionUID = -8359193017155677693L;

	/**
	 * 审核状态:0:待审核;1:审核通过;2:审核不通过
	 */
	private CommentStatusEnum status;

	/**
	 * 评论分类
	 */
	private Integer commentType;

	/**
	 * 0 全部 1 产品名称 2 订单编号 3 ID
	 */
	private CommentPageEnum queryType;

	private Integer queryTypeCode;

	/**
	 * 查询值
	 */
	private String queryName;

	/**
	 * 审核开始时间
	 */
	private Date adoptTimeSta;

	/**
	 * 审核结束时间
	 */
	private Date adoptTimeEnd;

	/**
	 * 删除标识0:未删除;1已删除
	 */
	private DeleteEnum delState;
}
