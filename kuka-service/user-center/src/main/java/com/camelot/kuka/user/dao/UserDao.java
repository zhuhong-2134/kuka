package com.camelot.kuka.user.dao;

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
 *
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
	 *
	 */
	List<User> findList(@Param("entity")UserPageReq user);

	/***
	 * <p>Description:[kuka用户分页查询]</p>
	 * Created on 2020/1/19
	 * @param req
	 * @return List<User>
	 *
	 */
	List<User> kukaPageList(@Param("entity") UserPageReq req);

	/***
	 * <p>Description:[新增用户]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 *
	 */
	int addUser(@Param("list") List<User> user);

	/***
	 * <p>Description:[修改用户]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 *
	 */
	int updateUser(User user);

	/***
	 * <p>Description:[删除用户]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 *
	 */
	int delUser(User user);

	/***
	 * <p>Description:[获取单个对象]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 *
	 */
	User queryById(@Param("entity") User user);

	/***
	 * <p>Description:[获取登录对象]</p>
	 * Created on 2020/2/4
	 * @param userName
	 * @return User
	 *
	 */
	User queryLongUser(@Param("userName") String userName);


	/***
	 * <p>Description:[判断用户是否存在]</p>
	 * Created on 2020/2/4
	 * @param user
	 * @return List<User>
	 *
	 */
	int checkUser(@Param("entity") User user);

	/**
	 * 根据ids获取数据
	 * @param ids
	 * @return
	 */
    List<User> queryByIds(@Param("array") Long[] ids);

	/**
	 * 邮箱或手机号查询
	 * @param query
	 * @return
	 */
    List<User> phoneOrMali(@Param("entity") User query);

	/**
	 * 条件查询返回集合
	 * @param userReq
	 * @return
	 */
    List<User> queryByInfo(@Param("entity") UserReq userReq);
}
