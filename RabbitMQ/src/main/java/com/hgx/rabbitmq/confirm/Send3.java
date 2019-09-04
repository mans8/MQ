package com.hgx.rabbitmq.confirm;

import com.hgx.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @author hgx
 * @date 2019/9/4 21:53
 * @Description消息确认confirm模式-----异步方式
 */
public class Send3 {

    private static final String QUEUE_NAME = "test_queue_confirm3";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmselect，将channel设成confirm模式
        channel.confirmSelect();

        //为确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {

            //没有问题的handleAck
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("----handleAck--multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("---handleAck--multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

            //handlerNack
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("----handleNack--multiple-----");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("----handleNack--multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "sss";
        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }

    }

}
