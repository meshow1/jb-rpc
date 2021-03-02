package com.jiangbo.jbrpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 表示一个具体服务
 */
@Data
@AllArgsConstructor
public class ServiceInstance {
    private Object target;//表示由哪个对象提供
    private Method method;//表示方法
}
