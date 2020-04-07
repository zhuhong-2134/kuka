package com.camelot.kuka.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2019-09-05
 *
 *
 * @version 1.0 Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class NumberUtil {

    /**
     * 默认保留两位
     * @param num 值
     * @return
     */
    public static Double format(Double num){
        if (null == num) {
            return num;
        }
        BigDecimal b = new BigDecimal(num);
        num = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return num;
    }

    /**
     *
     * @param num 值
     * @param format 保留位数
     * @return
     */
    public static Double format(Double num, int format){
        if (null == num) {
            return num;
        }
        BigDecimal b = new BigDecimal(num);
        num = b.setScale(format, BigDecimal.ROUND_HALF_UP).doubleValue();
        return num;
    }
}
