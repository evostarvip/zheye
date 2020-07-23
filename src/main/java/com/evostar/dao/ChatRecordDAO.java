package com.evostar.dao;

import com.evostar.VO.ChatRecordVO;
import com.evostar.model.ChatRecord;
import com.evostar.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ChatRecordDAO {

    public List<ChatRecordVO> getChatRecordList(@Param(value = "chatUserId") int chatUserId,
                                                @Param(value = "userId") int userId,
                                                @Param(value = "offset") int offset,
                                                @Param(value = "limit") int limit);

    @Insert("insert into chat_record (from_user_id,to_user_id,content,time) values (#{fromUserId},#{toUserId},#{content},#{time})")
    public int addChatRecord(ChatRecord chatRecord);
}
