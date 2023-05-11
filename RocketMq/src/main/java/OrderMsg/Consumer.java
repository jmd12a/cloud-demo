package OrderMsg;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-12:39
 * @Description：
 */
public class Consumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group6");
        consumer.setNamesrvAddr("192.168.153.128:9876");

        // 订阅消息，第一个是topic，第二个是tag
        consumer.subscribe("Topic1","Order");
        // 负载均衡模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 这个监听器在监听同一个队列时，会保证消息发生的先后顺序，以前是通过使用一个线程监听一个队列实现的,现在貌似是给队列加锁实现的
        // 保证顺序的几个消费，如这个例子中订单id相同的几个消费，只有在上一个消费被消费完毕，所有逻辑执行完，下一个保证顺序的消费才会执行。
        // 注：顺序消息只支持可靠同步发送方式，不支持异步发送方式，否则将无法严格保证顺序
        // 顺序消费仅支持负载均衡模式/集群模式，不支持广播模式
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                list.forEach(messageExt -> {
                    try {
                        System.out.println(Thread.currentThread().getName()+new String(messageExt.getBody(),"utf-8")+
                                consumeOrderlyContext.getMessageQueue().toString());
                        System.out.println(new String(messageExt.getBody(),"utf-8")+": 消费完成");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.out.println("正在消费");
    }
}
