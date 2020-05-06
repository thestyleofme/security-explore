package org.abigballofmud.controller;

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

    @PostMapping(value = "/login-success", produces = {"text/plain;charset=utf-8"})
    public String loginSuccess() {
        return "login-success";
    }

    @GetMapping(value = "/test1", produces = {"text/plain;charset=utf-8"})
    public String test1() {
        return "test1";
    }

    @GetMapping(value = "/test2", produces = {"text/plain;charset=utf-8"})
    public String test2() {
        return "test2";
    }
}
