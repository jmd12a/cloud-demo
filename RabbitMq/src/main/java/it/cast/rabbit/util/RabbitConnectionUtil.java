package it.cast.rabbit.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jmd
 * @create 2023-03-2023/3/17-15:13
 * @Description：
 */
public class RabbitConnectionUtil {
    private static ConnectionFactory connectionFactory;

    public static Connection getConnection() throws IOException, TimeoutException {
        if (connectionFactory == null){
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.153.128"); // 设置服务器ip
            /*
            * 这里连接的端口号是5672，是服务器的端口号；而浏览器访问管理页面连接的端口号是15672，两个是不同的
            * */
            connectionFactory.setPort(5672);   // 设置端口号
            // connectionFactory.setVirtualHost("StudyMq"); // 设置虚拟机名称，一个服务器可以有多个虚拟机，每个虚拟机都相当于
            connectionFactory.setUsername("root");
            connectionFactory.setPassword("122964");
        }

        Connection connection = connectionFactory.newConnection();

        return connection;

    }

}
