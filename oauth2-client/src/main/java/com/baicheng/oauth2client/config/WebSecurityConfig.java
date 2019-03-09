package com.baicheng.oauth2client.config;

import com.baicheng.oauth2client.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author baicheng
 * @description
 * @create 2019-02-26 20:30
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // --------------------------------- OAuth2 客户端配置 ----------------------------------

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource oauthDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public ClientTokenServices clientTokenServices(){
        return new JdbcClientTokenServices(oauthDataSource());
    }

    /**
     * 四种授权模式的选择, 只需要改变 OAuth2ProtectedResourceDetails 的具体实现即可
     * @return OAuth2ProtectedResourceDetails
     */
    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails() {

//        --------------- authorization_code --------------
//        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
//        resource.setClientId("oauth2_client");
//        resource.setClientSecret("1111");
//        resource.setAccessTokenUri("http://localhost:8001/oauth/token");
//        resource.setUserAuthorizationUri("http://localhost:8001/oauth/authorize");
//        resource.setScope(Arrays.asList("read"));
//        return resource;

//        ---------------- client_credentials ------------------
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setClientId("oauth2_client");
        resource.setClientSecret("1111");
        resource.setAccessTokenUri("http://localhost:8001/oauth/token");
        resource.setScope(Arrays.asList("read"));
        return resource;

//        ---------------- implicit ------------------
//        说明, implicit模式下, 实际上由user-agent即浏览器代替client去和认证服务器进行交互, 当拿到令牌后再经 client 向 资源服务器发出请求
//        所以此处的模拟测试, 请保证数据库中有 有效令牌(即先跑一次其他模式授权)
//        ImplicitResourceDetails resource = new ImplicitResourceDetails();
//        resource.setClientId("oauth2_client");
//        resource.setClientSecret("1111");
//        resource.setAccessTokenUri("http://localhost:8001/oauth/token");
//        resource.setScope(Arrays.asList("read"));
//        return resource;

//        ---------------- password ------------------
//        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
//        resource.setClientId("oauth2_client");
//        resource.setClientSecret("1111");
//        resource.setUsername("baicheng");
//        resource.setPassword("1111");
//        resource.setAccessTokenUri("http://localhost:8001/oauth/token");
//        resource.setScope(Arrays.asList("read"));
//        return resource;
    }

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations oAuth2RestOperations() {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), oAuth2ClientContext);

        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(
                        new ClientCredentialsAccessTokenProvider(),
                        new AuthorizationCodeAccessTokenProvider(),
                        new ImplicitAccessTokenProvider(),
                        new ResourceOwnerPasswordAccessTokenProvider()
                )
        );
        provider.setClientTokenServices(clientTokenServices());
        oAuth2RestTemplate.setAccessTokenProvider(provider);

        return oAuth2RestTemplate;
    }

    // ------------------------------- Web Security 配置 ------------------------------------

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                // 设置userDetailsService
                .userDetailsService(userDetailsService())
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    // 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**","/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/home",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return myUserDetailsService;
    }
}
