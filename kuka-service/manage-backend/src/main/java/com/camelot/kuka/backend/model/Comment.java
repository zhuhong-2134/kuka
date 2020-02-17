package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.backend.order.resp.OrderDetailedResp;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.comment.CommentStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [评论实体]</p>
 * Created on 2020/1/20
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 7453145544967314214L;
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
    private CommentStatusEnum status;

    /**
     * 审核时间
     */
    private Date adoptTime;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 头像
     */
    private String photoUrl;

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

    /**********************************/
    /*****以下字段,不是数据库字段******/
    /**********************************/

    /**
     * 评论图片
     */
    private String[] commentUrls;

    /**
     * 订单明细
     */
    private List<OrderDetailedResp> detaileList;

    /**
     * 总额
     */
    private Double sunPrice;
}
