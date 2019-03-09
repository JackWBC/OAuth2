package com.baicheng.oauth2client.utils;

import com.baicheng.oauth2client.bos.UserBO;
import com.baicheng.oauth2client.model.UserPO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author baicheng
 * @description
 * @create 2019-02-26 21:02
 */
public class POBOParseUtil {

    public static UserBO parseUserBO(UserPO userPO){

        return UserBO.builder()
                .id(userPO.getId())
                .userName(userPO.getUserName())
                .password(userPO.getPassword())
                .roles(change(userPO.getRoles()))
                .authorities(change(userPO.getAuthorities()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
                .isDeleted(userPO.getIsDeleted())
                .createTime(userPO.getCreateTime())
                .createBy(userPO.getCreateBy())
                .updateTime(userPO.getUpdateTime())
                .updateBy(userPO.getUpdateBy())
                .build();
    }

    private static Set<String> change(String str){
        if (StringUtils.isEmpty(str)){
            return new HashSet<>();
        }
        return Arrays.stream(str.split(","))
                .filter(item -> !StringUtils.isEmpty(item))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
