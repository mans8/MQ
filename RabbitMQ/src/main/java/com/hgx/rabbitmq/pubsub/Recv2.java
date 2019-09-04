package com.hgx.rabbitmq.pubsub;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 19:55
 * @Description
 */
public class Recv2 {

    private static String QUEUE_NAME = "test_queue_fanout_sms";
    private static String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //交换机声明
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //保证一次只发一个
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
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                }
            }
        };
        boolean autoAck = true;//自动应答false
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }


}
