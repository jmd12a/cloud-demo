package cn.itcast.fegin.feignClients;

import cn.itcast.fegin.FallbackFcatorys.UserClientFallbackFactory;
import cn.itcast.fegin.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jmd
 * @create 2023-03-2023/3/15-19:36
 * @Description：
 */
/*
* 这样就相当于把feignClient抽取到一个单独的模块里（但它并不是一个服务）
* 这个模块中写了调用相应服务的Feign客户端，然后我们可以将这个模块方式引入到其他的服务中，其他服务只要调用这个模块的代码即可
* */
@FeignClient(name="userservice" ,fallbackFactory = UserClientFallbackFactory.class) // 这个Feign客户端调用的是userservice服务的方法
@RequestMapping("user")
public interface UserFeignClient {


    /*
    * 在feign客户端调用其他服务模块的方法时，如果对应的方法要传入多个参数，且方法获取参数时，是使用一个类来接收的（且未用@RequestBoby注解标注）
    * 那么我们在客户端的接口方法，就需要用@SpringQueryMap标注，注解后面加上和原方法中相同的实体参数即可。
    * 注：原方法中无需添加
    * */
    @GetMapping("/{id}")
    User getUser(@PathVariable("id") Long id, @SpringQueryMap User user);

}
