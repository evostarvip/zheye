package com.evostar.service;

import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private EntityService entityService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisUtils redisUtils;

    //关注、
    public Boolean follow(int id, int type, int userId) {
        String key = entityService.getKeyByType(type);
        Boolean res = redisTemplate.boundSetOps(key+id).isMember(String.valueOf(userId));
        if(!res){
            redisTemplate.boundSetOps(key+id).add(String.valueOf(userId));
            return true;
        }else{
            //已经操作过了，
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
    }
    public void followCancel(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+id, String.valueOf(userId));
    }
}
