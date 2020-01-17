package com.camelot.kuka.common.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

/**
 * 分页创建类
 */
@SuppressWarnings("unused")
public class PageBuilder<T> implements Serializable {

    private static final long serialVersionUID = 6668574519472364218L;

    private static final long DEFAULT_SIZE = 20L;

    /** 分页--当前页 */
    private Long pageNo;

    /** 分页--每页数量 */
    @Getter
    private Long pageSize;

    /** 分页--总量 */
    @Setter
    private Long pageCount;

    public PageBuilder(Long pageNo, Long pageSize) {
        this.pageNo = Optional.ofNullable(pageNo).filter(no -> no != 0L).orElse(1L);
        this.pageSize = Optional.ofNullable(pageSize).orElse(DEFAULT_SIZE);
    }

    public long getStartIndex() {
        return (pageNo - 1) * pageSize;
    }

}
