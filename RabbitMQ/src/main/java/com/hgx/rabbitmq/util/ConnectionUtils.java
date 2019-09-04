package com.hgx.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    public static Connection getConnetion() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务器地址
        factory.setHost("127.0.0.1");
        //AMQP 5672
        factory.setPort(5672);
        //vhost（相当于一个数据库）
        factory.setVirtualHost("/vhost_hgx");
        //设置用户名
        factory.setUsername("hgx");
        //密码
        factory.setPassword("123456");
        return factory.newConnection();
    }




}
