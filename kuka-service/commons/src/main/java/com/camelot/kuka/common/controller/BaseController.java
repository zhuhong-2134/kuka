package com.camelot.kuka.common.controller;

import com.camelot.kuka.common.page.TableSupport;
import com.camelot.kuka.common.sql.SqlUtil;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.StringUtils;
import com.camelot.kuka.model.common.PageDomain;
import com.camelot.kuka.model.common.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [基础Controller]</p>
 * Created on 2019年08月08日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class BaseController {

    /**
     * Description: [前台时间类型传值yyyy-MM-dd HH:mm:ss格式字符串]
     * @param binder
     * @return: void
     * Created on 2019年08月08日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // CustomDateEditor为自定义日期编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }



    /**
     * 设置请求分页数据 (表单提交的方式分页)
     * 请求参数无需继承 pageDomain
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderByColumn());
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "id";
        }
        PageHelper.startPage(pageNum, pageSize, orderBy + " " + pageDomain.getIsAsc());
    }


    /**
     * 设置请求分页数据 (json提交的方式分页)
     * 请求参数需要继承 pageDomain
     * 排序默认 id desc
     */
    protected void startPage(Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize, "id desc");
    }

    /**
     * 设置请求分页数据 (json提交的方式分页)
     * 请求参数需要继承 pageDomain
     * 排序
     */
    protected void startPage(Integer pageNum, Integer pageSize, String orderByColumn, String isAsc)
    {
        // 驼峰转下划线
        String orderBy = SqlUtil.escapeOrderBySql(StringUtils.toUnderScoreCase(orderByColumn));
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "id";
        }
        PageHelper.startPage(pageNum, pageSize, orderBy + " " + isAsc);
    }

    /**
     * 响应请求分页数据
     * list 直接获取到list, 原始的, copy到新的对象 总条数不起作用
     * clazz 返回前段的数据
     */
    protected static <T, E>  PageResult<List<T>> getPage(List<E> list, Class<T> clazz){
        long total = new PageInfo(list).getTotal();
        return PageResult.success(BeanUtil.copyList(list, clazz), total);
    }
}
