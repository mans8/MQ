package com.hgx.rabbitmq.simle;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 16:05
 * @Description 消费者获取消息
 */
public class Recv {

    private static String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException
            {
                String msg = new String(body,"utf-8");
                System.out.println("new api recv:"+msg);
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }

    //过时写法
    public static void oldRecv() throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connetion.createChannel();
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("[recv] msg:"+msg);
        }
    }
}
