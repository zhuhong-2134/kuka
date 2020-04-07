package com.camelot.kuka.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: [map工具类]</p >
 * Created on 2019年08月30日
 *
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class MapUtils {

    /**
     * Description: [实体类转Map]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * Created on 2019年08月30日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Description: [Map转实体类]
     * @param map 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return: T
     * Created on 2019年08月30日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for(Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
