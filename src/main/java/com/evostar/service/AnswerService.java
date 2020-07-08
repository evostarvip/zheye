package com.evostar.service;

import com.evostar.dao.AnswerDAO;
import com.evostar.dao.QuestionDAO;
import com.evostar.exception.MyException;
import com.evostar.model.Answer;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private QuestionDAO questionDAO;

    public Answer getLastAnswerByQuestionId(int questionId){
        return answerDAO.getLastAnswerByQuestionId(questionId);
    }

    public int addAnswer(Answer answer){
        return answerDAO.addAnswer(answer);
    }
    public void checkQid(int questionId){
        if(questionId == 0){
            throw new MyException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid"+MsgCodeEnum.PARAM_EMPTY.getMsg());
        }
        //查询问题是否存在
        Question question = questionDAO.getById(questionId);
        if(question == null){
            throw new MyException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMsg());
        }
    }
    public List<Answer> getAnswerListByQidDesc(int qid, int offset, int limit){
        return answerDAO.getAnswerListByQidDesc(qid, offset, limit);
    }
}
