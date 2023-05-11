package it.cast.rabbit.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import it.cast.rabbit.util.RabbitConnectionUtil;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-19:25
 * @Description：
 */
public class Send {

    private static String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) {
        try(
                final Connection connection = RabbitConnectionUtil.getConnection();
                final Channel channel = connection.createChannel()
        ){
            // direct模式的交换机，根据routing key来发送消息
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String msg = "sms";

            // 第二个参数为routing key，如果使用的是默认交换机，第二个参数为队列名称
            channel.basicPublish(EXCHANGE_NAME,"sms",false,null,msg.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
