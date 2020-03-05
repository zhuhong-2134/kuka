package com.camelot.kuka.user.controller;

import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.common.utils.CodeUtil;
import com.camelot.kuka.common.utils.RedisStringUtils;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

/**
 * <p>Description: [登录信息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "登录API", tags = { "登录接口" })
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Resource
    private RedisStringUtils redisStringUtils;


    /***
     * <p>Description:[新增来访者用户]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/add")
    public Result addUser(UserReq req){
        return userService.visitorAddUser(req);
    }

    /***
     * <p>Description:[修改密码]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/updatePassword")
    public Result updatePassword(UserReq req){
        String loginUserName = AppUserUtil.getLoginUserName();
        return userService.updatePassWord(req, loginUserName);
    }

    /**
     * Description: [获取验证码图片]
     * @return: void
     * Created on 2019年08月26日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    @PostMapping("/login/code")
    protected void verificationCode(UserReq req, HttpServletResponse resp) throws IOException {
        if (null == req || StringUtils.isBlank(req.getUuid())) {
            return;
        }
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();
        // 生成的验证码和验证码图片
        StringBuffer code = (StringBuffer) codeMap.get("code");

        redisStringUtils.set("login:code:" + req.getUuid(), code.toString(), 60 * 3);

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1); resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: [校验验证码]
     * @return: void
     * Created on 2019年08月26日
     * Copyright (c) 2019 北京柯莱特科技有限公司
     **/
    @PostMapping("/login/checkCode")
    public Result<Boolean> checkCode(@RequestBody UserReq req) {
        String code = req.getCode();
        String uuid = req.getUuid();
        if (StringUtils.isBlank(code)) {
            return Result.error("验证码不能为空");
        }
        code = code.toUpperCase();
        if (StringUtils.isBlank(uuid)) {
            return Result.error("uuid不能为空");
        }
        // 获取缓存中的验证码
        String redisCode = redisStringUtils.get("login:code:" + req.getUuid());
        if (StringUtils.isBlank(redisCode)) {
            return Result.error("验证码过期");
        }
        if (!code.equals(redisCode)) {
            return Result.error("验证码错误");
        }
        return Result.success(true);
    }

    /***
     * <p>Description:[发送验证码]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/sendMail")
    public Result sendMail(UserReq req){
        return userService.sendMail(req);
    }

    /***
     * <p>Description:[修改密码]</p>
     * Created on 2020/2/4
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/login/update/passwordByMail")
    public Result passwordByMail(UserReq req){
        return userService.passwordByMail(req);
    }
}
