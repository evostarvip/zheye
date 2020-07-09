package com.evostar.service;

import com.evostar.dao.AnswerDAO;
import com.evostar.dao.QuestionDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.Answer;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import com.evostar.vo.ActionsVO;
import com.evostar.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private QuestionDAO questionDAO;

    public Answer getLastAnswerByQuestionId(int questionId) {
        return answerDAO.getLastAnswerByQuestionId(questionId);
    }

    public int addAnswer(Answer answer) {
        return answerDAO.addAnswer(answer);
    }

    public void checkQid(int questionId) {
        if (questionId == 0) {
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid" + MsgCodeEnum.PARAM_EMPTY.getMessage());
        }
        //查询问题是否存在
        Question question = questionDAO.getById(questionId);
        if (question == null) {
            throw new ServiceException(MsgCodeEnum.DATA_NONE);
        }
    }

    public List<Answer> getAnswerListByQidDesc(int qid, int offset, int limit) {
        return answerDAO.getAnswerListByQidDesc(qid, offset, limit);
    }

    public Answer getAnswerById(int aid) {
        return answerDAO.getAnswerById(aid);
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

    public int getAnswerCountByQid(int qid) {
        return answerDAO.getCountByQid(qid);
    }
}
