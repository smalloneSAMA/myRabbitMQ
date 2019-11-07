package com.smallone.rabbitMQ.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 商品：发布 删除 修改 查询....
 */

public class Send {

    public static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msgString = "商品///";
        channel.basicPublish(EXCHANGE_NAME,"goods.remove.add",null,msgString.getBytes());
        System.out.println("----Send: "+ msgString);

        channel.close();
        connection.close();

    }


}
