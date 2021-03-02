package com.jiangbo.jbrpc.server;

import com.jiangbo.jbrpc.codec.Decoder;
import com.jiangbo.jbrpc.codec.Encoder;
import com.jiangbo.jbrpc.codec.JSONDecoder;
import com.jiangbo.jbrpc.codec.JSONEncoder;
import com.jiangbo.jbrpc.transport.HTTPTransportServer;
import com.jiangbo.jbrpc.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port = 3000;
}
