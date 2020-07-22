package com.evostar.netty.response;

import com.evostar.netty.command.Command;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * 登录响应数据包
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String name;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}