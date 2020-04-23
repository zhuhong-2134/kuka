package com.camelot.kuka.model.common;

import lombok.Data;

/**
 * 分页数据
 */
@Data
public class PageDomain {
    /**
     * 当前记录起始索引
     */
    private Integer pageNum = 1;
    /**
     * 每页显示记录数
     */
    private Integer pageSize = 10;
    /**
     * 排序列 默认id
     */
    private String orderByColumn = "id";
    /**
     * 排序的方向 "desc" 或者 "asc"
     * 默认 desc
     */
    private String isAsc = "desc";
}
