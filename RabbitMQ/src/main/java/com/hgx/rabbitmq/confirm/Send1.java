package com.hgx.rabbitmq.confirm;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 21:53
 * @Description消息确认confirm模式-----普通发一条waitForConfirm()
 */
public class Send1 {

    private static final String QUEUE_NAME = "test_queue_confirm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmselect，将channel设成confirm模式
        channel.confirmSelect();

        String msg = "hello confirm message";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        if (!channel.waitForConfirms()) {
            System.out.println("发送失败");
        }else {
            System.out.println("发送成功");
        }
        channel.close();
        connection.close();
    }

}
