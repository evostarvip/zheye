package com.evostar.netty.handler;

import com.evostar.dao.UserDAO;
import com.evostar.model.User;
import com.evostar.netty.request.MessageRequestPacket;
import com.evostar.netty.response.MessageResponsePacket;
import com.evostar.netty.utils.Session;
import com.evostar.netty.utils.SessionUtil;
import com.evostar.netty.utils.SpringUtil;
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
    static {
        userService = SpringUtil.getBean(UserService.class);
    }

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {

        // 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 构造信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUser(session.getUserVO());
        messageResponsePacket.setTime(msg.getTime());
        messageResponsePacket.setContent(msg.getContent());

        // 拿到消息接收方的 Channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        // 将消息发给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("[" + msg.getToUserId() + "]不在线，发送失败!");
        }
    }
}
