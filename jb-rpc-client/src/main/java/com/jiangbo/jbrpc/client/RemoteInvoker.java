package com.jiangbo.jbrpc.client;

import com.jiangbo.jbrpc.Request;
import com.jiangbo.jbrpc.Response;
import com.jiangbo.jbrpc.ServiceDescriptor;
import com.jiangbo.jbrpc.codec.Decoder;
import com.jiangbo.jbrpc.codec.Encoder;
import com.jiangbo.jbrpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private  Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;
    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    /**
     * 动态代理中调用代理的方法时，会调用invoke方法，调用的方法传给method
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);
        Response resp = invokeRemote(request);
        if(resp == null || resp.getCode()!=0) {
            throw new IllegalStateException("fail to invoke remote: " + resp);
        }
        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        Response resp = null;
        TransportClient client = null;

        try {
            client = selector.select();
            byte[] outBytes = this.encoder.encoder(request);
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));//写入到新建的流中，并返回一个输入流

            byte[] inBytes = IOUtils.readFully(receive, receive.available());
            resp = decoder.decode(inBytes, Response.class);

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            resp = new Response();
            resp.setCode(1);
            resp.setMessage("RpcClient got error: " + e.getClass() + " : " + e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }

        return resp;
    }
}
