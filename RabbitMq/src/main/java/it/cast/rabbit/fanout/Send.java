package it.cast.rabbit.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import it.cast.rabbit.util.RabbitConnectionUtil;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-17:40
 * @Description：
 */
public class Send {

    private static String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) {
        /*
        * 我们在消息的发送端，并没有声明消息队列，而是直接将消息交给了交换机，让交换机决定将消息分发给哪些消费者
        * 消费者如果想要接收到消息，就要声明一个自己的队列，并将队列绑定到这个交换机。
        * 每个绑定了交换机的队列都会接收到消息
        * */
        try(
                Connection connection = RabbitConnectionUtil.getConnection();
                Channel channel = connection.createChannel())
        {
            // 第一个参数指定交换机名称，第二个参数指定交换机类型, fanout类型的交换机会将消息所有绑定了它的队列
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String msg = "注册成功";
            /*
            * 第一个参数：交换机名称，设置为我们指定的
            * 二：队列名称，设置为空，使用交换机进行转发
            * 三：消息类型，未设置
            * 四：消息内容
            * */
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
