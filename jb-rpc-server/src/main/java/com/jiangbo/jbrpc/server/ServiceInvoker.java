package com.jiangbo.jbrpc.server;

import com.jiangbo.jbrpc.Request;
import com.jiangbo.jbrpc.common.utils.ReflectionUtils;

/**
 * 调用service实例的辅助类
 * 调用具体服务
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance service, Request request){
        return ReflectionUtils.invoke(service.getTarget(), service.getMethod(), request.getParameters());
    }
}
