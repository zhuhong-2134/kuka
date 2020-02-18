package com.camelot.kuka.model.backend.mailmould.resp;

import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.mailmould.MailTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>Description: [消息模板请求返回所有字段]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class MailMouldResp implements Serializable {
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
     * 发送方式1:邮件;2站内信 3,都发送
     */
    private MailTypeEnum type;

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
}
