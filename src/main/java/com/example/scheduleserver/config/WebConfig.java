package com.example.scheduleserver.config;

import com.example.scheduleserver.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig {


    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        // 생성한 필터 등록
        filterRegistrationBean.setFilter(new LoginFilter());

        // 우선순위 1번 지정
        filterRegistrationBean.setOrder(1);

        // 전체 URL에 적용
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
