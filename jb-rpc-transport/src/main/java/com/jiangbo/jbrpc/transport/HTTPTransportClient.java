package com.jiangbo.jbrpc.transport;

import com.jiangbo.jbrpc.Peer;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HTTPTransportClient implements TransportClient{
    private String url;
    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" +peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();//新建http连接
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");

            httpConn.connect();//开始连接
            IOUtils.copy(data, httpConn.getOutputStream());//利用common.io工具类IOUtils把数据复制到输出流中

            int resultCode = httpConn.getResponseCode();//获取响应
            if(resultCode==HttpURLConnection.HTTP_OK){
                return httpConn.getInputStream();//成功则返回返回的信息流
            }
            else {
                return httpConn.getErrorStream();//否则返回失败信息流
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
