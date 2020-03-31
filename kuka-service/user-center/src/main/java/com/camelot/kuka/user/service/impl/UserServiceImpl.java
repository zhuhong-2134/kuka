package com.camelot.kuka.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.common.utils.*;
import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.MailReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.user.CreateSourceEnum;
import com.camelot.kuka.model.enums.user.UserStatusEnum;
import com.camelot.kuka.model.enums.user.UserTypeEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.req.UserPageReq;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import com.camelot.kuka.user.dao.RoleDao;
import com.camelot.kuka.user.dao.UserDao;
import com.camelot.kuka.user.feign.MailMouldClient;
import com.camelot.kuka.user.feign.MangeBackenCilent;
import com.camelot.kuka.user.model.Role;
import com.camelot.kuka.user.model.User;
import com.camelot.kuka.user.service.AddressService;
import com.camelot.kuka.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private MangeBackenCilent mangeBackenCilent;
    @Resource
    private AddressService addressService;

	@Override public LoginAppUser findByUsername(String username) {
        User user = userDao.queryLongUser(username);
        if (null != user && null != user.getStatus() && UserStatusEnum.SHUT == user.getStatus()) {
            return null;
        }
        LoginAppUser loginAppUser = BeanUtil.copyBean(user, LoginAppUser.class);
        return loginAppUser;
	}

    @Override
    public List<User> kukaPageList(UserPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<User> list = userDao.kukaPageList(req);
        // 放入角色名称
        setRoleName(list);
        return list;
    }



    @Override public List<User> pageList(UserPageReq req) {
        req.setDelState(DeleteEnum.NO);
        // 这个默认展示的是来访者
        if (null == req.getType()) {
            req.setType(UserTypeEnum.VISITORS);
        }
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<User> list = userDao.findList(req);
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
        user.setName(user.getUserName());
        // 固定参数
        user.setCreateBy(loginUserName);
        user.setCreateTime(new Date());
        user.setDelState(DeleteEnum.NO);
        if(user.getType()==null){//如果用户类型为空则设置为来访者
            user.setType(UserTypeEnum.VISITORS);
        }
        if(user.getSource()==null){//如果用户来源为空设置为后台创建
            user.setSource(CreateSourceEnum.BACKSTAGE);
        }
        // 默认的用户头像
        if (StringUtils.isBlank(user.getPhotoUrl())) {
            user.setPhotoUrl("http://www.kukaplus.com/static/imgs/zhuce.jpg");
        }
        // 查看用户是否已经存在
        int check = userDao.checkUser(user);
        if (check > 0) {
            return Result.error("邮箱或手机号已被绑定");
        }
        // 获取地址名称
        setAddressName(user);
        // 密码加密
        String md5Pwd = MD5Util.MD("m" + req.getPhone());
        user.setPassword(passwordEncoder.encode(md5Pwd));
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
        user.setName(user.getUserName());
        user.setStatus(UserStatusEnum.OPEN);
        // 默认的用户头像
        if (StringUtils.isBlank(user.getPhotoUrl())) {
            user.setPhotoUrl("http://www.kukaplus.com/static/imgs/zhuce.jpg");
        }
        // 获取地址名称
        setAddressName(user);
        // 查看用户是否已经存在
        int check = userDao.checkUser(user);
        if (check > 0) {
            return Result.error("手机号或邮箱已被绑定");
        }

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
        if (StringUtils.isBlank(req.getUuid())) {
            return Result.error("uuid不能为空");
        }
        if (StringUtils.isBlank(req.getCode())) {
            return Result.error("验证码不能为空");
        }
        // 校验验证码
        String redisCode = redisStringUtils.get("login:code:" + req.getUuid());
        if (StringUtils.isBlank(redisCode)) {
            return Result.error("验证码过期");
        }
        if (!req.getCode().toUpperCase().equals(redisCode)) {
            return Result.error("验证码错误");
        }
        if (null == req.getType()) {
            return Result.error("注册类型不能为空");
        }
        User user = BeanUtil.copyBean(req, User.class);
        Long id = codeGenerateUtil.generateId(PrincipalEnum.USER_USER);
        user.setId(id);
        // 固定参数
        user.setCreateBy(req.getUserName());
        user.setCreateTime(new Date());
        user.setDelState(DeleteEnum.NO);
        user.setType(req.getType());
        // 默认的用户头像
        if (StringUtils.isBlank(user.getPhotoUrl())) {
            user.setPhotoUrl("http://www.kukaplus.com/static/imgs/zhuce.jpg");
        }
        if (StringUtils.isBlank(req.getUserName())) {
            user.setUserName(req.getMail());
        } else {
            user.setUserName(req.getUserName());
        }
        user.setName(user.getUserName());
        user.setSource(CreateSourceEnum.REGISTER);
        // 根据来源分配角色
        if (req.getType() == UserTypeEnum.VISITORS) {
            user.setRoleId(3L);
        }
        if (req.getType() == UserTypeEnum.KUKA) {
            user.setRoleId(1L);
        }
        if (req.getType() == UserTypeEnum.SUPPILER) {
            user.setRoleId(2L);
        }

        // 查看用户是否已经存在
        int check = userDao.checkUser(user);
        if (check > 0) {
            return Result.error("邮箱被绑定");
        }
        // 获取地址名称
        setAddressName(user);
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
            UserResp userResp = BeanUtil.copyBean(info, UserResp.class);
            // 获取当前用户绑定的集成商
            if (info.getType() == UserTypeEnum.SUPPILER) {
                Result<SupplierResp> supplierRespResult = mangeBackenCilent.queryByCreateName(info.getUserName());
                if (supplierRespResult.isSuccess() && null != supplierRespResult.getData()) {
                    userResp.setSupplierId(supplierRespResult.getData().getId());
                }
            }
            return Result.success(userResp);
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
            // 获取地址名称
            setAddressName(user);
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

    @Override
    public Result passwordByMail(UserReq req) {
        String code = req.getCode();
        // 校验验证码是否正确
        if (StringUtils.isBlank(code)) {
            return Result.error("验证码不能为空");
        }
        code = code.toUpperCase();
        if (StringUtils.isBlank(req.getMail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(req.getPassword())) {
            return Result.error("密码不能为空");
        }
        // 获取缓存中的验证码
        String redisCode = redisStringUtils.get("login:mail:code:" + req.getMail());
        if (StringUtils.isBlank(redisCode)) {
            return Result.error("验证码过期");
        }
        if (!code.equals(redisCode)) {
            return Result.error("验证码错误");
        }
        // 邮箱是否注册
        User user = BeanUtil.copyBean(req, User.class);
        user.setDelState(DeleteEnum.NO);
        user.setMail(req.getMail());
        User info = userDao.queryById(user);
        if (null == info) {
            return Result.error("邮箱未注册");
        }
        info.setPassword(passwordEncoder.encode(req.getPassword()));
        int con = userDao.updateUser(info);
        if (0 == con) {
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @Override
    public Result<List<UserResp>> queryByType() {
        UserPageReq user = new UserPageReq();
        user.setDelState(DeleteEnum.NO);
        user.setType(UserTypeEnum.VISITORS);
        List<User> list = userDao.findList(user);
        list.forEach(us -> {
            // 格式化地址
            StringBuffer stringBuffer = new StringBuffer();
            if (StringUtils.isNoneBlank(us.getProvinceName())) {
                stringBuffer.append(us.getProvinceName());
            }
            if (StringUtils.isNoneBlank(us.getCityName())) {
                stringBuffer.append(us.getCityName());
            }
            if (StringUtils.isNoneBlank(us.getDistrictName())) {
                stringBuffer.append(us.getDistrictName());
            }
            us.setAddress(stringBuffer.toString());
        });
        return Result.success(BeanUtil.copyBeanList(list, UserResp.class));
    }

    @Override
    public Result<UserResp> queryByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return Result.error("用户名不能为空");
        }
        User user = new User();
        user.setUserName(userName);
        user.setDelState(DeleteEnum.NO);
        try {
            User info = userDao.queryById(user);
            if (null == info) {
                return Result.error("数据获取失败,刷新后重试");
            }
            UserResp userResp = BeanUtil.copyBean(info, UserResp.class);
            // 获取当前用户绑定的集成商
            if (info.getType() == UserTypeEnum.SUPPILER) {
                Result<SupplierResp> supplierRespResult = mangeBackenCilent.queryByCreateName(info.getUserName());
                if (supplierRespResult.isSuccess() && null != supplierRespResult.getData()) {
                    userResp.setSupplierId(supplierRespResult.getData().getId());
                }
            }
            return Result.success(userResp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n 获取用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(userName), e);
        }
        return Result.error("数据获取失败");
    }


    @Override
    public Result<UserResp> queryByName(String name) {
        if (StringUtils.isBlank(name)) {
            return Result.error("名称不能为空");
        }
        User user = new User();
        user.setName(name);
        user.setDelState(DeleteEnum.NO);
        try {
            User info = userDao.queryById(user);
            if (null == info) {
                return Result.error("数据获取失败,刷新后重试");
            }
            UserResp userResp = BeanUtil.copyBean(info, UserResp.class);
            // 获取当前用户绑定的集成商
            if (info.getType() == UserTypeEnum.SUPPILER) {
                Result<SupplierResp> supplierRespResult = mangeBackenCilent.queryByCreateName(info.getUserName());
                if (supplierRespResult.isSuccess() && null != supplierRespResult.getData()) {
                    userResp.setSupplierId(supplierRespResult.getData().getId());
                }
            }
            return Result.success(userResp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("\n 获取用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(name), e);
        }
        return Result.error("数据获取失败");
    }

    @Override
    public Result<List<UserResp>> queryByIds(Long[] ids) {
        List<User> users = userDao.queryByIds(ids);
        return Result.success(BeanUtil.copyBeanList(users, UserResp.class));
    }

    @Override
    public Result<UserResp> phoneOrMali(UserReq req) {
        User query = BeanUtil.copyBean(req, User.class);
        query.setDelState(DeleteEnum.NO);
        List<User> list = userDao.phoneOrMali(query);
        if (null != list && !list.isEmpty()) {
            return Result.success(BeanUtil.copyBean(list.get(0), UserResp.class));
        }
        return Result.success();
    }

    @Override
    public Result<Long> suppilerAddUser(UserReq req) {
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
            return Result.success(id);
        } catch (Exception e) {
            log.error("\n 新增用户失败, 参数:{}, \n 错误信息:{}", JSON.toJSON(req), e);
        }
        return Result.error("新增失败");
    }

    @Override
    public Result<List<UserResp>> queryByInfo(UserReq userReq) {
        userReq.setDelState(DeleteEnum.NO);
        List<User> users = userDao.queryByInfo(userReq);
        return Result.success(BeanUtil.copyBeanList(users, UserResp.class));
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

    /**
     * 通过地址编码获取地址名称
     * @param user
     */
    private void setAddressName(User user) {
        List<String> codes = new ArrayList<>();
        if (StringUtils.isNoneBlank(user.getProvinceCode())) {
            codes.add(user.getProvinceCode());
        }
        if (StringUtils.isNoneBlank(user.getCityCode())) {
            codes.add(user.getCityCode());
        }
        if (StringUtils.isNoneBlank(user.getDistrictCode())) {
            codes.add(user.getDistrictCode());
        }
        if (codes.isEmpty()) {
            return;
        }
        Result<Map<String, String>> mapResult = addressService.queryAddressMap(codes);
        if (!mapResult.isSuccess()) {
            log.error("/n 通过地址编码转换地址名称失败， 参数：{}", JSON.toJSONString(codes));
            return;
        }
        Map<String, String> map = mapResult.getData();
        user.setProvinceName(map.get(user.getProvinceCode()));
        user.setCityName(map.get(user.getCityCode()));
        user.setDistrictName(map.get(user.getDistrictCode()));
    }
}
