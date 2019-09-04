package com.hgx.rabbitmq.workfair;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 17:17
 * @Description 消费者发送消息，一个生产，多个消费
 */
public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //每个消费者确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息


        for (int i = 0; i < 100; i++) {
            String msg = "hello " + i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*5);
            System.out.println("[WQ ]send:"+msg);
        }
        channel.close();
        connection.close();
    }
}
