package cn.itcast.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
* openFeign调用User中的服务，是去注册中心拉取地址，然后给User服务发送了请求，和user服务本身无关。
* @EnableDiscoveryClient是将该服务配置在注册中心，并且能够使用DiscoveryClient类，通过服务的名称获取服务地址
* 只要User服务中不调用其他服务，就需要引入相关依赖，配置相关内容
* */
@MapperScan("cn.itcast.user.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
