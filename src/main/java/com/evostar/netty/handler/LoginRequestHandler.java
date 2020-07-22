package com.evostar.netty.handler;

import com.evostar.VO.UserVO;
import com.evostar.model.HostHolder;
import com.evostar.model.User;
import com.evostar.netty.request.LoginRequestPacket;
import com.evostar.netty.response.LoginResponsePacket;
import com.evostar.netty.utils.Session;
import com.evostar.netty.utils.SessionUtil;
import com.evostar.netty.utils.SpringUtil;
import com.evostar.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 登录请求逻辑处理器
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    private static UserService userService;
    static {
        userService = SpringUtil.getBean(UserService.class);
    }
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {
        // 处理登录请求数据包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        int userId = Integer.parseInt(msg.getUserId());
        User user = userService.selectById(userId);
        if(user != null){
            // 缓存用户会话信息和连接的映射关系
            UserVO userVO = userService.getUserVO(user);
            Session session = new Session(userVO);
            SessionUtil.bindSession(session, ctx.channel());

            loginResponsePacket.setSuccess(true);
            System.out.println("[" + userVO.getName() + "]登录成功");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("当前用户不存在或token失效");
            System.out.println(new Date() + ":登录失败!");
        }
        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 清除用户会话信息和连接的映射关系
        SessionUtil.unBindSession(ctx.channel());
    }
}
