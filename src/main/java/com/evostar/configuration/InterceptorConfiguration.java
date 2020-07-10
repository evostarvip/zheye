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
        // TODO json 接口放到 /api/ 下，拦截 /api/** 就好了
        // exclude 在Interceptor中的target，判断是否有 Annotation，最好实现 Role-Base 或者继承 shiro.
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/index")
                .excludePathPatterns("/api/question/detail/**")
                .excludePathPatterns("/api/reg")
                .excludePathPatterns("/api/layout")
                .excludePathPatterns("/api/login");
    }
}
