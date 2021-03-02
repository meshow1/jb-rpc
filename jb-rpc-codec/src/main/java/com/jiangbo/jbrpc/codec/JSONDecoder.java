package com.jiangbo.jbrpc.codec;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class JSONDecoder implements Decoder{
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
