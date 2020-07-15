package com.evostar.utils;

import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void removeSetMember(String key, String value) {
        Boolean exist = redisTemplate.boundSetOps(key).isMember(value);
        System.out.println(exist);
        if(exist){
            redisTemplate.opsForSet().remove(key, value);
        }
    }
}
