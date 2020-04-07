package com.camelot.kuka.common.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: [枚举工具类]</p>
 * Created on 2019年08月20日
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class EnumUtil {

    /**默认枚举类属性*/
    private final static String[] ATTRIBUTE_NAME_ARR = {"code", "description"};

    /**
     * Description: [将Enum枚举转换成JSON字符串]
     * @param clazz 枚举class
     * @param attributeNames 枚举属性，可不填，默认枚举属性为[code,description]
     * @return: java.lang.String 处理后的JSON字符串
     * Created on 2019年08月20日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static String getEnumJson(Class clazz, String ... attributeNames) {
        if (!clazz.isEnum()) {
            return null;
        }
        try {
            String[] attributeNameArr = (attributeNames != null && attributeNames.length > 0) ? attributeNames : ATTRIBUTE_NAME_ARR;
            StringBuilder jsonBud = new StringBuilder();
            jsonBud.append("[");
            List enumList = Arrays.asList(clazz.getEnumConstants());
            int size = enumList.size();
            for (int i = 0; i < size; i ++) {
                jsonBud.append("{");
                Object enumObj = enumList.get(i);
                jsonBud.append("\"").append("enumValue").append("\":").append("\"").append(enumObj).append("\",");
                int length = attributeNameArr.length;
                for (int j = 0; j < length; j ++) {
                    String attributeName = attributeNameArr[j];
                    String getMethodName = "get" + caseInitials(attributeName);
                    Method method = clazz.getMethod(getMethodName);
                    boolean flag = "class java.lang.String".equals(method.getReturnType().toString());
                    Object obj = method.invoke(enumObj);
                    jsonBud.append("\"").append(attributeName).append("\":");
                    if (flag) {
                        jsonBud.append("\"").append(obj).append("\"");
                    } else {
                        jsonBud.append(obj);
                    }
                    if (j < length - 1) {
                        jsonBud.append(",");
                    }
                }
                jsonBud.append("}");
                if (i < size - 1) {
                    jsonBud.append(",");
                }
            }
            jsonBud.append("]");
            return jsonBud.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Description: [首字母大写]
     * @param attribute 目标字符串
     * @return: java.lang.String
     * Created on 2019年08月20日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    private static String caseInitials(String attribute){
        if (null == attribute) {
            return null;
        }
        char[] cs = attribute.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
