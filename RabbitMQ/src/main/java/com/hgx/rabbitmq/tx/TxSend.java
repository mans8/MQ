package com.hgx.rabbitmq.tx;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 21:35
 * @Description
 */
public class TxSend {

    private static final String QUEUE_NAME = "test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello tx message";

        try {
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("send "+msg);
            channel.txCommit();
        } catch (Exception e){
            channel.txRollback();
            System.out.println("send message txRollback");
        }
        channel.close();
        connection.close();
    }

}
