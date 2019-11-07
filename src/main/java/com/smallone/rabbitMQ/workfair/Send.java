package com.smallone.rabbitMQ.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
//        创建连接
        Connection connection = ConnectionUtils.getConnection();
//        创建通道
        Channel channel= connection.createChannel();
//        声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        /**
         * 每个消费者，发送确认消息之前，消息队列不发送下一个消息
         * 到消费者，一次只处理一个消息。
         * 限制发送给同一个消费者不得超过一条
         */
        int prefetchCount=1;
        channel.basicQos(prefetchCount);

        for(int i=0;i<50;i++){
            String msg = "hell "+i;
            System.out.println("[WQ] send " + msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            try {
                Thread.sleep(i*20);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        channel.close();
        connection.close();




    }
}
