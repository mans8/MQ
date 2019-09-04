package com.hgx.rabbitmq.topic;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 21:05
 * @Description
 */
public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg = "商品......";
        channel.basicPublish(EXCHANGE_NAME,"goods.add",null,msg.getBytes());
        System.out.println("---send "+msg);

        channel.close();
        connection.close();
    }



}
