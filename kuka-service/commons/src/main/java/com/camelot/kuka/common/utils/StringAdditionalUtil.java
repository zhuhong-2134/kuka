package com.camelot.kuka.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串附加工具类
 *
 */
public class StringAdditionalUtil {

    /**
     * 下划线转驼峰法(默认小驼峰)
     *
     * @param underScore 下划线格式字符串
     * @return 驼峰格式字符串
     */
    public static String underScore2CamelCase(String underScore) {
        if (StringUtils.isBlank(underScore)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(underScore);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if(matcher.start()==0){
                sb.append(Character.toLowerCase(word.charAt(0)));
            }else{
                sb.append(Character.toUpperCase(word.charAt(0)));
            }
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰模式转换成下划线模式
     * @param camelCase 驼峰模式的字符串
     * @return String
     */
    public static String camelCase2UnderScore(String camelCase) {
        // 入参为空字符串时，直接返回
        if (StringUtils.isBlank(camelCase)) {
            return "";
        }
        camelCase = String.valueOf(camelCase.charAt(0)).toUpperCase()
            .concat(camelCase.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(camelCase);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == camelCase.length() ? "" : "_");
        }
        return sb.toString();
    }
}
