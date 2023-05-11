package delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-14:33
 * @Description：
 */
public class DelayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("delay_group");

        producer.setNamesrvAddr("192.168.153.128:9876");

        producer.start(); // 启动producer

        for (int i = 0; i < 3; i++) {

            int j = i;
            // 一个topic，要么只发送即时消息，要么只发送延时消息
            Message message = new Message("delayTopic", "tag1", ("延迟消息：" + i).getBytes(StandardCharsets.UTF_8));

            // 设置消息延时级别为3，延时10秒
            message.setDelayTimeLevel(2);

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                    if (j==2){
                        producer.shutdown();
                    }
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                    if (j==2){
                        producer.shutdown();
                    }
                }
            });
        }

        System.out.println(123);



    }
}
