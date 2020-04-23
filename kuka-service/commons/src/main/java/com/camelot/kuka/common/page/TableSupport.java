package com.camelot.kuka.common.page;

import com.camelot.kuka.common.utils.Constants;
import com.camelot.kuka.common.utils.ServletUtils;
import com.camelot.kuka.common.utils.StringUtils;
import com.camelot.kuka.model.common.PageDomain;


/**
 * 表格数据处理
 * 
 *
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();

        if (null != ServletUtils.getParameterToInt(Constants.PAGE_NUM)) {
            pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        }
        if (null != ServletUtils.getParameterToInt(Constants.PAGE_SIZE)) {
            pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        }
        if (StringUtils.isNoneBlank(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN))) {
            String orderByColumn =  ServletUtils.getParameter(Constants.ORDER_BY_COLUMN);
            // 驼峰转下划线
            orderByColumn = StringUtils.toUnderScoreCase(orderByColumn);
            pageDomain.setOrderByColumn(orderByColumn);
        }
        if (StringUtils.isNoneBlank(ServletUtils.getParameter(Constants.IS_ASC))) {
            pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        }
        return pageDomain;
    }


    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

}
