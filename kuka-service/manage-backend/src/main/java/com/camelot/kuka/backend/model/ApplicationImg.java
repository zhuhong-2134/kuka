package com.camelot.kuka.backend.model;

import com.camelot.kuka.model.enums.application.AppImgTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 *  应用图片表
 *   2020-02-07
 */
@Data
public class ApplicationImg implements Serializable {

    private static final long serialVersionUID = -8074317397320676314L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 应用主键
     */
    private Long appId;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片类型;0:封面图;1:营业执照
     */
    private AppImgTypeEnum type;
}
