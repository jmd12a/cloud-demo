package SendMsg;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-9:50
 * @Description：
 */
public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group2");
        consumer.setNamesrvAddr("192.168.153.128:9876");

        // 订阅消息，第一个是topic，第二个是tag
        consumer.subscribe("Topic1","TagB");
        // 负载均衡模式
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置监听器
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(messageExt -> {
                    System.out.println(new String(messageExt.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 开启消费者
        consumer.start();
    }
}
