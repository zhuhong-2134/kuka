package com.camelot.kuka.backend.model;

import java.io.Serializable;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.mailmould.MailTypeEnum;
import lombok.Data;
import java.util.Date;

/**
 *  邮件模板
 *    2020-02-17
 */
@Data
public class MailMould implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板名称
     */
    private String mouldName;

    /**
     * 模板编码
     */
    private String mouldCode;

    /**
     * 发送方式1:邮件;2站内信 3,全部
     */
    private MailTypeEnum type;
    private String typeStr;

    /**
     * 消息内容
     */
    private String message;

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

    public MailMould() {
    }

}
