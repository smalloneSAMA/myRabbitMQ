package com.smallone.rabbitMQ.workfair;

import com.rabbitmq.client.*;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recive1 {
    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        final Channel channel = connection.createChannel();
        //声明通道
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);

//        定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            //消息到达，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("new API recive" + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] down");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        Boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);


    }

}
