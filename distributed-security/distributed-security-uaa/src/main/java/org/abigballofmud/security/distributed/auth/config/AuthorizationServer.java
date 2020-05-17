package org.abigballofmud.security.distributed.auth.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

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
    ClientDetailsService clientDetailsService;
    @Resource
    private AuthorizationCodeServices authorizationCodeServices;
    private final AuthenticationManager authenticationManager;

    public AuthorizationServer(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 使用in-memory存储
                .inMemory()
                // .withClientDetails(clientDetailsService);
                // client_id
                .withClient("c1")
                // 客户端秘钥
                .secret(new BCryptPasswordEncoder().encode("secret"))
                // 资源列表
                .resourceIds("res1")
                // 该client允许的授权类型
                .authorizedGrantTypes("authorization_code",
                        "password", "client_credentials", "implicit", "refresh_token")
                // 允许的授权范围
                .scopes("all")
                // false跳转到授权页面
                .autoApprove(false)
                // 加上验证回调地址
                .redirectUris("http://www.baidu.com");
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
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        // 令牌默认有效期2小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 2);
        // 刷新令牌默认有效期3天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return tokenServices;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        // 设置授权码模式的授权码如何存取，暂时采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }
}
