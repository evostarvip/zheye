package com.evostar.netty.handler;

import com.evostar.VO.UserVO;
import com.evostar.dao.UserDAO;
import com.evostar.model.ChatRecord;
import com.evostar.model.User;
import com.evostar.netty.request.MessageRequestPacket;
import com.evostar.netty.response.MessageResponsePacket;
import com.evostar.netty.utils.Session;
import com.evostar.netty.utils.SessionUtil;
import com.evostar.netty.utils.SpringUtil;
import com.evostar.service.ChatRecordService;
import com.evostar.service.UserService;
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
    static {
        userService = SpringUtil.getBean(UserService.class);
        chatRecordService = SpringUtil.getBean(ChatRecordService.class);
    }

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {

        // 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());
        System.out.println("这里是解析messageRequest");
        System.out.println(msg);

        // 构造信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUser(session.getUserVO());
        messageResponsePacket.setTime(msg.getTime());
        messageResponsePacket.setContent(msg.getContent());
        User toUser = userService.selectById(Integer.parseInt(msg.getToUserId()));
        // 拿到消息接收方的 Channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());
        // 将消息发给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel) && toUser != null) {
            UserVO toUserVO = userService.getUserVO(toUser);
            messageResponsePacket.setToUser(toUserVO);
            ctx.channel().writeAndFlush(messageResponsePacket);
            toUserChannel.writeAndFlush(messageResponsePacket);
            this.save_record(messageResponsePacket);
        } else {
            System.out.println("[" + msg.getToUserId() + "]不在线，发送失败!");
        }
    }
    private void save_record(MessageResponsePacket messageResponsePacket){
        // 建立聊天记录
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setFromUserId(messageResponsePacket.getFromUser().getId());
        chatRecord.setToUserId(messageResponsePacket.getToUser().getId());
        chatRecord.setContent(messageResponsePacket.getContent());
        chatRecord.setTime(Integer.parseInt(messageResponsePacket.getTime()));
        chatRecordService.addChatRecord(chatRecord);
    }
}
