package com.camelot.kuka.backend.model;

import lombok.Data;

import java.io.Serializable;

/**
 *  通用产品表
 * @author xienan 2020-02-07
 */
@Data
public class ApplicationCurrency implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 应用主键
     */
    private String appId;
}
