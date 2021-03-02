package com.jiangbo.jbrpc.codec;

/**
 * 序列化
 */
public interface Encoder {
    byte[] encoder(Object obj);
}
