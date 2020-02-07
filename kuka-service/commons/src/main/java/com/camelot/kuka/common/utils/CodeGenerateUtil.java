package com.camelot.kuka.common.utils;


import com.camelot.kuka.model.enums.PrincipalEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Description: [编码生成工具类]</p>
 * Created on 2020年02月07日
 * @author <a href="mailto: hexiaobo@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
@Component
public class CodeGenerateUtil {

    @Autowired
    private RedisStringUtils redisStringUtils;

    public CodeGenerateUtil() {}

    /**
     * Description: [生成ID自增]
     * @param pre 表名枚举-不能为空
     * @return: java.lang.Long
     * Created on 2019年08月08日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public Long generateId(PrincipalEnum pre) {
        if (null == pre) {
            return null;
        }
        return redisStringUtils.incr(pre.getKey());
    }

}
