package cn.itcast.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jmd
 * @create 2023-03-2023/3/15-16:53
 * @Description：
 */

@Data
@ConfigurationProperties("pattern") // 通过这个注解，spring会将配置文件中和以pattern为前缀的配置属性，根据配置名称，注入到相同名称的属性中
@Component
public class PatternProperties {

    // 这里会注入配置文件中的pattern.dateformat
    private String dateformat;

    // private String test;

    /*
    * 注：通过这种方式从配置文件中获取的属性值，无需其他注解，可以自动刷新，能够实现热更新
    *    放到nacos配置中心，自动刷新的数据，一般都是自定义的关键配置
    * */
}
