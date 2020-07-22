package com.evostar.netty.request;

import com.evostar.netty.command.Event;
import com.evostar.netty.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端发送至服务端的消息数据包
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    /**
     * 消息要发给哪个用户
     */
    private String toUserId;

    /**
     * 消息内容
     */
    private String content;

    private String time;

    public MessageRequestPacket(String toUserId, String content, String time) {
        this.toUserId = toUserId;
        this.content = content;
        this.time = time;
    }

    @Override
    public Byte getEvent() {
        return Event.MESSAGE_REQUEST;
    }
}
