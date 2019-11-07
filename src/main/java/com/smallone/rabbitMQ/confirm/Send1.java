package com.smallone.rabbitMQ.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通模式
 */
public class Send1 {

    public static final String QUEUE_NAME = "queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect()，将channel设置为confirm模式，注意队列设置过了模式就不能变了
        channel.confirmSelect();

        String msg = "hello confirm msg";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if(!channel.waitForConfirms()){
            System.out.println("msg send failed");
        }else {
            System.out.println("msg send successful");
        }
        channel.close();
        connection.close();
    }

}
