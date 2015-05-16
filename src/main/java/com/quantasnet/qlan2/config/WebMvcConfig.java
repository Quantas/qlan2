package com.quantasnet.qlan2.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/404").setViewName("error/404");
        registry.addViewController("/403").setViewName("error/403");
        registry.addViewController("/503").setViewName("error/503");
    }

    // TODO: remove @Qualifier and inject by type
    // See for details: https://github.com/spring-projects/spring-boot/issues/2774
    @Bean
    public FilterRegistrationBean getSpringSecurityFilterChainBindedToError(
            @Qualifier("springSecurityFilterChain") Filter springSecurityFilterChain) {

        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(springSecurityFilterChain);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> container.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/404"),
                new ErrorPage(HttpStatus.FORBIDDEN, "/403"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/503"));
    }
}
