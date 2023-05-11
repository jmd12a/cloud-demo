package SendMsg;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-9:51
 * @Description：
 */
public class SynchroConsumer {

    public static void main(String[] args) throws Exception{
        /*
         * consumer的一个组包括多个consumer实例，但在同一个组的多个实例，应该订阅相同的内容，采用相同的消息过滤器， 使用相同的过滤逻辑。
         * */
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group1");


        consumer.setNamesrvAddr("192.168.153.128:9876");
        // 设置消费者的消费模式为广播模式，这个group内的每个消费者都会收到所有消息，默认是集群模式，每条消息只会分配给一个消费者。
        // 集群模式或者广播模式是消费者的模式，只能在消费者端通过setMessageModel()方法设置
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.subscribe("Topic1","TagA");

        // 这个监听器，应该是多个线程在执行
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(msg -> {
                    System.out.println(Thread.currentThread().getName()+new String(msg.getBody()));
                });

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
