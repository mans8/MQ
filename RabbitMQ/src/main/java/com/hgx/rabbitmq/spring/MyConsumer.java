package com.hgx.rabbitmq.spring;

/**
 * @author hgx
 * @date 2019/9/4 23:04
 * @Description
 */
public class MyConsumer {

    //具体执行业务的方法
    private void listen(String foo) {
        System.out.println("消费者：" + foo);
    }
}
