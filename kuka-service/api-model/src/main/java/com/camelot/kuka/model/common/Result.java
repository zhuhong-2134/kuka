package com.camelot.kuka.model.common;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: [返回结果集]</p>
 * Created on 2017年12月11日
 * 
 *
 * @version 1.0 
 * Copyright (c) 2017 北京柯莱特科技有限公司
 */
@Data
public class Result<T> implements Serializable {
	private static final long serialVersionUID = -1686054524991742104L;
	protected Integer code = 200;
	protected String msg = "success";
	protected T data;

	protected Map<String, List<EnumVal>> enumVal = new HashMap<>();

	public Result() {
		super();
	}

	public Result(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public static <T> Result<T> error(String msg) {
		return new Result<T>(-1, msg);
	}

	public static <T> Result<T> error(String msg, T data){
		Result<T> result = new Result<T>(-1, msg);
		if (null != result) {
			result.setData(data);
		}
		return result;
	}

	public static <T> Result<T> error(Integer code, String msg, T data){
		Result<T> result = new Result<T>(code, msg);
		if (null != result) {
			result.setData(data);
		}
		return result;
	}

	public static <T> Result<T> success() {
		return success(null, null);
	}

	public static <T> Result<T> success(String msg) {
		return success(msg, null);
	}

	public static <T> Result<T> success(T data) {
		return success(null, data);
	}

	public static <T> Result<T> success(String msg, T data) {
		Result<T> result = new Result<T>();
		if (StringUtils.isNotBlank(msg)) {
			result.setMsg(msg);
			result.setCode(200);
		}
		if (null != result) {
			result.setData(data);
		}
		return result;
	}
	//判断结果集是否返回成功
	public boolean isSuccess(){
		return this.code == 200;
	}

	/**
	 * Description: [添加枚举]
	 * @param key 枚举key
	 * @param list 枚举
	 * @return: void
	 * Created on 2019年08月20日
	 * @version 1.0
	 * Copyright (c) 2019 北京柯莱特科技有限公司
	 **/
	public void putEnumVal(String key, List<EnumVal> list) {
		enumVal.put(key, list);
	}

}
