package com.evostar.service;

import com.evostar.VO.ChatRecordVO;
import com.evostar.dao.ChatRecordDAO;
import com.evostar.model.ChatRecord;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRecordService {
    @Autowired
    private ChatRecordDAO chatRecordDAO;
    @Autowired
    private RedisUtils redisUtils;

    public List<ChatRecordVO> getChatList(int chatUserId, int userId, int offset, int limit){
        return chatRecordDAO.getChatRecordList(chatUserId, userId, offset, limit);
    }

    public int addChatRecord(ChatRecord chatRecord){
        return chatRecordDAO.addChatRecord(chatRecord);
    }

    public int getUnreadNum(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        if(redisUtils.hasKey(key)){
            return Integer.parseInt((String) redisUtils.getValue(key));
        }else{
            return 0;
        }
    }

    //清空未读数量
    public void clearUnreadNum(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        if(redisUtils.hasKey(key)){
            redisUtils.clearNum(key);
        }
    }
}
