package com.jiangbo.jbrpc.example;

import com.jiangbo.jbrpc.client.RpcClient;

public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalculateService service = client.getProxy(CalculateService.class);//到服务中寻找CalculateService服务

        int r1 = service.add(1, 2);
        int r2 = service.minus(10, 1);

        System.out.println(r1);
        System.out.println(r2);
    }
}
