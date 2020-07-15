package com.evostar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZheyeserversApplication {

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(){
        return new EhCacheCacheManager();
    }
    public static void main(String[] args) {
        SpringApplication.run(ZheyeserversApplication.class, args);
    }

}