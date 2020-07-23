package com.evostar.service;

import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            //以问题为中心
            redisTemplate.boundSetOps(key+beFollowedId).add(String.valueOf(followUserId));
            //以当前登录的人为中心
            key = entityService.getKeyByType(type+2);
            redisTemplate.boundSetOps(key+followUserId).add(String.valueOf(beFollowedId));
            return true;
        }else{
            //已经操作过了，
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
    }
    public void followCancel(int beFollowedId, int type, int followUserId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+beFollowedId, String.valueOf(followUserId));
        key = entityService.getKeyByType(type+2);
        redisUtils.removeSetMember(key+followUserId, String.valueOf(beFollowedId));
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

    public Boolean isFollowQuestion(int beFollowQId, int followUserId){
        String key = entityService.getKeyByType(4);
        return redisTemplate.boundSetOps(key+beFollowQId).isMember(String.valueOf(followUserId));
    }

    //获取我关注的人
    public List<String> getFollowedUser(int userId){
        String key = entityService.getKeyByType(7);
        Set<String> users =  redisTemplate.boundSetOps(key+userId).members();
        return new ArrayList<>(users);
    }
    //获取关注我的人
    public List<String> getFollowUser(int userId){
        String key = entityService.getKeyByType(5);
        Set<String> users =  redisTemplate.boundSetOps(key+userId).members();
        return new ArrayList<>(users);
    }
}
