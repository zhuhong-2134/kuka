package com.camelot.kuka.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.common.utils.RandomNumberUtils;
import com.camelot.kuka.common.utils.RedisStringUtils;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.MailReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserPageReq;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import com.camelot.kuka.user.dao.RoleDao;
import com.camelot.kuka.user.dao.UserDao;
import com.camelot.kuka.user.feign.MailMouldClient;
import com.camelot.kuka.user.model.Role;
import com.camelot.kuka.user.model.User;
import com.camelot.kuka.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.applet.Main;

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
    @Resource
    private CodeGenerateUtil codeGenerateUtil;
    @Resource
    private RoleDao roleDao;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private MailMouldClient mailMouldClient;
    @Resource
    private RedisStringUtils redisStringUtils;

	@Override public LoginAppUser findByUsername(String username) {
        User user = userDao.queryLongUser(username);
        LoginAppUser loginAppUser = BeanUtil.copyBean(user, LoginAppUser.class);
        return loginAppUser;
	}

    @Override
    public List<User> kukaPageList(UserPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<User> list = userDao.kukaPageList(req);
        list.forEach(user -> {
            JSONObject addressJson = formatAddress(user);
            user.setAddressJson(addressJson.toJSONString());
        });
        // 放入角色名称
        setRoleName(list);
        return list;
    }



    @Override public List<User> pageList(UserPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<User> list = userDao.findList(req);
        list.forEach(user -> {
            JSONObject addressJson = formatAddress(user);
            user.setAddressJson(addressJson.toJSONString());
        });
        return list;
	}

	@Override
	public Result addUser(UserReq req, String loginUserName) {
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
			return Result.error("手机号不能为空");
		}
		if (StringUtils.isBlank(req.getMail())) {
			return Result.error("邮箱不能为空");
		}
		User user = BeanUtil.copyBean(req, User.class);
        Long id = codeGenerateUtil.generateId(PrincipalEnum.USER_USER);
        user.setId(id);
        // 固定参数
        user.setCreateBy(loginUserName);
        user.setCreateTime(new Date());
        user.setDelState(DeleteEnum.NO);
        // 密码加密
        if (StringUtils.isNoneBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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
	public Result kukaAddUser(UserReq req, String loginUserName) {
		if (null == req) {
			return Result.error("参数不能为空");
		}
		if (StringUtils.isBlank(req.getUserName())) {
			return Result.error("用户名称不能为空");
		}
        if (StringUtils.isBlank(req.getPassword())) {
            return Result.error("密码不能为空");
        }
        if (StringUtils.isBlank(req.getPhone())) {
            return Result.error("手机号不能为空");
        }
		if (null == req.getRoleId()) {
			return Result.error("角色不能为空");
		}
		User user = BeanUtil.copyBean(req, User.class);
        Long id = codeGenerateUtil.generateId(PrincipalEnum.USER_USER);
        user.setId(id);
        // 固定参数
        user.setCreateBy(loginUserName);
        user.setCreateTime(new Date());
        user.setDelState(DeleteEnum.NO);
        // 密码加密
        if (StringUtils.isNoneBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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
    public Result visitorAddUser(UserReq req) {
        if (StringUtils.isBlank(req.getMail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(req.getPassword())) {
            return Result.error("密码不能为空");
        }
        User user = BeanUtil.copyBean(req, User.class);
        Long id = codeGenerateUtil.generateId(PrincipalEnum.USER_USER);
        user.setId(id);
        // 固定参数
        user.setCreateBy(req.getUserName());
        user.setCreateTime(new Date());
        user.setDelState(DeleteEnum.NO);
        user.setType(UserTypeEnum.VISITORS);
        user.setUserName(req.getUserName());
        // 密码加密
        if (StringUtils.isNoneBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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
    public Result<UserResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        User user = BeanUtil.copyBean(req, User.class);
        user.setDelState(DeleteEnum.NO);
        try {
            User info = userDao.queryById(user);
            if (null == info) {
                return Result.error("数据获取失败,刷新后重试");
            }
            JSONObject address = formatAddress(info);
            info.setAddressJson(address.toJSONString());
            return Result.success(BeanUtil.copyBean(info, UserResp.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n 获取用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("数据获取失败");
    }

    @Override
    public Result updateUser(UserReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        try {
            User user = BeanUtil.copyBean(req, User.class);
            // 固定参数
            user.setUpdateBy(loginUserName);
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
    public Result delUser(CommonReq req, String loginUserName) {
	    if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        User user = new User();
        user.setId(req.getId());
        user.setDelState(DeleteEnum.YES);
        user.setUpdateBy(loginUserName);
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

    @Override
    public Result updatePassWord(UserReq req, String loginUserName) {
	    // 获取原有数据
        User user = BeanUtil.copyBean(req, User.class);
        user.setDelState(DeleteEnum.NO);
        user.setUserName(loginUserName);
        User info = userDao.queryById(user);
        if (null == info) {
            return Result.error("获取用户信息失败");
        }
        // 对比原始密码是否相同
        if (!passwordEncoder.matches(req.getOldPassword(), info.getPassword())) {
            return Result.error("旧密码错误");
        }
        // 新密码加密
        String encode = passwordEncoder.encode(req.getPassword());
        User update = new User();
        update.setId(info.getId());
        update.setPassword(encode);
        update.setUpdateTime(new Date());
        update.setUpdateBy(loginUserName);
        int con = userDao.updateUser(update);
        if (0 == con) {
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @Override
    public Result sendMail(UserReq req) {
	    if (null == req || StringUtils.isBlank(req.getMail())) {
            return Result.error("邮箱不能为空");
        }
        // 邮箱是否注册
        User user = BeanUtil.copyBean(req, User.class);
        user.setDelState(DeleteEnum.NO);
        user.setMail(req.getMail());
        User info = userDao.queryById(user);
        if (null == info) {
            return Result.error("邮箱未注册");
        }
        MailReq mailReq = new MailReq();
        mailReq.setMail(req.getMail());
        mailReq.setTitle("kuka验证码");

        String code = RandomNumberUtils.randomSixCode();
        mailReq.setMessage("您的验证码是：" + code + "  （请勿将验证码告知他人）");

        // 放入缓存
        redisStringUtils.set("login:mail:code:" + req.getMail(), code, 60 * 5 );

        return mailMouldClient.sendMail(mailReq);
    }

    /**
     *  格式化地址信息
     * @param user
     * @return
     */
    private JSONObject formatAddress(User user) {
        // 区
        JSONObject qu = new JSONObject();
        qu.put("label", user.getDistrictName());
        qu.put("value", user.getDistrictCode());
        // 市
        JSONObject shi = new JSONObject();
        shi.put("label", user.getCityName());
        shi.put("value", user.getCityCode());
        shi.put("children", qu);
        // 省
        JSONObject shen = new JSONObject();
        shen.put("label", user.getProvinceName());
        shen.put("value", user.getProvinceCode());
        shen.put("children", shi);
        // 总
        JSONObject zong = new JSONObject();
        zong.put("options", shen);
        return  zong;
    }

    /**
     * 放入角色名称
     * @param list
     */
    private void setRoleName(List<User> list) {
        // 获取角色
        List<Role> roleList = roleDao.findList(new Role());
        for (User user : list) {
            if (null == user.getRoleId()) {
                continue;
            }
            for (Role role : roleList) {
                if (role.getId().compareTo(user.getRoleId()) == 0) {
                    user.setRoleName(role.getRoleName());
                    break;
                }
            }
        }
    }

}
