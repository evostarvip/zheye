package com.evostar.configuration;

import com.evostar.interceptor.TokenInterceptor;
import com.evostar.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/layout")
                .excludePathPatterns("/reg")
                .excludePathPatterns("/index")
                .excludePathPatterns("/question/detail/**")
                .excludePathPatterns("/answerList/**");

        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/question/detail/**")
                .addPathPatterns("/answerList/**");
    }
}
