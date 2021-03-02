package com.jiangbo.jbrpc.client;

import com.jiangbo.jbrpc.Peer;
import com.jiangbo.jbrpc.common.utils.ReflectionUtils;
import com.jiangbo.jbrpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelector implements TransportSelector{
    /**
     * 表示连接好的client，因为一个服务可能有多个连接，这里选择方法为随机挑一个
     */
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        this.clients = new ArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);

        for (Peer peer : peers) {
            for (int i = 0; i<count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
