package com.camelot.kuka.user.service;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserPageReq;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import com.camelot.kuka.user.model.User;

import java.util.List;

/**
 * <p>Description: [用户信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface UserService {

	/***
	 * <p>Description:[通过用户名获取用户对象]</p>
	 * Created on 2020/1/19
	 * @param username
	 * @return com.camelot.kuka.model.user.LoginAppUser
	 * @author 谢楠
	 */
	LoginAppUser findByUsername(String username);

	/***
	 * <p>Description:[分页查询]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return List
	 * @author 谢楠
	 */
	List<User> pageList(UserPageReq req);

	/***
	 * <p>Description:[新增用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 * @author 谢楠
	 */
	Result addUser(UserReq req, String loginUserName);

	/**
	 * 获取单条数据
	 * @param req
	 * @return
	 */
	Result<UserResp> queryById(CommonReq req);

	/***
	 * <p>Description:[修改用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 * @author 谢楠
	 */
	Result updateUser(UserReq req, String loginUserName);

	/***
	 * <p>Description:[删除用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 * @author 谢楠
	 */
	Result delUser(CommonReq req, String loginUserName);
}
