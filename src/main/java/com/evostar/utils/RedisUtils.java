package com.evostar.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    private static RedisTemplate<String, String> redisTemplate;

    public static void removeSetMember(String key, String value){
        Boolean res = redisTemplate.opsForSet().isMember(key, value);
        if(res){
            redisTemplate.opsForSet().remove(key, value);
        }
    }
}
