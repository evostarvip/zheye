package com.evostar.netty.request;

import com.evostar.netty.command.Event;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * @author feng
 * @date 2019-04-21
 */
@Data
public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getEvent() {
        return Event.HEART_BEAT_REQUEST;
    }
}
