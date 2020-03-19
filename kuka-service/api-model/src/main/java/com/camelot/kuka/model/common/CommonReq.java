package com.camelot.kuka.model.common;

import com.camelot.kuka.model.enums.user.WhetherEnum;
import lombok.Data;

/**
 * <p>Description: [通用请求实体]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Data
public class CommonReq {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String queryName;

    /**
     * 是否标识
     */
    private WhetherEnum status;

    /**
     * 主键s
     */
    private Long[] ids;

    /**
     * 用户主键
     */
    private Long userId;
}
