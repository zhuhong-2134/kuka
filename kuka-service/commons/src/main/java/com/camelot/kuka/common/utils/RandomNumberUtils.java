package com.camelot.kuka.common.utils;

import java.util.Random;

/**
 * <p>
 * Description: [随机数生成]
 * </p>
 * Created on 2019-09-05
 *
 *
 * @version 1.0 Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class RandomNumberUtils {

    /**
     * 随机生成指定位数
     * @param length 生成长度
     * @return 如果 length <=0 则返回， length > 0 返回指定长度随机数
     */
    public static String randomCode(int length) {
        if (length <= 0) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 默认生成六位随机数
     * @return 返回六位随机数
     */
    public static String randomSixCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
