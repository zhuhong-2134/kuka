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
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface UserService {

	/***
	 * <p>Description:[通过用户名获取用户对象]</p>
	 * Created on 2020/1/19
	 * @param username
	 * @return com.camelot.kuka.model.user.LoginAppUser
	 *
	 */
	LoginAppUser findByUsername(String username);

	/***
	 * <p>Description:[库卡用户分页查询]</p>
	 * Created on 2020/1/19
	 * @param req
	 * @return com.camelot.kuka.model.user.LoginAppUser
	 * @author req
	 */
	List<User> kukaPageList(UserPageReq req);

	/***
	 * <p>Description:[分页查询]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return List
	 *
	 */
	List<User> pageList(UserPageReq req);

	/***
	 * <p>Description:[新增用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 *
	 */
	Result addUser(UserReq req, String loginUserName);

	/***
	 * <p>Description:[kuka后台-新增用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 *
	 */
	Result kukaAddUser(UserReq req, String loginUserName);

	/***
	 * <p>Description:[注册-用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 *
	 */
	Result visitorAddUser(UserReq req);

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
	 *
	 */
	Result updateUser(UserReq req, String loginUserName);

	/***
	 * <p>Description:[删除用户]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 *
	 */
	Result delUser(CommonReq req, String loginUserName);

	/***
	 * <p>Description:[修改密码]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return Result
	 *
	 */
	Result updatePassWord(UserReq req, String loginUserName);

	/***
	 * <p>Description:[发送验证码]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
    Result sendMail(UserReq req);

	/***
	 * <p>Description:[修改密码]</p>
	 * Created on 2020/2/4
	 * @param req
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
    Result passwordByMail(UserReq req);

	/***
	 * <p>Description:[获取来发展所有数据]</p>
	 * Created on 2020/1/20
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
	Result<List<UserResp>> queryByType();

	/***
	 * <p>Description:[根据用户名获取]</p>
	 * Created on 2020/1/20
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
    Result<UserResp> queryByUserName(String userName);

	/***
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	Result<UserResp> queryByName(String name);

	/***
	 * <p>Description:[根据用户名获取]</p>
	 * Created on 2020/1/20
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
	Result<List<UserResp>> queryByIds(Long[] ids);

	/***
	 * <p>Description:[邮箱或手机号查询]</p>
	 * Created on 2020/1/20
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
	Result<UserResp> phoneOrMali(UserReq req);

	/***
	 * <p>Description:[新增集成商用户]</p>
	 * Created on 2020/1/20
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
	Result<Long> suppilerAddUser(UserReq req);

	/***
	 * <p>Description:[获取列表数据]</p>
	 * Created on 2020/1/20
	 * @return com.camelot.kuka.model.common.PageResult
	 *
	 */
    Result<List<UserResp>> queryByInfo(UserReq userReq);
}
