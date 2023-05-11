package cn.itcast.fegin.FallbackFcatorys;

import cn.itcast.fegin.feignClients.UserFeignClient;
import cn.itcast.fegin.pojo.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jmd
 * @create 2023-03-2023/3/29-10:09
 * @Description：
 */
@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable throwable) {
        UserFeignClient userFeignClient = new UserFeignClient() {
            @Override
            public User getUser(Long id, User user) {
                // 打印错误信息
                log.error(throwable.toString());
                // 给用户返回一个空的user
                return new User();
            }
        };
        return userFeignClient;
    }
}
