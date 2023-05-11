package cn.itcast.order.web;

import cn.itcast.fegin.feignClients.UserFeignClient;
import cn.itcast.fegin.pojo.User;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
public class OrderController {

   @Resource
   private OrderService orderService;

   //@Resource
   //private RestTemplate restTemplate;

   @Resource
   private UserFeignClient userFeignClient;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId
                                    //@RequestHeader(value = "mubiao",required = false) String mubiao
                                    ) {
        // Order order = orderService.queryOrderById(orderId);
        Order order = new Order();
        order.setId(101l);
        order.setName("苹果14promax");
        order.setNum(2);
        order.setPrice(8966l);
        order.setUserId(10l);
        /*String url = "http://userserver/user/1"; // 使用在eureka中注册的服务名来代替ip和端口号
        User user = restTemplate.getForObject(url, User.class);*/
        User user = new User();
        user.setUsername("张三");
        user.setAddress("西安高新区");
        order.setUser(userFeignClient.getUser(1l,user));
        // order.getUser().setAddress(mubiao);
        // 根据id查询订单并返回
        return order;
    }
}
