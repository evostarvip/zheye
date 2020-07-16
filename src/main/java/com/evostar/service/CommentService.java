package com.evostar.service;

import com.evostar.dao.AnswerDAO;
import com.evostar.dao.CommentDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.Answer;
import com.evostar.model.Comment;
import com.evostar.model.MsgCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private CommentDAO commentDAO;

    public void checkIsExist(int id, int type){
        if(type == 1){//给回答回复
            Answer answer = answerDAO.getById(id);
            if(answer == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else if(type == 2){//给评论回复
            Comment comment = commentDAO.getById(id);
            if(comment == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else {
            throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
        }
    }

    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }
    public Comment getById(int id){
        return commentDAO.getById(id);
    }

    public List<Comment> getAnswerCommentListById(int entityId, int type, int offset, int limit){
        return commentDAO.getAnswerCommentListById(entityId, type, offset, limit);
    }
}
