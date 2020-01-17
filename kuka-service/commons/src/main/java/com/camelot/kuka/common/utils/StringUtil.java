/**
 * 
 */
package com.camelot.kuka.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description
 * @author    陈贵兵
 * @Date      2019年12月26日
 */
public class StringUtil {
	/**
	 * http改为https
	 * @author 陈贵兵
	 */
	public static String httpToHttps(String url) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}
		String http="http://";
		String https="https://";
		url=url.replace(http, https);
		return url;
	}
}
