package com.camelot.kuka.common.utils;

import com.fehorizon.commonService.common.constants.SysConstant;
import com.fehorizon.commonService.model.enums.CodeNumGenerateEnum;
import com.fehorizon.commonService.model.enums.PrincipalEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>Description: [编码生成工具类]</p>
 * Created on 2019年08月08日
 * @author <a href="mailto: hexiaobo@camelotchina.com">贺小波</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
@Component
public class CodeGenerateUtil {

    @Autowired
    private RedisStringUtils redisStringUtils;
	DecimalFormat DF_SIX = new DecimalFormat("000000");
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
	 * @param codeNumGenerateEnum 单据枚举
	* @return 新的单号
	* @author 崔春松
	*/
    public String generateNumber(CodeNumGenerateEnum codeNumGenerateEnum){
		if(null == codeNumGenerateEnum){
			return  null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String formatDate = sdf.format(date);
		String redisKye = codeNumGenerateEnum.getKey()+formatDate+ ":" + SysConstant.CODEYMDREDISKEY;
		Long no = redisStringUtils.incr(redisKye);
		redisStringUtils.expire(redisKye, 24 * 60 * 60);
		DecimalFormat df = new DecimalFormat("00000");
		String cNo = df.format(no);
		return codeNumGenerateEnum.getCode() + formatDate + cNo;
	}



    /**
=======
		String newCode = getString(codeNumGenerateEnum);
		return newCode;
	}

	private String getString(CodeNumGenerateEnum codeNumGenerateEnum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String formatDate = sdf.format(date);
		String redisKye = codeNumGenerateEnum.getKey() + ":" + SysConstant.CODEYMDREDISKEY;
		Long no = redisStringUtils.incr(redisKye);
		redisStringUtils.expire(redisKye, DateUtils.getCurrent2TodayEndMillisTime() / SysConstant.ONE_THOUSAND);
		DecimalFormat df = new DecimalFormat("00000");
		String cNo = df.format(no);
		return codeNumGenerateEnum.getCode() + formatDate + cNo;
	}



	/**
>>>>>>> feature_proxy
     * Description: [生成编码-(前缀+时间yyyyMMdd+四位自增码)]
     * @param prefix 编码前缀-不可为空
     * @return: java.lang.String
     * Created on 2019年08月16日
     * @version 1.0
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    public String generateYMDCode(String prefix) {
        if (null == prefix) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String formatDate = sdf.format(date);
        String redisKye = prefix + ":" + SysConstant.CODEYMDREDISKEY;
        Long no = redisStringUtils.incr(redisKye);
        redisStringUtils.expire(redisKye, DateUtils.getCurrent2TodayEndMillisTime() / SysConstant.ONE_THOUSAND);
        DecimalFormat df = new DecimalFormat("0000");
        String cNo = df.format(no);
        return prefix + formatDate + cNo;
    }

	/**
	 * 获取6位自曾编号(SA20191112000001)
	 * @param prefix 编码前缀
	 * @return 编号
	 */
	public String generateCodeYmdSix(String prefix) {
		if (null == prefix) {
			return null;
		}
		LocalDate date = LocalDate.now();
		String formatDate = date.format(DateTimeFormatter.BASIC_ISO_DATE);
		String redisKey = prefix + "_" +  formatDate + "_" +  SysConstant.CODEYMDREDISKEY;
		Long no = redisStringUtils.incr(redisKey);
		redisStringUtils.expire(redisKey, DateUtils.getCurrent2TodayEndMillisTime() / SysConstant.ONE_THOUSAND);
		return prefix + formatDate + DF_SIX.format(no);
	}

}
