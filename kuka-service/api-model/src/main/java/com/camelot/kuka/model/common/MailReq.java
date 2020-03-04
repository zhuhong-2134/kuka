package com.camelot.kuka.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 发送邮件请求
 */
@Data
public class MailReq implements Serializable {

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 标题
     */
    private String title;

    /**
     * 密码
     */
    private String message;
}
