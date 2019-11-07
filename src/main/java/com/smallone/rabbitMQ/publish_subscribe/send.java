package com.smallone.rabbitMQ.publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class send {
    public static final String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"Fanout");//分发

        String msg = "hello ps";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("Send: "+ msg);

        channel.close();
        connection.close();
    }

}
