package com.product.log.filter.config;


import com.product.async.service.AsyncService;
import com.product.log.filter.ApiLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LogFilterConfig {

    private final AsyncService asyncService;

    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> apiLoggingFilterRegistrationBean() {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiLoggingFilter(asyncService));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
