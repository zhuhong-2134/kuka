package com.camelot.kuka.common.utils;


import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: [对象操作类]</p>
 * Created on 2019/11/20
 * @author <a href="mailto: hexiaobo@camelotchina.com">贺小波</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 **/
public abstract class BeanUtil {

    /**
     * <p>Description:[单个对象相同的属性复制]</p>
     * Created on 2019/11/20
     * @param objSource 源对象
     * @param clazz     目标class
     * @return T
     * @author 贺小波
     */
    public static <T> T copyProperties(Object objSource, Class<T> clazz) {
        if (null == objSource) {
            return null;
        }
        try {
            T objDes = clazz.newInstance();
            Field[] fields = objSource.getClass().getDeclaredFields();
            Field[] fieldsSup = objSource.getClass().getSuperclass().getDeclaredFields();
            Method[] method = clazz.getMethods();
            if (null != fields) {
                BeanUtil.assignment(objSource, clazz, objDes, fields, method);
            }
            if (null != fieldsSup) {
                BeanUtil.assignment(objSource, clazz, objDes, fieldsSup, method);
            }
            return objDes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Description:[单个对象指定属性复制]</p>
     * Created on 2019/11/20
     * @param objSource    源对象
     * @param clazz        目标class
     * @param attributeMap 属性指定关系MAP-若为空默认为同名属性
     * @return T
     * @author 贺小波
     */
    public static <T> T copyProperties(Object objSource, Class<T> clazz, Map<String, String> attributeMap) {
        if (attributeMap == null || attributeMap.size() == 0) {
            return BeanUtil.copyProperties(objSource, clazz);
        }
        if (objSource == null) {
            return null;
        }
        try {
            T objDes = clazz.newInstance();
            Field[] fields = objSource.getClass().getDeclaredFields();
            Field[] fieldsSup = objSource.getClass().getSuperclass().getDeclaredFields();
            Method[] method = clazz.getMethods();
            if (null != fields) {
                BeanUtil.assignment(objSource, clazz, objDes, fields, method, attributeMap);
            }
            if (null != fieldsSup) {
                BeanUtil.assignment(objSource, clazz, objDes, fieldsSup, method, attributeMap);
            }
            return objDes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Description:[单个对象相同的属性复制]</p>
     * Created on
     * @param objSource 源对象
     * @param clazz     目标对象
     * @return 转换结果
     * @author 崔春松
     */
    public static <T> T copyBean(Object objSource, Class<T> clazz) {
        if (null == objSource) {
            return null;
        }
        try {
            T objDes = clazz.newInstance();
            BeanCopier beanCopier = BeanCopier.create(objSource.getClass(), clazz, false);
            beanCopier.copy(objSource, objDes, null);
            return objDes;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Description:[List集合对象相同的属性复制]</p>
     * Created on 2019/11/20
     * @param objSource 源list
     * @param clazz     目标class
     * @return java.util.List<T>
     * @author 贺小波
     */
    public static <T, E> List<T> copyList(List<E> objSource, Class<T> clazz) {
        if (null == objSource) {
            return null;
        }
        List<T> target = new ArrayList<>();
        for (E souece : objSource) {
            target.add(copyProperties(souece, clazz));
        }
        return target;
    }

    /**
     * <p>Description:[List集合对象指定属性复制]</p>
     * Created on 2019/11/20
     * @param objSource    源list
     * @param clazz        目标class
     * @param attributeMap 属性指定关系MAP-若为空默认为同名属性
     * @return java.util.List<T>
     * @author 贺小波
     */
    public static <T, E> List<T> copyList(List<E> objSource, Class<T> clazz, Map<String, String> attributeMap) {
        if (attributeMap == null || attributeMap.size() == 0) {
            return BeanUtil.copyList(objSource, clazz);
        }
        if (objSource == null) {
            return null;
        }
        List<T> target = new ArrayList<>();
        for (E souece : objSource) {
            target.add(BeanUtil.copyProperties(souece, clazz, attributeMap));
        }
        return target;
    }

    /**
     * <p>Description:[List集合对象相同的属性复制]</p>
     * Created on
     * @param objSource 源list
     * @param clazz     目标list
     * @return 转换结果
     * @author 崔春松
     */
    public static <T, E> List<T> copyBeanList(List<E> objSource, Class<T> clazz) {
        if (null == objSource) {
            return null;
        }
        List<T> target = new ArrayList<T>();
        for (E souece : objSource) {
            target.add(copyBean(souece, clazz));
        }
        return target;
    }

    /**
     * <p>Description:[赋值]</p>
     * Created on 2019/11/20
     * @param objSource    源
     * @param clazz        目标class
     * @param objDes       目标
     * @param fields       属性集
     * @param method       方法集
     * @param attributeMap
     * @return void
     * @author 贺小波
     */
    private static <T> void assignment(Object objSource, Class clazz, T objDes, Field[] fields, Method[] method, Map<String, String> attributeMap) {
        Map<String, String> filterMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            String attributeName = field.getName();
            String appointAttributeName = attributeMap.get(attributeName);
            if (!BeanUtil.isnull(filterMap.get(attributeName))) {
                continue;
            }
            if (BeanUtil.isnull(appointAttributeName)) {
                appointAttributeName = attributeName;
            } else {
                BeanUtil.assignment(method, attributeName, attributeName, field, clazz, objSource, objDes);
                filterMap.put(appointAttributeName, appointAttributeName);
            }
            BeanUtil.assignment(method, attributeName, appointAttributeName, field, clazz, objSource, objDes);
        }
    }

    /**
     * <p>Description:[赋值]</p>
     * Created on 2019/11/20
     * @param method               方法集
     * @param attributeName        属性
     * @param appointAttributeName 属性
     * @param field                属性对象
     * @param clazz                目标class
     * @param objSource            源
     * @param objDes               目标
     * @return void
     * @author 贺小波
     */
    private static <T> void assignment(Method[] method, String attributeName, String appointAttributeName, Field field, Class clazz, Object objSource, T objDes) {
        try {
            if (checkMethod(method, attributeName)) {
                String setMethodName = "set" + caseInitials(appointAttributeName);
                if ("class java.math.BigDecimal".equals(field.getType().toString())) {
                    Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});
                    if (null != field.get(objSource)) {
                        setMethod.invoke(objDes, new BigDecimal(field.get(objSource) + "").setScale(4, BigDecimal.ROUND_HALF_UP));
                    }
                } else {
                    Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});
                    setMethod.invoke(objDes, field.get(objSource));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Description:[赋值]</p>
     * Created on 2019/9/20
     * @param objSource 源
     * @param clazz     目标class
     * @param objDes    目标
     * @param fields    属性集
     * @param method    方法集
     * @return void
     * @author 贺小波
     */
    private static <T> void assignment(Object objSource, Class clazz, T objDes, Field[] fields, Method[] method) {
        for (Field field : fields) {
            field.setAccessible(true);
            BeanUtil.assignment(method, field.getName(), field.getName(), field, clazz, objSource, objDes);
        }
    }

    /**
     * <p>Description:[校验属性是否有set方法]</p>
     * Created on 2019/9/20
     * @param method    方法集
     * @param attribute 属性
     * @return boolean
     * @author 贺小波
     */
    private static boolean checkMethod(Method[] method, String attribute) {
        boolean flag = false;
        if (null == method || null == attribute) {
            return false;
        }
        for (Method md : method) {
            if ("set".equals(md.getName().substring(0, 3))) {
                if (caseInitials(attribute).equals(md.getName().substring(3, md.getName().length()))) {
                    flag = true;
                    continue;
                }
            }
        }
        return flag;
    }

    /**
     * <p>Description:[首字母变大写]</p>
     * Created on 2019/9/20
     * @param attribute 目标字符串
     * @return java.lang.String
     * @author 贺小波
     */
    private static String caseInitials(String attribute) {
        if (null == attribute) {
            return null;
        }
        char[] cs = attribute.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * <p>Description:[判断字符串是否为空]</p>
     * Created on 2019/11/20
     * @param value 目标字符串
     * @return boolean
     * @author 贺小波
     */
    private static boolean isnull(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象是否为空，且对象的所有属性都为空
     * ps: boolean类型会有默认值false 判断结果不会为null 会影响判断结果
     * 序列化的默认值也会影响判断结果
     * @param object
     * @return
     */
    public static boolean objCheckIsNull(Object object) {
        Class clazz = object.getClass(); // 得到类对象
        Field[] fields = clazz.getDeclaredFields(); // 得到所有属性
        boolean flag = true; //定义返回结果，默认为true
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object); //得到属性值
                String fieldName = field.getName(); // 得到属性名
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue != null) {  //只要有一个属性值不为null 就返回false 表示对象不为null
                flag = false;
                break;
            }
        }
        return flag;
    }

}
