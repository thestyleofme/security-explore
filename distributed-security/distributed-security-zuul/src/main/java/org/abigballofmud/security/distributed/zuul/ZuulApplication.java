package org.abigballofmud.security.distributed.zuul;

import java.util.List;

import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * <p>
 * DiscoveryApplication
 * </p>
 *
 * @author isacc 2020/5/19 2:12
 * @since 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ZuulApplication.class, args);
            if (ConfigurationManager.getConfigInstance() instanceof DynamicConfiguration) {
                DynamicConfiguration config = (DynamicConfiguration) ConfigurationManager.getConfigInstance();
                config.stopLoading();
            } else if (ConfigurationManager.getConfigInstance() instanceof ConcurrentCompositeConfiguration) {
                ConcurrentCompositeConfiguration configInst =
                        (ConcurrentCompositeConfiguration) ConfigurationManager.getConfigInstance();
                List<AbstractConfiguration> configs = configInst.getConfigurations();
                if (configs != null) {
                    for (AbstractConfiguration config : configs) {
                        if (config instanceof DynamicConfiguration) {
                            ((DynamicConfiguration) config).stopLoading();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
