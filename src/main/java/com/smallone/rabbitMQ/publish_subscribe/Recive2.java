package com.smallone.rabbitMQ.publish_subscribe;

import com.rabbitmq.client.*;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recive2 {
    public static final String EXCHANGE_NAME = "exchange_fanout";
    public static final String QUEUE_NAME = "subscribe_queue2";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();
        // 队里声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机里
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        Consumer consumer = new DefaultConsumer(channel){

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
                    System.out.println("[2] down");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        channel.basicConsume(QUEUE_NAME,false,consumer);


    }

}
