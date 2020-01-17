package com.camelot.kuka.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>Description: [异常处理工具类]</p>
 * Created on 2019/9/25
 * @author <a href="mailto: hexiaobo@camelotchina.com">贺小波</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class ExceptionUtil {

    /**
     * <p>Description:[异常转换字符串]</p>
     * Created on 2019/9/25
     * @param e 异常信息
     * @return java.lang.String
     * @author 贺小波
     */
    public static String getExceptionToString(Exception e) {
        if (e == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * <p>Description:[工具类禁止实例化]</p>
     * Created on 2020/1/8
     * @param
     * @return
     * @author 贺小波
     */
    private ExceptionUtil(){
        super();
    }

}
