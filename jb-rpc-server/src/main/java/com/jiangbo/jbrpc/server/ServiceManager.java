package com.jiangbo.jbrpc.server;

import com.jiangbo.jbrpc.Request;
import com.jiangbo.jbrpc.ServiceDescriptor;
import com.jiangbo.jbrpc.common.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务，service的管理类
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services;//这里是把一个bean的不同服务分别注册
    public ServiceManager(){
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void registery(Class<T> interfaceClass, T bean){//bean是interfaceClass这个接口的一个实现类
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods){
            ServiceInstance sis = new ServiceInstance(bean, method);
            ServiceDescriptor sdp = ServiceDescriptor.from(interfaceClass, method);

            services.put(sdp, sis);
            log.info("register service: {} {}", sdp.getClazz(), sdp.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor sdp = request.getService();
        return services.get(sdp);
    }
}
