package org.abigballofmud.security.spring.init;

import org.abigballofmud.security.spring.config.WebSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/13 2:00
 * @since 1.0
 */
public class SpringSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    protected SpringSecurityApplicationInitializer() {
        // super(WebSecurityConfig.class);
    }
}
