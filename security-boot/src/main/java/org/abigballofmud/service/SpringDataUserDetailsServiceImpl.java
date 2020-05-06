package org.abigballofmud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/17 16:07
 * @since 1.0
 */
@Service
@Slf4j
public class SpringDataUserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 数据库中根据账号查询用户信息 暂时模拟
        log.debug("username: {}", username);
        return User.withUsername("zhangsan")
                .password("$2a$10$GPhyb8ybIu.6gXMJt5ycH.yr6IT9YvOGvPcWYHniCMNouIeTt1GcC")
                .authorities("p1").build();
    }
}
