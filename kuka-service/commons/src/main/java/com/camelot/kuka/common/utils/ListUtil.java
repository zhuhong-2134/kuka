package com.camelot.kuka.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created on 2018年1月5日
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0 Copyright (c) 2018 北京柯莱特科技有限公司
 */
public class ListUtil {
	/**
	 *
	 * <p>
	 * Discription:[将一个list均分成n个list,主要通过偏移量来实现的 ]
	 * </p>
	 * Created on 2018年1月5日
	 *
	 * @param source
	 * @param n
	 * @return
	 * @author:[崔春松]
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = source.size() % n; // (先计算出余数)
		int number = source.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}

	/**
	 *
	 * <p>
	 * Discription:[将一个list按照指定的大小拆分]
	 * </p>
	 * Created on 2018年1月5日
	 *
	 * @param list
	 *            要拆分的集合
	 * @param size
	 *            指定的大小
	 * @return
	 * @author:[崔春松]
	 */
	public static <T> List<List<T>> getSplitList(List<T> list, int size) {
		List<List<T>> returnList = new ArrayList<List<T>>();
		int listSize = list.size();
		int num = listSize % size == 0 ? listSize / size : (listSize / size + 1);
		int start = 0;
		int end = 0;
		for (int i = 1; i <= num; i++) {
			start = (i - 1) * size;
			end = i * size > listSize ? listSize : i * size;
			returnList.add(list.subList(start, end));
		}
		return returnList;
	}

	/**
	 * <p>
	 * Discription:[获取list元素的某个字段值，组成数组返回]
	 * </p>
	 * Created on 2018年3月12日
	 *
	 * @param list
	 * @param column
	 * @return
	 * @author:[宁晓强]
	 */
	public static <T> String[] toArray(List<T> list, String column) {
		try {
			if (list == null || list.isEmpty()) {
				return new String[0];
			}
			if (StringUtils.isEmpty(column)) {
				return null;
			}
			int len = list.size();
			List<String> result = new ArrayList<>(len);
			String methodName = "get" + column.substring(0, 1).toUpperCase() + column.substring(1);
			for (int i = 0; i < len; i++) {
				Method method = list.get(i).getClass().getMethod(methodName, new Class[0]);
				Object obj = method.invoke(list.get(i), new Object[0]);
				if (obj != null) {
					result.add(String.valueOf(obj));
				}
			}
			return result.toArray(new String[0]);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

	/**
	 * splitAry方法<br>
	 *
	 * @param ary
	 *            要分割的数组
	 * @param subSize
	 *            分割的块大小
	 * @return
	 *
	 */
	private static Object[] splitAry(int[] ary, int subSize) {
		int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;
		List<List<Integer>> subAryList = new ArrayList<List<Integer>>();
		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<Integer> list = new ArrayList<Integer>();
			int j = 0;
			while (j < subSize && index < ary.length) {
				list.add(ary[index++]);
				j++;
			}
			subAryList.add(list);
		}
		Object[] subAry = new Object[subAryList.size()];
		for (int i = 0; i < subAryList.size(); i++) {
			List<Integer> subList = subAryList.get(i);
			int[] subAryItem = new int[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j] = subList.get(j).intValue();
			}
			subAry[i] = subAryItem;
		}
		return subAry;
	}

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
				continue;
			}

			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	public static Map<String, Object> objectToMap(Object obj) {
		if(obj == null){
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		try{
			Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}
		}catch (Exception e){

		}
		return map;
	}
	/**
	* <p>Description:[String数据转Long数组]</p>
	* Created on
	 * @param stringArray String数组
	* @return LONG数组
	* @author 崔春松
	*/
	public static Long[] StringArray2LongArray(String[] stringArray)
	{
		List<Long> list=new ArrayList<>();
		for (String str: stringArray) {
			try {
				list.add(Long.parseLong(str));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		Long[] longArray=list.toArray(new Long[list.size()]);
		return longArray;
	}
	/**
	  * 分页
	 * @author 陈贵兵
	 */
	public static <S> List<S> listLimit(List<S> list,Integer pageNo,Integer pageSize){
		List<S> list2=new ArrayList<>();
		if (list == null || list.size() == 0) {
			 return list2;
		}
		int totalCount = list.size();
		pageNo = pageNo - 1;
		int fromIndex = pageNo * pageSize;
		if(fromIndex>=totalCount) {
			return list2;
		}
		int toIndex = ((pageNo + 1) * pageSize);
		if (toIndex > totalCount) {
			toIndex = totalCount;
		}
		list2= list.subList(fromIndex, toIndex);
		return list2;
	}
	/**
	 * @author 陈贵兵
	 */
	public static boolean isEmpty(List<?> list) {
		return list==null||list.size()==0;
	}
	/**
	 * @author 陈贵兵
	 */
	public static boolean isNotEmpty(List<?> list) {
		return list!=null&&list.size()>0;
	}
	/**
	 * 返回左侧有右侧无的数据（注意：不去重）
	 * @author 陈贵兵
	 */
	public static <E> List<E> leftDiff(List<E> left,List<E> right){
		if (isEmpty(left)||isEmpty(right)) {
			return left;
		}
		List<E> leftDiff=new ArrayList<E>();
		for (E l : left) {
			if (l==null) {
				continue;
			}
			boolean exists=false;
			for (E r : right) {
				if (l.equals(r)) {
					exists=true;
					break;
				}
			}
			if (!exists) {
				leftDiff.add(l);
			}
		}
		return leftDiff;
	}
	
}
