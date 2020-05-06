package org.abigballofmud.security.session.service.impl;

import java.util.*;

import org.abigballofmud.security.session.model.AuthenticationRequest;
import org.abigballofmud.security.session.model.UserDTO;
import org.abigballofmud.security.session.service.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 1:08
 * @since 1.0
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private HashMap<String, UserDTO> userMap = new HashMap<>(8);

    {
        Set<String> authorities1 = new HashSet<>();
        // p1 -> controller中的test1
        authorities1.add("p1");
        Set<String> authorities2 = new HashSet<>();
        // p2 -> controller中的test2
        authorities2.add("p2");
        userMap.put("zhangsan", new UserDTO("1", "zhangsan", "123", "张三", "18584", authorities1));
        userMap.put("lisi", new UserDTO("2", "lisi", "456", "李四", "80545", authorities2));
    }

    @Override
    public UserDTO authentication(AuthenticationRequest authenticationRequest) {
        // 校验参数
        if (Objects.isNull(authenticationRequest) ||
                StringUtils.isEmpty(authenticationRequest.getUsername()) ||
                StringUtils.isEmpty(authenticationRequest.getPassword())) {
            throw new IllegalArgumentException("账号密码不能为空");
        }
        // 根据账号去查数据库 这里模拟
        UserDTO userDTO = getUserByName(authenticationRequest.getUsername());
        // 校验密码
        if (!Objects.equals(userDTO.getPassword(), authenticationRequest.getPassword())) {
            throw new IllegalArgumentException("用户名或密码不正确");
        }
        // 认证成功，返回用户身份信息
        return userDTO;
    }

    private UserDTO getUserByName(String username) {
        return Optional.ofNullable(userMap.get(username)).orElseThrow(IllegalArgumentException::new);
    }

}
