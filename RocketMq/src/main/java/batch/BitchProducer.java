package batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-15:18
 * @Description：
 */
public class BitchProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("batch_producer");

        producer.setNamesrvAddr("192.168.153.128:9876");
        producer.start();

        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            Message message = new Message("batchTopic", "tag1", ("批量消费" + i).getBytes(StandardCharsets.UTF_8));
            messages.add(message);
        }

        // 批量消息的发送可以是广播模式，也可以是负载均衡模式
        SendResult send = producer.send(messages);
        System.out.println(send);

        producer.shutdown();
    }
}
