package org.abigballofmud.security.distributed.auth.config;

import java.util.Collections;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * <p>
 * 授权服务配置
 * </p>
 *
 * @author isacc 2020/5/18 0:45
 * @since 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;
    @Resource
    private JwtAccessTokenConverter accessTokenConverter;
    @Resource
    // private ClientDetailsService clientDetailsService;
    private ClientDetailsService jdbcClientDetailsService;
    @Resource
    private AuthorizationCodeServices authorizationCodeServices;
    @Resource
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthorizationServer(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 将客户端信息存储到数据库
     */
    @Bean
    public ClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
        JdbcClientDetailsService detailsService = new JdbcClientDetailsService(dataSource);
        detailsService.setPasswordEncoder(passwordEncoder);
        return detailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService);
        // clients
        //         // 使用in-memory存储
        //         .inMemory()
        //         // client_id
        //         .withClient("c1")
        //         // 客户端秘钥
        //         .secret(new BCryptPasswordEncoder().encode("secret"))
        //         // 资源列表
        //         .resourceIds("res1")
        //         // 该client允许的授权类型
        //         .authorizedGrantTypes("authorization_code",
        //                 "password", "client_credentials", "implicit", "refresh_token")
        //         // 允许的授权范围
        //         .scopes("all")
        //         // false跳转到授权页面
        //         .autoApprove(false)
        //         // 加上验证回调地址
        //         .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices)
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // oauth/token_key公开
                .tokenKeyAccess("permitAll()")
                // oauth/check_token公开
                .checkTokenAccess("permitAll()")
                // 允许表单认证申请令牌
                .allowFormAuthenticationForClients();
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(jdbcClientDetailsService);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        // jwt设置 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // 令牌默认有效期2小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 2);
        // 刷新令牌默认有效期3天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return tokenServices;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        // 设置授权码模式的授权码如何存取，暂时采用内存方式 InMemoryAuthorizationCodeServices
        return new JdbcAuthorizationCodeServices(dataSource);
    }
}
