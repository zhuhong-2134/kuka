package com.camelot.kuka.common.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: []</p>
 * Created on 2019年7月17日
 * @author  <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public interface BaseDAO<T> {
	
	/**
	 * <p>Discription:[根据条件进行分页查询]</p>
	 * Created on 2019年7月17日
	 * @param params
	 * @return
	 * @author:[崔春松]
	 */
	public List<T> queryList(Map<String, Object> params);
	
	/**
	 * <p>Discription:[新增]</p>
	 * Created on 2019年7月17日
	 * @param t
	 * @author:[崔春松]
	 */
	public Integer add(@Param("entity") T t);

	/**
	 * <p>Discription:[根据ID查询]</p>
	 * Created on 2019年7月17日
	 * @param id
	 * @return
	 * @author:[崔春松]
	 */
	public T queryById(Object id);

	/**
	 * <p>
	 * Discription:[根据ID和路由Id查询]
	 * </p>
	 * Created on 2019年7月17日
	 * @param t
	 * @return
	 * @author:[ ]
	 */
	public T queryByIdAndRoutingId(@Param("entity") T t);

	/**
	 * <p>Discription:[修改]</p>
	 * Created on2019年7月17日
	 * @param t
	 * @return
	 * @author:[崔春松]
	 */
	public Integer update(@Param("entity") T t);

	/**
	 * <p>Discription:[根据查询条件修改]</p>
	 * Created on 2019年7月17日
	 * @param t
	 * @return
	 * @author:[崔春松]
	 */
	public Integer updateBySelect(@Param("entity") T t);

	/**
	 * <p>Discription:[根据ID删除数据]</p>
	 * Created on 2019年7月17日
	 * @param params
	 * @return
	 * @author:[崔春松]
	 */
	public Integer delete(Map<String, Object> params);

	/**
	 * <p>Discription:[根据ID和路由id删除数据]</p>
	 * Created on 2019年7月17日
	 * @param t
	 * @return
	 * @author:[崔春松]
	 */
	public Integer deleteByIdAndRoutingId(@Param("entity") T t);

	/**
	 * <p>Discription:[失效或者起效]</p>
	 * Created on 2019年7月17日
	 * @param params
	 * @return
	 * @author:[崔春松]
	 */
	public Integer disableOrEnable(Map<String, Object> params);

}
