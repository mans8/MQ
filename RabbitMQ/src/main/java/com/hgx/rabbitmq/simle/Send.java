package com.hgx.rabbitmq.simle;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * @author hgx
 * @date 2019/9/4 16:01
 * @Description 消费者发送消息，一个生产，一个消费
 */
public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnetion();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simple!";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("send message:"+msg);

        channel.close();
        connection.close();
    }
}
