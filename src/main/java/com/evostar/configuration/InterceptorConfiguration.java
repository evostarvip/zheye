package com.evostar.configuration;

import com.evostar.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").
                excludePathPatterns("/index").
                excludePathPatterns("/reg").
                excludePathPatterns("/question/**").
                excludePathPatterns("/login").
                excludePathPatterns("/layout").
                excludePathPatterns("/*.html").
                excludePathPatterns("/webjars/**").
                excludePathPatterns("/swagger**").
                excludePathPatterns("/v2/**").
                excludePathPatterns("/*.ico");
    }
}
