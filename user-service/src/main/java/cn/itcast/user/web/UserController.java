package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
// @RefreshScope //刷新这个控制器方法从nacos配置中心或者的配置的热更新，无需重启就可以更新服务
public class UserController {

    @Autowired
    private UserService userService;


   /* @Value("${pattern.dateformat}") // 使用value在nacos配置中心的配置文件中取值
    private String dateformat;*/

    @Resource
    private PatternProperties patternProperties;

    @GetMapping("nowTime")
    public String getDate(){
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat()));
        return nowDate;
    }

    @GetMapping("value")
    public PatternProperties getPatternProperties(){
        return patternProperties;
    }

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    // @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id, User user) throws InterruptedException {
        log.info("getUser方法被调用了");
        user.setId(id);
        return user;
    }

    @GetMapping
    public User testGetParam(User user){
        /*System.out.println(map.size());
        map.entrySet().stream().forEach(entry ->{
            System.out.println(entry.getKey()+":"+entry.getValue());
        });*/
        System.out.println(user);
        return new User();
    }


}
