package com.dayoung.ginseng;

import com.dayoung.ginseng.member.interceptor.LogInterceptor;
import com.dayoung.ginseng.member.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/members/login")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/bootstrap/**", "/");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/bootstrap/**", "/members/**", "/");
    }

}
