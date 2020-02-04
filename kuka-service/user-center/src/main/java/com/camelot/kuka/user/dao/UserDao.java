package com.camelot.kuka.user.dao;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.req.UserPageReq;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [用户DAO]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface UserDao {

	/***
	 * <p>Description:[通过用户名获取用户对象]</p>
	 * Created on 2020/1/19
	 * @param user
	 * @return List<User>
	 * @author 谢楠
	 */
	List<User> findList(@Param("entity")UserPageReq user);

	/***
	 * <p>Description:[新增用户]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 * @author 谢楠
	 */
	int addUser(@Param("list") List<User> user);

	/***
	 * <p>Description:[修改用户]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 * @author 谢楠
	 */
	int updateUser(User user);

	/***
	 * <p>Description:[删除用户]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 * @author 谢楠
	 */
	int delUser(User user);

}
