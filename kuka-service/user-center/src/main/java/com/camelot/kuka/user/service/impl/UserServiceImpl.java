package com.camelot.kuka.user.service.impl;

import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import com.camelot.kuka.user.dao.UserDao;
import com.camelot.kuka.user.model.User;
import com.camelot.kuka.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [类功能描述]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override public LoginAppUser findByUsername(String username) {
		return null;
	}

	@Override public List<User> pageList(UserReq req) {
		User user = BeanUtil.copyBean(req, User.class);
		return userDao.findList(user);
	}
}
