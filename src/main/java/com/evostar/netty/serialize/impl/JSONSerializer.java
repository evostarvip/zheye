package com.evostar.netty.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.evostar.netty.serialize.Serializer;
import com.evostar.netty.serialize.SerializerAlgorithm;

/**
 * json 序列化器
 *
 * @author feng
 * @date 2019-04-20
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
