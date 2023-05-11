package it.cast.rabbit.fanout;

import com.rabbitmq.client.*;
import it.cast.rabbit.util.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-18:09
 * @Description：
 */
public class PhoneRev {
    private static String EXCHANGE_NAME = "fanout_exchange";
    private static String QUEUE_NAME = "phone_queue";

    public static void main(String[] args) {

        try {
            Connection connection = RabbitConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            // 先声明，才能绑定队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            // 将队列与交换机绑定，发送到交换机的消息，会被交换机转发给绑定了它的队列。第一个参数为队列名，第二个参数为交换机名
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");


            final DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("通过电话通知：您好，你的账号"+new String(body,"utf-8"));
                    // 手动回复ack
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };

            // 当这个队列接收到消息时，就执行方法。第二个参数设置为false，不自动恢复ack
            channel.basicConsume(QUEUE_NAME,false,defaultConsumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
