package com.jiangbo.jbrpc.example;

import com.jiangbo.jbrpc.server.RpcServer;

public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.registery(CalculateService.class, new CalculateServiceImpl());
        server.start();
    }
}
