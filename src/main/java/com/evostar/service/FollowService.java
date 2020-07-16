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
    public Boolean follow(int beFollowedId, int type, int followUserId) {
        String key = entityService.getKeyByType(type);
        Boolean res = redisTemplate.boundSetOps(key+beFollowedId).isMember(String.valueOf(followUserId));
        if(!res){
            redisTemplate.boundSetOps(key+beFollowedId).add(String.valueOf(followUserId));
            return true;
        }else{
            //已经操作过了，
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
    }
    public void followCancel(int beFollowedId, int type, int followUserId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+beFollowedId, String.valueOf(followUserId));
    }

    public Boolean isFollowUser(int beFollowUserId, int followUserId){
        String key = entityService.getKeyByType(5);
        return redisTemplate.boundSetOps(key+beFollowUserId).isMember(String.valueOf(followUserId));
    }
    public int followUserNum(int beFollowUserId){
        String key = entityService.getKeyByType(5);
        return redisTemplate.boundSetOps(key+beFollowUserId).size().intValue();
    }
    public int followQuestionNum(int questionId){
        String key = entityService.getKeyByType(4);
        return redisTemplate.boundSetOps(key+questionId).size().intValue();
    }
}
