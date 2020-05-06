package org.abigballofmud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/17 2:07
 * @since 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/").setViewName("redirect:/login");
        // 自定义登录页面
        registry.addViewController("/").setViewName("redirect:/login-view");
        registry.addViewController("/login-view").setViewName("login");
    }

}
