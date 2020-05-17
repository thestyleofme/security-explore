package org.abigballofmud.security.distributed.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 * AuthApplication
 * </p>
 *
 * @author isacc 2020/5/17 2:48
 * @since 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
