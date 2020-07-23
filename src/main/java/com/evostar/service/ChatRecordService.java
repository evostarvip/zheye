package com.evostar.service;

import com.evostar.VO.ChatRecordVO;
import com.evostar.dao.ChatRecordDAO;
import com.evostar.model.ChatRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRecordService {
    @Autowired
    private ChatRecordDAO chatRecordDAO;

    public List<ChatRecordVO> getChatList(int chatUserId, int userId, int offset, int limit){
        return chatRecordDAO.getChatRecordList(chatUserId, userId, offset, limit);
    }

    public int addChatRecord(ChatRecord chatRecord){
        return chatRecordDAO.addChatRecord(chatRecord);
    }
}
