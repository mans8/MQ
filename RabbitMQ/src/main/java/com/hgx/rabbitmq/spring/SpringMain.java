package com.hgx.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hgx
 * @date 2019/9/4 23:05
 * @Description
 */
public class SpringMain {

    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:context.xml");
        //rabbitmq模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);

        //发送消息
        template.convertAndSend("hello, world!");
        Thread.sleep(1000);//休眠一秒
        ctx.destroy();
    }
}
