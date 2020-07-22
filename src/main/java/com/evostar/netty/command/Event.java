package com.evostar.netty.command;

/**
 * 指令
 */
public interface Event {

    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;

    Byte LOGOUT_REQUEST = 5;
    Byte LOGOUT_RESPONSE = 6;

    Byte HEART_BEAT_REQUEST = 17;
    Byte HEART_BEAT_RESPONSE = 18;

}
