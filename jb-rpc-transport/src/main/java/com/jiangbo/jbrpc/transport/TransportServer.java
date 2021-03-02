package com.jiangbo.jbrpc.transport;

/**
 * 1.启动监听端口
 * 2.接受请求
 * 3.关闭监听
 */
public interface TransportServer {
    void init(int port, RequestHandler handler);//初始化传入handler和port
    void start();

    void stop();

}
