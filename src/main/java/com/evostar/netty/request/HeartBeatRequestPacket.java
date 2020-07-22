package com.evostar.netty.request;

import com.evostar.netty.command.Command;
import com.evostar.netty.protocol.Packet;
import lombok.Data;

/**
 * @author feng
 * @date 2019-04-21
 */
@Data
public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
