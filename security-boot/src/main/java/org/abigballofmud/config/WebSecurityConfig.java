package org.abigballofmud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/13 1:22
 * @since 1.0
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义UserDetailsService，查询用户信息
     */
    /*@Override
    @Bean
    public UserDetailsService userDetailsServiceBean() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return manager;
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        // HashMap<String, PasswordEncoder> idToPasswordEncoder = new HashMap<>(4);
        // idToPasswordEncoder.put("1", new BCryptPasswordEncoder());
        // return new DelegatingPasswordEncoder("1", idToPasswordEncoder);
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全拦截机制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http     // 跨站请求伪造 cross-site request forgery
                .csrf().disable()
                .authorizeRequests()
                // .antMatchers("/r/r1").hasAuthority("p1")
                // .antMatchers("/r/r2").hasAuthority("p2")
                // r开头的必须认证通过
                .antMatchers("/r/**").authenticated()
                // 其他请求放过
                .anyRequest().permitAll()
                .and()
                // 允许表单登录
                .formLogin()
                // 登录页面地址
                .loginPage("/login-view")
                .loginProcessingUrl("/login")
                // 自定义登陆成功后的页面地址
                .successForwardUrl("/login-success")
                .and()
                // 默认如果需要就创建一个session登录时
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                // 自定义logout
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-view?logout");

    }
}
