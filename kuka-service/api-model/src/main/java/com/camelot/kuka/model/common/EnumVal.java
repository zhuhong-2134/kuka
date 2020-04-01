package com.camelot.kuka.model.common;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.model.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumVal implements Serializable{
	
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 6839998524176020342L;

	private int code;
	
	private String des;
	
	private String name;
	
	private boolean selected;
	
	public static List<EnumVal> getEnumList(Class<?> clazz) {
		return getEnumList(clazz, true);
	}
	
	public static List<EnumVal> getEnumList(Class<?> clazz, boolean isSelected) {
		List<EnumVal> list = new ArrayList<EnumVal>();
		int index = 0;
		for (Object obj : clazz.getEnumConstants()) {
			EnumVal model = new EnumVal();
			model.setCode(((BaseEnum) obj).getCode());
			model.setDes(((BaseEnum) obj).getDescription());
			model.setName(obj.toString());
			if (isSelected && index == 0) {
				model.setSelected(true);
				index++;
			}
			list.add(model);
		}
		return list;
	}
	
	public static List<EnumVal> getEnumList(Class<?> clazz, boolean isSelected, Integer... codes) {
		List<EnumVal> list = new ArrayList<EnumVal>();
		int index = 0;
		for (Object obj : clazz.getEnumConstants()) {
			if(!Arrays.asList(codes).contains(((BaseEnum) obj).getCode())){
				continue;
			}
			EnumVal model = new EnumVal();
			model.setCode(((BaseEnum) obj).getCode());
			model.setDes(((BaseEnum) obj).getDescription());
			model.setName(obj.toString());
			if (isSelected && index == 0) {
				model.setSelected(true);
				index++;
			}
			list.add(model);
		}
		return list;
	}

	public static String getDescription(List<EnumVal> list,Integer code){
		String description="";
		if(list.isEmpty()||code==null){
			return description;
		}
		for(EnumVal enumVal:list){
			if(code.equals(enumVal.getCode())){
				description= enumVal.getDes();
				break;
			}
		}
		return description;
	}

	public static Integer getCode(List<EnumVal> list,String name){
		Integer code=null;
		if(list.isEmpty()||StringUtils.isBlank(name)){
			return code;
		}
		for(EnumVal enumVal:list){
			if(name.equals(enumVal.getName())){
				code= enumVal.getCode();
				break;
			}
		}
		return code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * <p>Discription:[转换成json]</p>
	 * Created on 2016年10月28日
	 * 
	 * @param clz
	 * @param select (是否选中第一个 )
	 * @return
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(Class clz, boolean select) {
		List<EnumVal> l = new ArrayList<EnumVal>();
		int i = 0;
		for (Object obj : clz.getEnumConstants()) {
			EnumVal ev = new EnumVal();
			ev.setCode(((BaseEnum) obj).getCode());
			ev.setDes(((BaseEnum) obj).getDescription());
			ev.setName(obj.toString());
			if (i == 0) {
				if (select) {
					ev.setSelected(true);
					i++;
				}
			}
			l.add(ev);
		}
		return getEnumVal(l);
	}

	public static String getEnumVal(List<EnumVal> list) {
		return JSON.toJSONString(list);
	}

	@Override
	public String toString() {
		return "EnumVal [code=" + code + ", des=" + des + ", name=" + name + ", selected=" + selected + "]";
	}

}
