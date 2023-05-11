package transaction;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * @author Jmd
 * @create 2023-03-2023/3/20-10:36
 * @Description：
 */
public class TranProducer {
    private static String groupId = "tran_pro_group";

    public static void main(String[] args) throws Exception {
        // 事务消息生产者的组id和其他的生产者不能相同
        TransactionMQProducer producer = new TransactionMQProducer(groupId);

        producer.setNamesrvAddr("192.168.153.128:9876");

        // 设置处理回查的线程
        /*
        *  这个ThreadPoolExecutor是一个线程池
        *  @param corePoolSize 线程池中的核心线程数量
        *
         * @param maximumPoolSize 线程池中的最大线程数量
         *
         * @param keepAliveTime 当线程池中的线程数量超过核心线程数量时，空闲线程的停留时间、
         *
         * @param unit 是keepAliveTime参数的时间单位
         *
         * @param workQueue 用来缓存任务的阻塞队列。注：当阻塞队列已满，会创建新的线程执行任务。当阻塞队列已满且达到最大线程数，会拒绝新的任务。
         *
         * @param 线程工厂
        *
        * */
        ExecutorService executor = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                });
        producer.setExecutorService(executor);



        // 设置事务监听器
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String tags = msg.getTags();
                if (StringUtils.equals("Tag-1",tags)){
                    // 提交
                    // 可以在第一次执行时，在数据表或者redis中存入数据；其他执行时，通过唯一的事务id查询数据，如果查到，则直接提交，没有查到，则执行事务成功后提交。
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if (StringUtils.equals("Tag-2",tags)) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("由于发送方提交了UnKnown,所以进行了回查");
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        producer.start();

        for (int i = 0; i < 3; i++) {
            Message message =
                    new Message("tran_Topic", "Tag-"+(i + 1) , ("测试事务消息的发送：" + (i + 1)).getBytes(StandardCharsets.UTF_8));
            // 第二个参数是执行本地事务所需要的参数，可以将这里的变量、对象传到本地事务中去，
            // 即传递给executeLocalTransaction(Message msg, Object arg)的第二个参数arg
            producer.sendMessageInTransaction(message,null);

            Thread.sleep(100);

        }

        for (int i = 0; i < 1000; i++) {
            TimeUnit.SECONDS.sleep(1000);
        }

        producer.shutdown();

    }
}
