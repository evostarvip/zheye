package com.evostar;


import com.evostar.netty.WebSocketChatServer;
import com.evostar.netty.config.NettyProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * NettyServer启动器
 *
 * @author feng
 * @date 2019-04-22
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Netty属性配置
     */
    private final NettyProperties nettyProperties;

    /**
     * WebSocket服务端启动器
     */
    private final WebSocketChatServer webSocketChatServer;


    public NettyBooter(WebSocketChatServer webSocketChatServer,  NettyProperties nettyProperties) {
        this.webSocketChatServer = webSocketChatServer;
        this.nettyProperties = nettyProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // 根据netty配置协议，运行不同的启动器
            if ("websocket".equals(nettyProperties.getProtocol().toLowerCase())) {
                webSocketChatServer.start();
            } else {
                System.out.println("没有TCP通讯协议");
            }
        }
    }
}
