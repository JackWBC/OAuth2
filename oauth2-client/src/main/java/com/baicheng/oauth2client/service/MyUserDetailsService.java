package com.baicheng.oauth2client.service;

import com.baicheng.oauth2client.mapper.UserMapper;
import com.baicheng.oauth2client.model.UserPO;
import com.baicheng.oauth2client.model.UserPOExample;
import com.baicheng.oauth2client.utils.POBOParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author baicheng
 * @description
 * @create 2019-02-26 20:37
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        try {
            if (StringUtils.isEmpty(userName)){
                log.info("userName is empty, UserName: {}", userName);
                return null;
            }
            UserPOExample example = new UserPOExample();
            example.createCriteria().andUserNameEqualTo(userName);
            List<UserPO> userPOS = userMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(userPOS)){
                log.info("userName not found, userName: {}", userName);
                return null;
            }
            return POBOParseUtil.parseUserBO(userPOS.get(0));
        } catch (Exception e) {
            log.error("found user error, userName: {}", userName, e);
            return null;
        }
    }
}
