package com.hgx.rabbitmq.routing;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 20:10
 * @Description
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建交换机声明
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        for (int i = 0; i < 100; i++) {
            String routingKey = (i%2 == 0)?"error":"warning";
            String msg = "hello direct! message" + i + "," +routingKey;
            //发布
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
            System.out.println("send:"+msg + "  routingkey:" + routingKey);
        }

        channel.close();
        connection.close();
    }


}
