package org.abigballofmud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@Slf4j
public class LoginController {

    @PostMapping(value = "/login-success", produces = {"text/plain;charset=utf-8"})
    public String loginSuccess() {
        // 提示具体用户名称登录成功
        return getUsername() + " login-success";
    }

    /**
     * 拥有p1权限才可以访问
     */
    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=utf-8"})
    @PreAuthorize("hasAuthority('p1')")
    public String test1() {
        return getUsername() + " test1";
    }

    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=utf-8"})
    @PreAuthorize("hasAuthority('p2')")
    public String test2() {
        return getUsername() + " test2";
    }

    private String getUsername() {
        // 当前认证通过的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        }
        // 用户身份
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
