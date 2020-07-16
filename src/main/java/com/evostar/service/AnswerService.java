package com.evostar.service;

import com.evostar.VO.ActionsVO;
import com.evostar.VO.AnswerVO;
import com.evostar.dao.AnswerDAO;
import com.evostar.model.Answer;
import com.evostar.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SupportService supportService;
    @Autowired
    private UserService userService;
    public int addAnswer(Answer answer){
        return answerDAO.addAnswer(answer);
    }

    public Answer getLastAnswerByQuestionId(int questionId){
        return answerDAO.getLastAnswerByQuestionId(questionId);
    }

    public AnswerVO getAnswerVO(Answer answer) {
        AnswerVO answerVO = new AnswerVO();
        answerVO.setId(answer.getId());
        answerVO.setAnswer(userService.getUserVO(answer.getAnswer()));
        answerVO.setContent(answer.getContent());
        answerVO.setCreatedDate(answer.getCreatedDate());
        ActionsVO actionsVO = new ActionsVO();
        //暂留，后面开发点赞、评论时填充值
        actionsVO.setCollect(false);
        actionsVO.setAgreeNum(supportService.supportNum(answer.getId(), 2));
        if(hostHolder.getUser() != null){
            actionsVO.setDisagree(supportService.isUnSupport(answer.getId(), 2, hostHolder.getUser().getId()));
            actionsVO.setIsAgree(supportService.isSupport(answer.getId(), 2, hostHolder.getUser().getId()));
        }
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

    public int getCountByUserId(int userId){
        return answerDAO.getCountByUserId(userId);
    }
}
