package com.baicheng.oauth2client.test.service;

import com.baicheng.oauth2client.Oauth2ClientApplicationTests;
import com.baicheng.oauth2client.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author baicheng
 * @description
 * @create 2019-02-26 22:02
 */
@Slf4j
public class MyUserDetailsServiceTest extends Oauth2ClientApplicationTests {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Test
    public void testLoadUserByUsername(){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("baicheng");
        log.info("userDetails: {}", userDetails);
    }
}
