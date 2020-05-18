package org.abigballofmud.security.distributed.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * <p>
 * TokenConfig
 * </p>
 *
 * @author isacc 2020/5/18 1:10
 * @since 1.0
 */
@Configuration
public class TokenConfig {

    public static final String STRING_KEY = "uaa123";

    @Bean
    public TokenStore tokenStore() {
        // 使用内存来存储令牌（普通令牌） InMemoryTokenStore()
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称秘钥，资源服务器使用该秘钥来验证
        converter.setSigningKey(STRING_KEY);
        return converter;
    }
}
