package it.cast.rabbit.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import it.cast.rabbit.util.RabbitConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-15:12
 * @Description：
 */
public class Producer {
    private static String queueName = "MyQueue";
    public static void main(String[] args) {
        // 关闭资源的语法糖
        try(
                Connection    connection = RabbitConnectionUtil.getConnection();
                Channel channel = connection.createChannel();
        )
        {
            channel.queueDeclare(queueName,false,false,false,null);

            String message = "Hello, MQ!";
            channel.basicPublish("",queueName,null,message.getBytes());
        }catch (Exception exception){
            exception.printStackTrace();
        }


    }


}
