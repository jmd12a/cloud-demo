package it.cast.rabbit.direct;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import it.cast.rabbit.util.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-19:36
 * @Description：
 */
public class RevEmail {

    private static String EXCHANGE_NAME = "direct_exchange";
    private static String QUEUE_NAME = "email_direct";

    public static void main(String[] args) {
        try {
            final Channel channel = RabbitConnectionUtil.getConnection().createChannel();

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"sms"); // 将队列与交换机绑定，指定接收routing key为sms类型的消息

            final DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("接收到："+new String(body)+"类型的消息");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };

            channel.basicConsume(QUEUE_NAME,false,defaultConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
