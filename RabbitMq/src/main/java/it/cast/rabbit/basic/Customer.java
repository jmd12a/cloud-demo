package it.cast.rabbit.basic;

import com.rabbitmq.client.*;
import it.cast.rabbit.util.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-15:11
 * @Description：
 */
public class Customer {

    private static String queueName = "MyQueue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitConnectionUtil.getConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare(queueName,false,false,false,null);
        DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    long deliveryTag = envelope.getDeliveryTag(); // 消息id
                    String exchange = envelope.getExchange(); // 交换机
                    String routingKey = envelope.getRoutingKey(); // 路由的key

                    // System.out.println(exchange);
                    System.out.println(deliveryTag);
                    String message = new String(body, "utf-8");
                    System.out.println(message);

                    // 第一个参数，消息的id，第二个参数，是否批量ACK所有id小于第一个参数的消息。
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };

        /*
        * queueName 队列名称
        * true 是否自动恢复
        * customer 要执行的方法
        **/
        channel.basicConsume(queueName, false, consumer);
        // 这个代码后面的依然会执行，但这个代码会一直保持监听状态,每次接收到队列中的消息，就会执行consumer中的handleDelivery方法

        System.out.println(123);


    }
}
