package com.evostar.netty.attribute;

import com.evostar.model.HostHolder;
import com.evostar.model.User;
import com.evostar.netty.utils.Session;
import io.netty.util.AttributeKey;

/**
 * 用于缓存到 channel 中的属性的键
 *
 * @author feng
 * @date 2019-04-20
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
