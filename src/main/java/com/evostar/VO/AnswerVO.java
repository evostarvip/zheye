package com.evostar.VO;

import com.evostar.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class AnswerVO {
    private int id;
    private int userId;
    private String content;
    private Date createdDate;
    private UserVO answer;
    private ActionsVO actions;
}
