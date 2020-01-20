package com.camelot.kuka.common.controller;

import com.camelot.kuka.common.page.PageDomain;
import com.camelot.kuka.common.page.TableDataInfo;
import com.camelot.kuka.common.page.TableSupport;
import com.camelot.kuka.common.sql.SqlUtil;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [基础Controller]</p>
 * Created on 2019年08月08日
 * @author <a href="mailto: hexiaobo@camelotchina.com">xienan</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class BaseController {

    /**
     * Description: [前台时间类型传值yyyy-MM-dd HH:mm:ss格式字符串]
     * @param binder
     * @param request
     * @return: void
     * Created on 2019年08月08日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // CustomDateEditor为自定义日期编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }



    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (null != pageNum && null != pageSize)
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected static <T, E>  PageResult<List<T>> getPage(List<E> list, Class<T> clazz){
        long total = new PageInfo(list).getTotal();
        return PageResult.success(BeanUtil.copyList(list, clazz), total);
    }
}
