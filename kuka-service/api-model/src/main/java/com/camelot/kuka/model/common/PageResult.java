package com.camelot.kuka.model.common;

import lombok.Data;

/**
 * <p>Description: [分页返回结果集]</p>
 * Created on 2017年12月11日
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0
 * Copyright (c) 2017 北京柯莱特科技有限公司
 */
@Data
public class PageResult<T> extends Result<T> {
    private long total;
    public PageResult(){
        super();
    }
    public PageResult(Integer code,String msg){
        super();
        this.code = code;
        this.msg = msg;
    }
    public static <T> PageResult<T> error(String msg){
        return new PageResult<T>(-1,msg);
    }

    public static <T> PageResult<T> success(T result,long total){
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setTotal(total);
        pageResult.setData(result);
        return pageResult;
    }


}
