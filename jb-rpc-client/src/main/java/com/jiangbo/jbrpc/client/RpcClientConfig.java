package com.jiangbo.jbrpc.client;

import com.jiangbo.jbrpc.Peer;
import com.jiangbo.jbrpc.codec.Decoder;
import com.jiangbo.jbrpc.codec.Encoder;
import com.jiangbo.jbrpc.codec.JSONDecoder;
import com.jiangbo.jbrpc.codec.JSONEncoder;
import com.jiangbo.jbrpc.transport.HTTPTransportClient;
import com.jiangbo.jbrpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> dncoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;//默认的路由选择策略
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1", 3000)
    );//设置一个默认的可达地址，因为没有注册中心所以这里写死一个
}
