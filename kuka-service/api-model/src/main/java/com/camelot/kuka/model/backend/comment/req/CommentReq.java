package com.camelot.kuka.model.backend.comment.req;

import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [评论实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class CommentReq implements Serializable {


    private static final long serialVersionUID = -922471688578296978L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 产品id
     */
    private Long appId;

    /**
     * 评论说明
     */
    private String commentDesc;

    /**
     * 评论图片
     */
    private String commentUrl;

    /**
     * 评论分类--待定
     */
    private Integer commentType;

    /**
     * 审核状态:0:待审核;1:审核通过;2:审核不通过
     */
    private Integer status;

    /**
     * 审核时间
     */
    private DeleteEnum adoptTime;

    /**
     * 删除标识0:未删除;1已删除
     */
    private DeleteEnum delState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;
}
