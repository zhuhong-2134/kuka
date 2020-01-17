package com.camelot.kuka.oauth;

import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.oauth.feign.UserClient;
import com.camelot.kuka.oauth.util.RedisStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OauthTest {

    @Autowired
    private UserClient userClient;
    @Autowired
    private RedisStringUtils redisStringUtils;
    @Test
    public void findByUsername() {
        LoginAppUser loginAppUser = userClient.findByUsername("admin");
        System.out.println(loginAppUser);
    }
    @Test
    public void redisTest(){
        boolean flag = redisStringUtils.set("hello-word","您好");
        System.out.println(flag);
        System.out.println(redisStringUtils.get("hello-word"));
    }

}
