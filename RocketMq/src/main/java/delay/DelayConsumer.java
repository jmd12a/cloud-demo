package delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-14:47
 * @Description：
 */
public class DelayConsumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_delay");

        consumer.setNamesrvAddr("192.168.153.128:9876");
        consumer.subscribe("delayTopic", "tag1");
        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(messageExt -> {
                    try {
                        System.out.println(new String(messageExt.getBody(),"utf-8")+"延迟时间："+(System.currentTimeMillis()-
                                messageExt.getBornTimestamp()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
