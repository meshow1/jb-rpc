package com.jiangbo.jbrpc.codec;
import com.alibaba.fastjson.JSON;
/**
 * jsonencoder
 */
public class JSONEncoder implements Encoder{
    @Override
    public byte[] encoder(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
