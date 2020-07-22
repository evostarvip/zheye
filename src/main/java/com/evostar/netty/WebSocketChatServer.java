package com.evostar.netty;

import com.evostar.netty.config.NettyProperties;
import com.evostar.netty.initializer.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChatServer {
    private final WebSocketServerInitializer webSocketServerInitializer;
    private int port;

    public WebSocketChatServer(NettyProperties nettyProperties, WebSocketServerInitializer webSocketServerInitializer) {
        this.port = nettyProperties.getWebsocket().getPort();
        this.webSocketServerInitializer = webSocketServerInitializer;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 指定线程模型，这里是主从线程模型
                .group(bossGroup, workerGroup)
                // 指定服务端的 Channel 的 I/O 模型
                .channel(NioServerSocketChannel.class)
                // 指定处理新连接数据的读写处理逻辑:每次有新连接到来，都会去执行ChannelInitializer.initChannel()，并new一大堆handler。所以如果handler中无成员变量，则可写成单例
                .childHandler(webSocketServerInitializer);

        serverBootstrap.bind(port).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("websocket端口绑定成功 port = " + port);
            } else {
                System.out.println("websocket端口绑定失败");
            }
        });
    }
}
