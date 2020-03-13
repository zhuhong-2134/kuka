package com.camelot.kuka.model.backend.message.resp;

import com.camelot.kuka.model.enums.backend.MessageStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  消息管理
 * @author xienan 2020-02-20
 */
@Data
public class MessageResp implements Serializable {

    private static final long serialVersionUID = -2746156133992810570L;
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
