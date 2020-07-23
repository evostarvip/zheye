package com.evostar.netty.handler;

import com.evostar.model.ChatRecord;
import com.evostar.model.User;
import com.evostar.netty.request.MessageRequestPacket;
import com.evostar.netty.response.MessageResponsePacket;
import com.evostar.netty.utils.Session;
import com.evostar.netty.utils.SessionUtil;
import com.evostar.netty.utils.SpringUtil;
import com.evostar.service.ChatRecordService;
import com.evostar.service.UserService;
import com.evostar.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息转发请求逻辑处理器
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    private static UserService userService;
    private static ChatRecordService chatRecordService;
    private static RedisUtils redisUtils;
    static {
        userService = SpringUtil.getBean(UserService.class);
        chatRecordService = SpringUtil.getBean(ChatRecordService.class);
        redisUtils = SpringUtil.getBean(RedisUtils.class);
    }

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg){
        // 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 构造信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUser(session.getUserVO());
        messageResponsePacket.setTime(msg.getTime());
        messageResponsePacket.setContent(msg.getContent());
        messageResponsePacket.setUnreadNum(this.getUnreadNum(session.getUserVO().getId(), Integer.parseInt(msg.getToUserId())));
        User toUser = userService.selectById(Integer.parseInt(msg.getToUserId()));
        if(toUser != null){
            // 拿到消息接收方的 Channel
            Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());
            this.save_record(messageResponsePacket);
            // 将消息发给消息接收方
            if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
                //通知
                ctx.channel().writeAndFlush(messageResponsePacket);
                toUserChannel.writeAndFlush(messageResponsePacket);
            }
        }else{
            System.out.println("[" + msg.getToUserId() + "]用户不存在，发送失败!");
        }
    }
    //保存聊天记录
    private void save_record(MessageResponsePacket messageResponsePacket){
        // 建立聊天记录
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setFromUserId(messageResponsePacket.getFromUser().getId());
        chatRecord.setToUserId(messageResponsePacket.getToUser().getId());
        chatRecord.setContent(messageResponsePacket.getContent());
        chatRecord.setTime(Integer.parseInt(messageResponsePacket.getTime()));
        chatRecordService.addChatRecord(chatRecord);
    }
    //获取未读数量
    public int getUnreadNum(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        this.unreadNumInc(fromUserId, toUserId);
        if(redisUtils.hasKey(key)){
            return Integer.parseInt((String) redisUtils.getValue(key));
        }else {
            return 1;
        }
    }
    //增加未读数量
    private void unreadNumInc(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        redisUtils.increment(key, 1);
    }
}
