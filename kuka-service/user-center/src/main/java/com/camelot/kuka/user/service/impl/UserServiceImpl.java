package com.camelot.kuka.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserPageReq;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.user.dao.UserDao;
import com.camelot.kuka.user.model.User;
import com.camelot.kuka.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [类功能描述]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override public LoginAppUser findByUsername(String username) {
		return null;
	}

	@Override public List<User> pageList(UserPageReq req) {
        req.setDelState(0);
		return userDao.findList(req);
	}

	@Override
	public Result addUser(UserReq req) {
		if (null == req) {
			return Result.error("参数不能为空");
		}
		if (StringUtils.isBlank(req.getUserName())) {
			return Result.error("姓名不能为空");
		}
		if (null == req.getSex()) {
			return Result.error("性别不能为空");
		}
		if (StringUtils.isBlank(req.getPhone())) {
			return Result.error("姓名不能为空");
		}
		if (StringUtils.isBlank(req.getMail())) {
			return Result.error("邮箱不能为空");
		}
		User user = BeanUtil.copyBean(req, User.class);
		// 固定参数
        user.setCreateBy("admin");
        user.setCreateTime(new Date());
        user.setDelState(0);
        try {
            int con = userDao.addUser(Arrays.asList(user));
            if (con == 0) {
                return Result.error("新增失败");
            }
            return Result.success();
        } catch (Exception e) {
            log.error("\n 新增用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("新增失败");
	}

    @Override
    public Result updateUser(UserReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        try {
            User user = BeanUtil.copyBean(req, User.class);
            // 固定参数
            user.setUpdateBy("admin");
            user.setUpdateTime(new Date());
            int con = userDao.updateUser(user);
            if (con == 0) {
                return Result.error("修改失败");
            }
            return Result.success();
        } catch (Exception e) {
            log.error("\n 修改用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("修改失败");
    }

    @Override
    public Result delUser(CommonReq req) {
	    if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        User user = new User();
        user.setId(req.getId());
        user.setDelState(1);
        user.setUpdateBy("admin");
        user.setUpdateTime(new Date());
        try {
            int con = userDao.delUser(user);
            if (con == 0) {
                return Result.error("删除失败");
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n 删除用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("删除失败");
    }
}
