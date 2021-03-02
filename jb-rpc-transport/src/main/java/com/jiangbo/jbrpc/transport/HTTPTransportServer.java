package com.jiangbo.jbrpc.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HTTPTransportServer implements TransportServer{
    private RequestHandler handler;
    private Server server;//实现是基于jetty的Server
    @Override
    public void init(int port, RequestHandler handler) {
        /**
         * 使用jetty嵌入servlet的方法
         */

        this.handler = handler;
        this.server = new Server(port);//新建jetty提供的Server类,指定使用的端口

        // servlet 接受请求
        ServletContextHandler ctx = new ServletContextHandler();

        /**
         * 创建一个 ServletContextHandler 并给这个 Handler 添加一个 Servlet，
         * 这里的 ServletHolder 是 Servlet 的一个装饰类，它十分类似于 Tomcat 中的 StandardWrapper。
         */

        server.setHandler(ctx);

        ServletHolder holder = new ServletHolder(new RequestServlet());//holder是jetty处理请求的一个抽象，托管
        ctx.addServlet(holder, "/*");//设置servlet的url，这个路径下的访问使用这个servlet
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();//join方法，等待server准备好
        } catch (Exception e) {
            log.error(e.getMessage(), e);//利用slf4j打印错误日志
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 自定义servlet处理方式
     */
    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");

            ServletInputStream inputStream = req.getInputStream();
            ServletOutputStream outputStream = resp.getOutputStream();

            if(handler!=null){
                handler.onRequest(inputStream, outputStream);
            }

            outputStream.flush();
        }
    }
}
