package com.evostar.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 空闲检查
 */
public class IMIdleStateHandler extends IdleStateHandler {

    /**
     * 15秒检查一次。通常服务器空闲检测时间要比客户端心跳检测时间2倍还多一些
     */
    private static final int READER_IDLE_TIME = 15000;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
        System.out.println("空闲检测。。。。。。。。");
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
