package cn.itcast.user.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jmd
 * @create 2023-03-2023/3/29-17:09
 * @Description：
 */
@Component
@Slf4j
// sentinel提供的，对请求进行解析，并返回一个值，一般用来解析请求的来源。如果没有实现这个接口，默认所有请求返回default
public class HeaderParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getHeader("origin");

        log.info("请求来源是："+origin);
        if (StringUtils.isEmpty(origin)){
            return "blank";
        }

        return origin;
    }
}
