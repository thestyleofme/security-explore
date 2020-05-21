package org.abigballofmud.security.distributed.order.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;

/**
 * <p>
 * ResourceServerConfig
 * </p>
 *
 * @author isacc 2020/5/18 2:34
 * @since 1.0
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                // resourceId与授权服务的resourceIds匹配
                .resourceId("res1")
                // 验证令牌的服务
                // .tokenServices(tokenServices())
                // 自定检验token
                .tokenStore(tokenStore)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_ADMIN')")
                // .antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // @Bean
    // public ResourceServerTokenServices tokenServices() {
    //     // 使用远程服务请求授权服务器验证token，必须制定验证token的url, client_id, client_secret
    //     RemoteTokenServices tokenServices = new RemoteTokenServices();
    //     tokenServices.setCheckTokenEndpointUrl("http://localhost:53020/oauth/check_token");
    //     tokenServices.setClientId("c1");
    //     tokenServices.setClientSecret("secret");
    //     return tokenServices;
    // }
}
