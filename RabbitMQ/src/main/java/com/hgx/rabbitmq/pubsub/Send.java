package com.hgx.rabbitmq.pubsub;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 19:48
 * @Description
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建交换机声明
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        for (int i = 0; i < 50; i++) {
            String msg = "hello " + i;
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
            Thread.sleep(i*20);
            System.out.println("[pub ]send:"+msg);
        }
        channel.close();
        connection.close();
    }
}
