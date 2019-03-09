package com.baicheng.oauth2authorizationserver.test;

import com.baicheng.oauth2authorizationserver.Oauth2AuthorizationServerApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 15:31
 */
@Slf4j
public class ClientDataSourceTest extends Oauth2AuthorizationServerApplicationTests {

    @Autowired
    @Qualifier("myClientDetailsService")
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Test
    public void testClientDetailsService(){
        List<ClientDetails> clientDetails = jdbcClientDetailsService.listClientDetails();
        log.info("clientDetails: {}", clientDetails);
        ClientDetails details = clientDetails.get(0);
        Collection<GrantedAuthority> authorities = details.getAuthorities();
        log.info("authorities.size: {}", authorities.size());
    }
}
