package com.evostar.netty.request;

import com.evostar.model.User;
import com.evostar.netty.command.Event;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * 登录请求数据包
 * @date 2019-04-20
 */
@Data
public class LoginRequestPacket extends Packet {

    /**
     * 用户Id
     */
    private String userId;

    @Override
    public Byte getEvent() {
        return Event.LOGIN_REQUEST;
    }

}
