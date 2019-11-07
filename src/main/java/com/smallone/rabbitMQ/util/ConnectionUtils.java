package com.smallone.rabbitMQ.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    /**
     * 获取MQ连接
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {

        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        //设置端口号 ampq 5672
        factory.setPort(5672);

        //设置virtual host
        factory.setVirtualHost("/xieaoyi");

        //用户名
        factory.setUsername("xieaoyi");

        //密码
        factory.setPassword("xieaoyi");


        return factory.newConnection();
    }

}
