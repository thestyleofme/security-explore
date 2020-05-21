package org.abigballofmud.security.distributed.auth.service;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.security.distributed.auth.dao.UserDao;
import org.abigballofmud.security.distributed.common.model.UserDTO;
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
    private final ObjectMapper objectMapper;

    public SpringDataUserDetailsServiceImpl(UserDao userDao,
                                            ObjectMapper objectMapper) {
        this.userDao = userDao;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
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
        // 将userDTO转为json
        String principal = objectMapper.writeValueAsString(userDTO);
        return User.withUsername(principal)
                .password(userDTO.getPassword())
                .authorities(userPermissionList.toArray(new String[]{})).build();
    }
}
