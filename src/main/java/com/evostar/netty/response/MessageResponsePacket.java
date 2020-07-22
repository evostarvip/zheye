package com.evostar.netty.response;

import com.evostar.VO.UserVO;
import com.evostar.netty.command.Command;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * 服务端发送至客户端的消息数据包
 */
@Data
public class MessageResponsePacket extends Packet {

    /**
     * 消息来源用户
     */
    private UserVO fromUser;

    private String content;

    private String time;

    private int unreadNum = 0;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
