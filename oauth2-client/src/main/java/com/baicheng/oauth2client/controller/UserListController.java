package com.baicheng.oauth2client.controller;

import com.baicheng.oauth2client.bos.UserBO;
import com.baicheng.oauth2client.mapper.UserMapper;
import com.baicheng.oauth2client.model.UserPO;
import com.baicheng.oauth2client.model.UserPOExample;
import com.baicheng.oauth2client.utils.POBOParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author baicheng
 * @description 方法权限测试
 * @create 2019-02-26 23:36
 */
@RestController
@Slf4j
@RequestMapping("/userList")
public class UserListController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/show")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_1', 'AUTHORITY_2')")
    public ResponseEntity<List<UserBO>> showUserList(){
        List<UserPO> userPOS = userMapper.selectByExample(new UserPOExample());
        if (CollectionUtils.isEmpty(userPOS)){
            return ResponseEntity.of(Optional.empty());
        }
        List<UserBO> userBOS = userPOS.stream().map(POBOParseUtil::parseUserBO).filter(Objects::nonNull).collect(Collectors.toList());
        return ResponseEntity.ok(userBOS);
    }

    @RequestMapping("/say")
    @PreAuthorize("hasAuthority('AUTHORITY_1')")
    public ResponseEntity<String> sayHelloSpring(){
        return ResponseEntity.ok("Hello Spring!!!");
    }
}
