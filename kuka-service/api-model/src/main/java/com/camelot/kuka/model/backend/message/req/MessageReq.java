package com.camelot.kuka.model.backend.message.req;

import com.camelot.kuka.model.enums.backend.JumpStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  消息管理
 * @author xienan 2020-02-20
 */
@Data
public class MessageReq implements Serializable {


    private static final long serialVersionUID = -1047591320613706973L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息类型:0:订单;1:应用;2:集成商
     */
    private MessageTypeEnum type;

    /**
     * 来源id 订单或应用或集成商的id
     */
    private Long sourceId;

    /**
     * 消息状态 0:未读;1已读
     */
    private MessageStatusEnum status;

    /**
     * 接受人ID
     */
    private Long userId;

    /**
     * 跳转状态
     */
    private JumpStatusEnum jumpStatus;

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
