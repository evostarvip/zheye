package com.evostar.controller;

import com.evostar.VO.ChatListVO;
import com.evostar.VO.ChatRecordVO;
import com.evostar.model.ChatRecord;
import com.evostar.model.HostHolder;
import com.evostar.model.User;
import com.evostar.service.ChatRecordService;
import com.evostar.service.FollowService;
import com.evostar.service.UserService;
import com.evostar.utils.RedisUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "聊天")
public class ChatRecordController {
    @Autowired
    private ChatRecordService chatRecordService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "获取聊天记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", required = false,value = "分页，默认1"),
            @ApiImplicitParam(name = "chatUserId", required = true, defaultValue = "1", value = "聊天对象的id")
    })
    @RequestMapping(value = "/chat_record", method = RequestMethod.GET)
    public List<ChatRecordVO> record(int chatUserId, @RequestParam(value = "page", required = false) int page){
        int limit = 20;
        int offset = (page - 1) * limit;
        int userId = hostHolder.getUser().getId();
        List<ChatRecordVO> chatRecordList = chatRecordService.getChatList(chatUserId, userId, offset, limit);
        Collections.reverse(chatRecordList);
        return chatRecordList;
    }


    @ApiOperation(value = "聊天列表")
    @RequestMapping(value = "/chatList", method = RequestMethod.GET)
    public List<ChatListVO> chatList(){
        //获取我关注的人+关注我的人集合
        //获取我关注的人
        List<String> followedUser = followService.getFollowedUser(hostHolder.getUser().getId());
        //获取关注我的人
        List<String> followUser = followService.getFollowUser(hostHolder.getUser().getId());
        //求两个list的并集，先去重、再合并
        followedUser.removeAll(followUser);
        followedUser.addAll(followUser);
        //获取
        List<User> userList = userService.getUserByIds(followedUser);
        return userList.stream().map(user -> {
            ChatListVO chatListVO = new ChatListVO();
            chatListVO.setUser(userService.getUserVO(user));
            List<ChatRecordVO> chatRecords = chatRecordService.getChatList(user.getId(), hostHolder.getUser().getId(),0, 1);
            if(chatRecords.size() > 0){
                chatListVO.setContent(chatRecords.get(0).getContent());
                chatListVO.setTime(chatRecords.get(0).getTime());
            }
            int unreadNum = chatRecordService.getUnreadNum(user.getId(), hostHolder.getUser().getId());
            chatListVO.setUnreadNum(unreadNum);
            return chatListVO;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "清楚未读消息")
    @RequestMapping(value = "/clear_unread_num", method = RequestMethod.GET)
    @ApiImplicitParam(name = "fromUserId", value = "发消息的人的id", required = true, defaultValue = "1")
    public HashMap<String, String> clearUnreadNum(int fromUserId){
        chatRecordService.clearUnreadNum(fromUserId, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }
}
