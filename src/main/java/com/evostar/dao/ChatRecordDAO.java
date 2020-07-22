package com.evostar.dao;

import com.evostar.model.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ChatRecordDAO {

    public List<ChatRecord> getChatRecordList(@Param(value = "chatUserId") int chatUserId,
                                              @Param(value = "userId") int userId,
                                              @Param(value = "offset") int offset,
                                              @Param(value = "limit") int limit);
}
