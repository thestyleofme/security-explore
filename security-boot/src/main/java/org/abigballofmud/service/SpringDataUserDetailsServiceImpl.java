package org.abigballofmud.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.dao.UserDao;
import org.abigballofmud.model.UserDTO;
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

    private final UserDao userDao;

    public SpringDataUserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("username: {}", username);
        // 数据库中根据账号查询用户信息
        UserDTO userDTO = userDao.getUserByUsername(username);
        if (userDTO == null) {
            // 如果用户不存在，返回空 由provider来抛异常
            return null;
        }
        // 根据user_id查询权限
        List<String> userPermissionList = userDao.findPermissionByUserId(userDTO.getId());
        return User.withUsername(userDTO.getUsername())
                .password(userDTO.getPassword())
                .authorities(userPermissionList.toArray(new String[]{})).build();
    }
}
