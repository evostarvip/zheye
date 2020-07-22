package com.evostar.netty.response;

import com.evostar.netty.command.Event;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * @author feng
 * @date 2019-04-21
 */
@Data
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getEvent() {
        return Event.HEART_BEAT_RESPONSE;
    }
}
