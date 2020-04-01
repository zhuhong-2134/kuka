package com.camelot.kuka.common.utils;


import com.camelot.kuka.model.enums.PrincipalEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: [编码生成工具类]</p>
 * Created on 2020年02月07日
 *
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

    /**
     * <p>Description:[生成单据编号]</p>
     * Created on
     * @param
     * @return 新的单号
     *
     */
    public String generateNumber(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String formatDate = sdf.format(date);
        String redisKye = "TX" + formatDate + ":" + "SYS:CODE:YMD:REDIS:KEY";
        Long no = redisStringUtils.incr(redisKye);
        redisStringUtils.expire(redisKye, 24 * 60 * 60);
        DecimalFormat df = new DecimalFormat("00000");
        String cNo = df.format(no);
        return "TX" + formatDate + cNo;
    }

}
