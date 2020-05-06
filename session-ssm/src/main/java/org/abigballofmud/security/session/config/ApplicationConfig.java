package org.abigballofmud.security.session.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 在此配置除了Controller的其他bean，比如数据库连接池，事务管理器，业务bean等
 * </p>
 *
 * @author isacc 2019/12/10 0:16
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = "org.abigballofmud.security",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class ApplicationConfig {
}
