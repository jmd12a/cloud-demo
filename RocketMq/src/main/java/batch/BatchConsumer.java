package batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-15:18
 * @Description：
 */
public class BatchConsumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_bitch");

        consumer.setNamesrvAddr("192.168.153.128:9876");
        consumer.subscribe("batchTopic", "tag1");

        consumer.setMessageModel(MessageModel.BROADCASTING);
        /*
        * 如果要批量消费，可以通过设置以下几个属性来实现
        * 第一个属性设置每隔多少秒，线程可以从服务器拉取一次数据。一次间隔只能有一个线程拉取
        * 第二个属性设置最大消费数限制，一个线程一次最多消费多少条数据。如果要批量消费，这么要调大
        * 第三个属性设置一次拉取多少条数据
        *
        * 批量消费，每次都是几十条或者几百条数据一起被拉取，一次被拉取的批量数据是交给一个线程来处理的
        * */
        consumer.setPullInterval(100);
        consumer.setConsumeMessageBatchMaxSize(101);
        consumer.setPullBatchSize(100);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(messageExt -> {
                    try {
                        System.out.println(Thread.currentThread().getName()+new String(messageExt.getBody(),"utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (list.size() < 3  ) {
                        System.out.println("批量发送的消息不会被一次全部接受");
                    }
                });

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
