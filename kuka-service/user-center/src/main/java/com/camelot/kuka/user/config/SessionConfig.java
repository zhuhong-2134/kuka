package com.camelot.kuka.user.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 开启session共享
 * 
 * @author 崔春松
 *
 */
@EnableRedisHttpSession
public class SessionConfig {

}
