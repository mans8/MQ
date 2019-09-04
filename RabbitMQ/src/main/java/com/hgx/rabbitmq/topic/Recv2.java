package com.hgx.rabbitmq.topic;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 20:12
 * @Description
 */
public class Recv2 {

    private static String EXCHANGE_NAME = "test_exchange_topic";
    private static String QUEUE_NAME = "test_queue_topic_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        final Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //交换机和队列绑定，并设置key
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"goods.#");
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
                System.out.println("[2] Recv msg:" + msg);
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);//回复
                }
            }
        };
        boolean autoAck = false;//手动应答
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
