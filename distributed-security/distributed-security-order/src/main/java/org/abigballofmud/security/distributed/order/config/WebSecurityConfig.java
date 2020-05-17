// package org.abigballofmud.security.distributed.order.config;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
// /**
//  * <p>
//  * 需配置 spring.main.allow-bean-definition-overriding=true
//  * </p>
//  *
//  * @author isacc 2019/12/13 1:22
//  * @since 1.0
//  */
// @Configuration
// @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//     /**
//      * 安全拦截机制
//      */
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http     // 跨站请求伪造 cross-site request forgery
//                 .csrf().disable()
//                 .authorizeRequests()
//                 // .antMatchers("/r/r1").hasAnyAuthority("p1")
//                 .antMatchers("/r/**").authenticated()
//                 // 其他请求放过
//                 .anyRequest().permitAll()
//                 .and()
//                 // 允许表单登录
//                 .formLogin();
//
//     }
// }
