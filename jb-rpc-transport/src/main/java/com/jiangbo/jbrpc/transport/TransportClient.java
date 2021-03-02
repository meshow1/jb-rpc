package com.jiangbo.jbrpc.transport;

import com.jiangbo.jbrpc.Peer;

import java.io.InputStream;

/**
 * 1.创建连接
 * 2.发送数据等待响应
 * 3.关闭连接
 */
public interface TransportClient {
    void connect(Peer peer);
    InputStream write(InputStream data);//发送数据后等待响应返回的也是数据
    void close();
}
