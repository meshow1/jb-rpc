package com.jiangbo.jbrpc.client;

import com.jiangbo.jbrpc.codec.Decoder;
import com.jiangbo.jbrpc.codec.Encoder;
import com.jiangbo.jbrpc.common.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

public class RpcClient {
    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());//使用默认配置，默认连接的 ip为127.0.0.1，端口为3000
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDncoderClass());
        this.selector = ReflectionUtils.newInstance(config.getSelectorClass());

        this.selector.init(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTransportClass()
        );

    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz, encoder, decoder, selector)
        );
    }
}
