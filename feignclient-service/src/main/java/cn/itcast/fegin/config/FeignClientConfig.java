package cn.itcast.fegin.config;

import cn.itcast.fegin.FallbackFcatorys.UserClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author Jmd
 * @create 2023-03-2023/3/15-20:36
 * @Description：
 */


// 通过自定义配置类的方式来实现Feign的配置，需要注意的是，这个类不是用 @Configuration标注，而是标注在@EnableFeignClients或者@FeignClient中
//  标注在前者是全局配置，标注在后者是对单独的某个客户端起作用
/*
* 优化feign的两种方式：
*   1.使用连接池来代替默认的HttpUrl。比如HttpClient
*   2.使用BASIC日志级别，或者不使用日志
* */
public class FeignClientConfig {

    @Bean
    public Logger.Level getLoggerLevel(){
        return Logger.Level.BASIC;
    }

    @Bean
    public UserClientFallbackFactory getUserClientFallbackFactory(){
        return new UserClientFallbackFactory();
    }
}
