package com.evostar.netty.utils;

import com.evostar.netty.handler.*;
import io.netty.channel.ChannelPipeline;

/**
 * ChannelPipeline工具类
 *
 * @author feng
 * @date 2019-04-22
 */
public class PipelineUtil {

    /**
     * 添加websocket/tcp通用handler
     *
     * @param pipeline
     */
    public static void addHandler(ChannelPipeline pipeline) {
        pipeline.addLast(
                // 登录
                LoginRequestHandler.INSTANCE,
                // 心跳检测
                HeartBeatRequestHandler.INSTANCE,
                // 身份校验
                AuthHandler.INSTANCE,
                // 单聊消息
                MessageRequestHandler.INSTANCE
//                // 退出登录
//                LogoutRequestHandler.INSTANCE
        );
    }

}
