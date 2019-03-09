package com.baicheng.oauth2client.test.mapper;

import com.baicheng.oauth2client.Oauth2ClientApplicationTests;
import com.baicheng.oauth2client.mapper.UserMapper;
import com.baicheng.oauth2client.model.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author baicheng
 * @description
 * @create 2019-02-26 21:19
 */
@Slf4j
public class UserMapperTest extends Oauth2ClientApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert(){
        UserPO userPO = new UserPO();

        userPO = userPO.withUserName("baicheng")
                .withPassword(passwordEncoder.encode("1111"))
                .withRoles("ADMIN,USER1")
                .withAuthorities("AUTHORITY_1,AUTHORITY_2")
                .withCreateBy("baicheng")
                .withUpdateBy("baicheng");

        int insertSelective = userMapper.insertSelective(userPO);
        log.info("insertSelective: {}", insertSelective);
    }
}
