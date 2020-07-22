package com.evostar.netty.response;

import com.evostar.netty.command.Command;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * @author feng
 * @date 2019-04-21
 */
@Data
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
