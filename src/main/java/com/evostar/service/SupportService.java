package com.evostar.service;

import com.evostar.dao.AnswerDAO;
import com.evostar.dao.CommentDAO;
import com.evostar.dao.QuestionDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.*;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SupportService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private RedisUtils redisUtils;

    public Boolean checkIsExist(int id, int type){
        if(type == 0){
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY);
        }
        if(id == 0){
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY);
        }
        if(type == 1){
            Question question = questionDAO.getById(id);
            if(question == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else if(type == 2){
            Answer answer = answerDAO.getById(id);
            if(answer == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else{
            Comment comment = commentDAO.getById(id);
            if(comment == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }
        return true;
    }


    public String getKeyByType(int type){
        String key = "";
        for (EntityType entityType : EntityType.values()){
            if(entityType.getType() == type){
                key = entityType.getKey();
                break;
            }
        }
        return key;
    }
    //点赞、给点赞的集合增加数据
    public Boolean support(int id, int type, int userId) {
        String key = getKeyByType(type);
        if(key.equals("")){
            throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
        }
        redisUtils.removeSetMember(key+"_UNSUPPORT_"+id, String.valueOf(userId));
        Boolean res = redisTemplate.boundSetOps(key+"_SUPPORT_"+id).isMember(String.valueOf(userId));
        if(!res){
            redisTemplate.boundSetOps(key+"_SUPPORT_"+id).add(String.valueOf(userId));
            return true;
        }else{
            //已经操作过了，
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
    }
    //点赞、给点赞的集合增加数据
    public Boolean unSupport(int id, int type, int userId) {
        String key = getKeyByType(type);
        if(key.equals("")){
            throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
        }
        redisTemplate.boundSetOps(key+"_UNSUPPORT_"+id).add(String.valueOf(userId));
        Boolean res = redisTemplate.boundSetOps(key+"_UNSUPPORT_"+id).isMember(String.valueOf(userId));
        if(!res){
            redisUtils.removeSetMember(key+"_SUPPORT_"+id, String.valueOf(userId));
            return true;
        }else{
            //已经操作过了，
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
    }




}
