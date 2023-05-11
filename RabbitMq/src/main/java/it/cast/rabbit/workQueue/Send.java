package it.cast.rabbit.workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import it.cast.rabbit.util.RabbitConnectionUtil;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-17:01
 * @Description：
 */
public class Send {
    private static String queueName = "work_queue";
    public static void main(String[] args) {

        // 关闭资源的语法糖
        try (
                Connection connection = RabbitConnectionUtil.getConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(queueName, false, false, false, null);

            String message = "MQ的工作队列!";
            for (int i = 0; i < 50; i++) {
                channel.basicPublish("", queueName, null, message.getBytes());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
