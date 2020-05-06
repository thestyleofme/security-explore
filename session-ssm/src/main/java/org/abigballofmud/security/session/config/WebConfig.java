package org.abigballofmud.security.session.config;

import org.abigballofmud.security.session.interceptors.SimpleAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 0:25
 * @since 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.abigballofmud.security",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class WebConfig implements WebMvcConfigurer {

    private final SimpleAuthenticationInterceptor simpleAuthenticationInterceptor;

    public WebConfig(SimpleAuthenticationInterceptor simpleAuthenticationInterceptor) {
        this.simpleAuthenticationInterceptor = simpleAuthenticationInterceptor;
    }

    /**
     * 视图解析器
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(simpleAuthenticationInterceptor).addPathPatterns("/test*");
    }
}
