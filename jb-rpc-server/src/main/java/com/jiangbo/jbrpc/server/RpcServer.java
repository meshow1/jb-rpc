package com.jiangbo.jbrpc.server;

import com.jiangbo.jbrpc.Request;
import com.jiangbo.jbrpc.Response;
import com.jiangbo.jbrpc.codec.Decoder;
import com.jiangbo.jbrpc.codec.Encoder;
import com.jiangbo.jbrpc.common.utils.ReflectionUtils;
import com.jiangbo.jbrpc.transport.RequestHandler;
import com.jiangbo.jbrpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config1) {
        this.config = config1;

        // net
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);//handler为下面自定义的Requesthandler的实现

        // codec
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        // service
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public <T> void registery(Class<T> interfaceClass, T bean) {
        serviceManager.registery(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
    }
    public void stop() {
        this.net.stop();
    }

    /**
     * 实现了transport包下定义的RequestHandler接口
     */
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {
            Response resp = new Response();
            try {
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(inBytes, Request.class);

                log.info("get request: {}", request);

                ServiceInstance sis = serviceManager.lookup(request);
                Object ret = serviceInvoker.invoke(sis, request);
                resp.setData(ret);

            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                resp.setCode(1);
                resp.setMessage("RpcServer got error: " + e.getClass().getName()
                +" " + e.getMessage());
            } finally {
                try {
                    byte[] outBytes = encoder.encoder(resp);
                    toResp.write(outBytes);
                    log.info("response client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };
}
