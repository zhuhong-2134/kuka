package com.camelot.kuka.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: [List转换成树]</p>
 * 必须符合如下条件
 * 主键字段为id
 * 父类ID为Pid
 * 子类为childred
 * Created on 2019/12/9 
 * @author  <a href="mailto: cuichunsong@camelotchina.com">崔春松 </a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */

public class List2Tree<T> {
	/**
	 * 获取节点ID
	 */
	private Long getKey(T node) {
		try {
			Method method = node.getClass().getMethod("getId");
			Long id = (Long)method.invoke(node);
			return id;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return  null;
	};

	/**
	 * 获取节点父节点ID
	 */
	private Long getParentId(T node){
		try {
			Method method = node.getClass().getMethod("getPid");
			Long pid = (Long)method.invoke(node);
			return pid;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	};

	/**
	 * 获取子节点
	 */
	private List<T> getChildrens(T node){
		try {
			Method method = node.getClass().getMethod("getChildren");
			List<T> list = (List<T>)method.invoke(node);
			return list;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置子节点
	 */
	private void setChildrens(List<T> nodes,T node){
		try {
			Method method = node.getClass().getMethod("setChildren",Class.forName("java.util.List"));
			method.invoke(node,nodes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成树方法
	 */
	public List<T> listToTree(List<T> oldList) {
		Map<Long,Object> newMap = new HashMap<>();
		List<T> newList = new ArrayList<>();
		for (T tree : oldList){
			newMap.put(getKey(tree),tree);
		}
		for (T tree : oldList){
			T parent = (T) newMap.get(getParentId(tree));
			if (parent != null){
				if (getChildrens(parent) == null){
					List<T> ch = new ArrayList<>();
					ch.add(tree);
					setChildrens(ch,parent);
				}else {
					List<T> ch = getChildrens(parent);
					ch.add(tree);
					setChildrens(ch,parent);
				}
			}else {
				newList.add(tree);
			}
		}
		return newList;
	}
}
