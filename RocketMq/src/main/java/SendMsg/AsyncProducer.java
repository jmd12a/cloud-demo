package SendMsg;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-22:34
 * @Description：
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception{
        final DefaultMQProducer defaultMQProducer = new DefaultMQProducer("Group1");
        defaultMQProducer.setNamesrvAddr("192.168.153.128:9876");

        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        defaultMQProducer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("Topic1","TagB","OrderID:777",("Hello, RocketMQ"+i).getBytes(StandardCharsets.UTF_8));

            defaultMQProducer.send(message , new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("消息发送失败 "+throwable.getMessage());
                }
            });


            // 程序睡一会 不然发送过快可能会出现问题
            // TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(123);

        TimeUnit.SECONDS.sleep(3); // 休息十秒后再关闭，以免异步消息没有发送完毕
        // 异步直接走下面了，消息还没有发送出去，producer就被关闭了，异步中是不能这样操作的
        defaultMQProducer.shutdown();

    }
}
