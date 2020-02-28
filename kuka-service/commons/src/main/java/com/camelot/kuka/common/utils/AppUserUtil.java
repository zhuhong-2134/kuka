package com.camelot.kuka.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.camelot.kuka.model.user.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

@Slf4j
public class AppUserUtil {

    /**
     * 获取登陆的 LoginAppUser
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LoginAppUser getLoginAppUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginAppUser) {
					LoginAppUser la = (LoginAppUser) principal;
                    return la;
                }
                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");
				LoginAppUser la = JSONObject.parseObject(JSONObject.toJSONString(map), LoginAppUser.class);
                return la;
            }
        }
        return null;
    }

    /**
     *  开发使用
     * @return
     */
    public static String getLoginUserName() {
        return "admin";
    }

    /**
     * 获取登陆的 LoginAppUser
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LoginAppUser getLoginUser() {
        LoginAppUser user = new LoginAppUser();
        user.setId(1010101L);
        user.setUserName("admin");
        user.setRoleId(10003L);
        user.setPhotoUrl("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
        return user;
    }

    /**
     * 获取登录用户名
     * @return
     */
    public static String getLoginAppUserName() {
        return null == AppUserUtil.getLoginAppUser() ? null : AppUserUtil.getLoginAppUser().getUsername();
    }

    /**
     * 获取登录用户ID
     * @return
     */
    public static Long getLoginAppUserId() {
        return null == AppUserUtil.getLoginAppUser() ? null : AppUserUtil.getLoginAppUser().getId();
    }
}
