package com.camelot.kuka.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fehorizon.commonService.model.user.LoginAppUser;
import com.google.common.collect.Sets;
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
        // TODO 测试
        // return adminUser();
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

    private static LoginAppUser superAdminUser() {
        LoginAppUser loginAppUser = new LoginAppUser();
        loginAppUser.setSuperAdmin(Boolean.TRUE);
        loginAppUser.setOrgAdmin(Boolean.TRUE);
        loginAppUser.setAscription(-1L);
        loginAppUser.setApplications(Sets.newHashSet());
        loginAppUser.setPermissions(Sets.newHashSet());
        loginAppUser.setId(-1L);
        loginAppUser.setUsername("superAdmin");
        return loginAppUser;
    }

    private static LoginAppUser adminUser() {
        LoginAppUser loginAppUser = new LoginAppUser();
        loginAppUser.setSuperAdmin(Boolean.FALSE);
        loginAppUser.setOrgAdmin(Boolean.TRUE);
        loginAppUser.setAscription(41L);
        loginAppUser.setApplications(Sets.newHashSet("code1","code2","code3","code4"));
        loginAppUser.setPermissions(Sets.newHashSet("code1:999", "code1:122", "code1:000", "code1:676", "code1:897"));
        loginAppUser.setId(1L);
        loginAppUser.setUsername("feHorizonAdmin");
        return loginAppUser;
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
