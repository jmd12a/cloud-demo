package cn.itcast.order;

import cn.itcast.fegin.FallbackFcatorys.UserClientFallbackFactory;
import cn.itcast.fegin.config.FeignClientConfig;
import cn.itcast.fegin.feignClients.UserFeignClient;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {UserFeignClient.class},defaultConfiguration = FeignClientConfig.class)
// 加载启动中类，框架会扫描所有使用@FeignClient注解标注的类，并加载到IOC容器中
// 通过clients指定这个服务要使用哪些client客户端，因为客户端所在的模块不在启动类默认扫描的包中，需要重新指定一下
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /*@Bean
    @LoadBalanced // 负载均衡，让restTemplate在几个相同服务中，使用算法，轮换使用，提高效率。
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    // 配置负载均衡优化策略的算法，这里选择的是随机算法
    // 注：通过这个方式配置的负载均衡优化策略，是在整个order-service都起作用的，是全局配置。
    // @Bean
    public IRule rule(){
        return new RandomRule();
    }*/
}