package org.abigballofmud.security.session.init;

import org.abigballofmud.security.session.config.ApplicationConfig;
import org.abigballofmud.security.session.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 0:39
 * @since 1.0
 */
public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Spring容器，相当于加载application.xml
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class};
    }

    /**
     * ServletContext，相当于加载spring-mvc.xml
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * url-mapping
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
