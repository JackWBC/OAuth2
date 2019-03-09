package com.baicheng.oauth2authorizationserver.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 17:27
 */
@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //TODO 写死测试
        if (!"baicheng".equals(s)){
            return null;
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_PRODUCT_ADMIN");
        return new User("baicheng", "$2a$10$UF0Bqp8zfQLi89y8G2DLhe.YvLPsgY8QRqBMgxBkE1SaTnxQI0Ju6",
                    true, true, true, true,
                    Arrays.asList(simpleGrantedAuthority)
                );
    }
}
