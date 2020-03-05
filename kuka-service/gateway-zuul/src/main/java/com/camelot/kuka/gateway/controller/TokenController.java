package com.camelot.kuka.gateway.controller;

import com.camelot.kuka.gateway.feign.LogClient;
import com.camelot.kuka.gateway.feign.Oauth2Client;
import com.camelot.kuka.gateway.feign.UserLoginClient;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.log.Log;
import com.camelot.kuka.model.oauth.SystemClientInfo;
import com.camelot.kuka.model.user.constants.CredentialType;
import com.camelot.kuka.model.user.req.UserReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 登陆、刷新token、退出
 *
 * @author 崔春松
 */
@Slf4j
@RestController
public class TokenController {

    @Resource
    private Oauth2Client oauth2Client;
    @Resource
    private LogClient logClient;
    @Resource
    private UserLoginClient userLoginClient;

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/api/sys/login")
    public Result<Map<String, Object>> login(String userName, String password, String uuid, String code) {
        // 校验验证码
        UserReq userReq = new UserReq();
        userReq.setCode(code);
        userReq.setUuid(uuid);
        Result<Boolean> booleanResult = userLoginClient.checkCode(userReq);
        if (!booleanResult.isSuccess()) {
            return Result.error(booleanResult.getMsg());
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", userName + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);

        Map<String, Object> tokenInfo = null;
        try {
            tokenInfo = oauth2Client.postAccessToken(parameters);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("账号或密码错误");
        }
        saveLoginLog(userName, "用户名密码登陆");

        return Result.success(tokenInfo);
    }



    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(String username, String remark) {
        log.info("{}登陆", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                Log log = Log.builder().username(username).module("登陆").remark(remark).createTime(new Date())
                        .build();
                logClient.save(log);
            } catch (Exception e) {
                // do nothing
            }

        });
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refresh_token
     * @return
     */
    @PostMapping("/api/sys/refresh_token")
    public Map<String, Object> refresh_token(String refresh_token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("refresh_token", refresh_token);
        return oauth2Client.postAccessToken(parameters);
    }

    /**
     * 退出
     *
     * @param access_token
     */
    @GetMapping("/api/sys/logout")
    public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        oauth2Client.removeToken(access_token);
    }
}
