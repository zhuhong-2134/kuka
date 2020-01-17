package com.camelot.kuka.common.utils;

import java.util.List;
import java.util.StringJoiner;

/**
 * @Author lizelong01
 * @Description List转换成String工具类
 */
public class ListToStrUtils {

    /**
     * 将List<String>，List<Long>,List<Integer>拼接成字符串
     *
     * @param list list
     * @return 拼接之后的字符串如 123','123 or 123,123
     */
    public static String listToStrNoPrefixSuffix(List list, String spilt) {
        if (null != list && list.size() > 0) {
            StringJoiner joiner = new StringJoiner(spilt);
            for (Object object : list) {
                joiner.add(String.valueOf(object));
            }
            return joiner.toString();
        }
        return null;
    }

    /**
     * 将List<String>，List<Long>,List<Integer>拼接成字符串
     *
     * @param list list
     * @return 拼接之后的字符串如 '123','123' or 123,123
     */
    public static String listToStrPrefixSuffix(List list) {
        if (list != null && list.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (Object object : list) {
                if (object instanceof String) {
                    stringBuffer.append("'").append(object).append("'").append(",");
                } else if (object instanceof Long) {
                    stringBuffer.append(object).append(",");
                } else if (object instanceof Integer) {
                    stringBuffer.append(object).append(",");
                }
            }
            // 删除最后一个","
            stringBuffer.deleteCharAt(stringBuffer.toString().length() - 1);
            return stringBuffer.toString();
        }
        return null;
    }
}
