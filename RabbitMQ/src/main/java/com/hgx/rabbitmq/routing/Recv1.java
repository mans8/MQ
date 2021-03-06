package com.hgx.rabbitmq.routing;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 20:12
 * @Description
 */
public class Recv1 {

    private static String EXCHANGE_NAME = "test_exchange_direct";
    private static String QUEUE_NAME = "test_queue_direct_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        final Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //交换机和队列绑定，并设置路由key
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");
        //保证每次只发一个
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            //消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException{
                String msg = new String(body,"utf-8");
                System.out.println("[1] Recv msg:" + msg);
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);//回复
                }
            }
        };
        boolean autoAck = false;//手动应答
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
