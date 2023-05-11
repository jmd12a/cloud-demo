package SendMsg;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-9:32
 * @Description：
 */
public class SynchroProducer {

    public static void main(String[] args) throws Exception{
        final DefaultMQProducer defaultMQProducer = new DefaultMQProducer("Group1");

        defaultMQProducer.setNamesrvAddr("192.168.153.128:9876");

        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        defaultMQProducer.start();

        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("Topic1","TagA","OrderID:888","Hello, RocketMQ".getBytes(StandardCharsets.UTF_8));

            messages.add(message);

        }

        SendResult result = defaultMQProducer.send(messages);
        System.out.println(result);

        // 程序睡一会 不然发送过快可能会出现问题
        // TimeUnit.SECONDS.sleep(1);

        System.out.println(123);

        defaultMQProducer.shutdown();
    }

}
