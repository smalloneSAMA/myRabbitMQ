package com.smallone.rabbitMQ.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send3 {
    public static final String QUEUE_NAME="queue_confirm3";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        //生产者调用confirmSelct
        channel.confirmSelect();
        //未确认的消息
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {

            public void handleAck(long deliveryTag, boolean multiple) throws IOException {

                if(multiple){
                    System.out.println("----handleAck-----multiple");
                    confirmSet.headSet(deliveryTag+1).clear();

                }else{
                    System.out.println("-----handleAck-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("----handleNack-----multiple");
                    confirmSet.headSet(deliveryTag+1).clear();

                }else{
                    System.out.println("-----handleNack-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

        });

        String msg = "ssss";

        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }


    }



}
