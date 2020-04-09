package com.xn.admin.common.config;

import com.xn.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {

    @Bean
    public Filter xssFilter(){
        return new XssFilter();
    }

    @Bean
    public Filter domainFilter(){
        return new DomainFilter();
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(xssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean domainFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(domainFilter());
        registration.addUrlPatterns("/*");
        registration.setName("domainFilter");
        registration.setOrder(2);
        return registration;
    }
}
