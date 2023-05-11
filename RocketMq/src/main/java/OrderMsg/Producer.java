package OrderMsg;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import sun.nio.cs.ext.MS874;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-12:06
 * @Description：
 */
public class Producer {

    public static void main(String[] args) throws Exception{
        final DefaultMQProducer defaultMQProducer = new DefaultMQProducer("Group2");

        defaultMQProducer.setNamesrvAddr("192.168.153.128:9876");

        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        defaultMQProducer.start();

        List<Order> orderList = Order.OrderList();
        Message message = null;
        /*
        * 要发送顺序消息,需要将需要有顺序的消息发送到同一个队列中，因为队列天生就是有序。
        * 要保证有顺序的消息发送到一个队列，就需要这些消息有一个共同表示，比如这里的orderId，同一个id的消息发送到同一个队列
        * 我们还需要一个选择器，来定义根据这个表示选择队列的方式
        *
        * 注：顺序消息只支持可靠同步发送方式，不支持异步发送方式，否则将无法严格保证顺序
        * */


        for (int i = 0; i < orderList.size(); i++) {
             message =  new Message("Topic1", "Order",
                     (i+": "+orderList.get(i).toString()).getBytes(StandardCharsets.UTF_8));

            defaultMQProducer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long orderId = (long) o;
                    // 通过取模的方式，得到一个list的索引，可以保证相同id得到相同的索引，从而得到相同队列
                    int index = (int) orderId % list.size();

                    return list.get(index);
                }
            }, orderList.get(i).getOrderId()/*, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                    if (j == orderList.size()-1){
                        defaultMQProducer.shutdown();
                    }
                }

                @Override
                public void onException(Throwable throwable) {
                    if (j == orderList.size()-1){
                        defaultMQProducer.shutdown();
                    }
                    throwable.getMessage();
                }
            }*/);

            // TimeUnit.SECONDS.sleep(1);

        }

        System.out.println(123);

        defaultMQProducer.shutdown();

    }
}
