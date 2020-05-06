package org.abigballofmud.security.session.controller;

import java.util.Objects;
import javax.servlet.http.HttpSession;

import org.abigballofmud.security.session.constants.SecurityConstant;
import org.abigballofmud.security.session.model.AuthenticationRequest;
import org.abigballofmud.security.session.model.UserDTO;
import org.abigballofmud.security.session.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 1:27
 * @since 1.0
 */
@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "login", produces = {"text/plain;charset=utf-8"})
    public String login(AuthenticationRequest authenticationRequest, HttpSession session) {
        UserDTO userDTO = authenticationService.authentication(authenticationRequest);
        // 登录成功 存入session
        session.setAttribute(SecurityConstant.SESSION_USER_KEY, userDTO);
        return String.format("%s login success", userDTO.getUsername());
    }

    @PostMapping(value = "/logout", produces = {"text/plain;charset=utf-8"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout success";
    }

    @GetMapping(value = "/test1", produces = {"text/plain;charset=utf-8"})
    public String test1(HttpSession session) {
        Object object = session.getAttribute(SecurityConstant.SESSION_USER_KEY);
        String fullName;
        if (Objects.isNull(object)) {
            fullName = "匿名用户";
        } else {
            UserDTO userDTO = (UserDTO) object;
            fullName = userDTO.getFullName();
        }
        return fullName + " test1";
    }

    @GetMapping(value = "/test2", produces = {"text/plain;charset=utf-8"})
    public String test2(HttpSession session) {
        Object object = session.getAttribute(SecurityConstant.SESSION_USER_KEY);
        String fullName;
        if (Objects.isNull(object)) {
            fullName = "匿名用户";
        } else {
            UserDTO userDTO = (UserDTO) object;
            fullName = userDTO.getFullName();
        }
        return fullName + " test2";
    }

}
