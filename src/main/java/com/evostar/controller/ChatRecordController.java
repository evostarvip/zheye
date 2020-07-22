package com.evostar.controller;

import com.evostar.model.ChatRecord;
import com.evostar.model.HostHolder;
import com.evostar.service.ChatRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Api(tags = "聊天")
public class ChatRecordController {
    @Autowired
    private ChatRecordService chatRecordService;
    @Autowired
    private HostHolder hostHolder;

    @ApiOperation(value = "获取聊天记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", required = false,value = "分页，默认1"),
            @ApiImplicitParam(name = "chatUserId", required = true, defaultValue = "1", value = "聊天对象的id")
    })
    @RequestMapping(value = "/chat_record", method = RequestMethod.GET)
    public List<ChatRecord> record(int chatUserId, @RequestParam(value = "page", required = false) int page){
        int limit = 20;
        int offset = (page - 1) * limit;
        int userId = hostHolder.getUser().getId();
        List<ChatRecord> chatRecordList = chatRecordService.getChatList(chatUserId, userId, offset, limit);
        Collections.reverse(chatRecordList);
        return chatRecordList;
    }


    @ApiOperation(value = "聊天列表")
    public void chatList(){

    }
}
