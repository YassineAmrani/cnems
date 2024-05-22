package com.cnems.config;

import com.cnems.filter.JwtTokenFilter;
import com.cnems.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> jwtfilter() {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtTokenFilter);

        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
