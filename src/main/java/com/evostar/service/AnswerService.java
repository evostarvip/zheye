package com.evostar.service;

import com.evostar.VO.ActionsVO;
import com.evostar.VO.AnswerVO;
import com.evostar.dao.AnswerDAO;
import com.evostar.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerDAO answerDAO;
    public int addAnswer(Answer answer){
        return answerDAO.addAnswer(answer);
    }

    public Answer getLastAnswerByQuestionId(int questionId){
        return answerDAO.getLastAnswerByQuestionId(questionId);
    }

    public AnswerVO getAnswerVO(Answer answer) {
        AnswerVO answerVO = new AnswerVO();
        answerVO.setId(answer.getId());
        answerVO.setAnswer(answer.getAnswer());
        answerVO.setContent(answer.getContent());
        answerVO.setCreatedDate(answer.getCreatedDate());
        ActionsVO actionsVO = new ActionsVO();
        //暂留，后面开发点赞、评论时填充值
        actionsVO.setCollect(false);
        actionsVO.setAgreeNum(0);
        actionsVO.setDisagree(false);
        actionsVO.setIsAgree(false);
        actionsVO.setLike(false);
        actionsVO.setReviewNum(0);
        answerVO.setActions(actionsVO);
        return answerVO;
    }

    public List<Answer> getAnswerListByQidDesc(int qid, int offset, int limit){
        return answerDAO.getAnswerListByQidDesc(qid, offset, limit);
    }

    public int getAnserCountByQid(int qid){
        return answerDAO.getCountByQid(qid);
    }

    public Answer getById(int id){
        return answerDAO.getById(id);
    }
}
