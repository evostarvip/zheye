package com.evostar.model;

import com.evostar.VO.UserVO;
import lombok.Data;

@Data
public class ChatRecord {
    private int id;
    private UserVO fromUser;
    private UserVO toUser;
    private String content;
    private int time;
}
