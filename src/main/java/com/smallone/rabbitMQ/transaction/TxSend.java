package com.smallone.rabbitMQ.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.smallone.rabbitMQ.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {

    public static final String QUEUE_NAME = "queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello tx";
        try {

            channel.txSelect();

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

//            int x = 1/0;
            channel.txCommit();
            System.out.println("[Send]" + msg);
        }catch (Exception e){
            channel.txRollback();
            System.out.println("send msg tx Rollback");
        }
        channel.close();
        connection.close();


    }

}
