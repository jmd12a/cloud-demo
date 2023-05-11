package it.cast.rabbit.workQueue;

import com.rabbitmq.client.*;
import it.cast.rabbit.util.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-17:04
 * @Description：
 */
public class Rev1 {

    private static String queueName = "work_queue";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 设置这个信道的消费者同时只能处理一个消息，这样做，消息会被分发给其他空闲的消费者，或者没有这个设置的消息者
            channel.basicQos(1);
            // 声明信道
            channel.queueDeclare(queueName,false,false,false,null);

            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消息id："+ envelope.getDeliveryTag());
                    System.out.println(new String(body,"utf-8"));

                    // 消息id，手动回复
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };

            // 对这个队列进行监听，当这个队列,有消息时，就调用consumer中的handleDelivery方法
            channel.basicConsume(queueName,false,consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
