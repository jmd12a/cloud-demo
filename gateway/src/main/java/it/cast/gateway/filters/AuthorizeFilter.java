package it.cast.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Jmd
 * @create 2023-03-2023/3/16-12:37
 * @Description：
 */

// @Component // 注入到容器中
@Order(-1) // 设置顺序
// GlobalFilter 全局过滤器，和默认过滤器的区别是默认过滤器只有定义好的几种，而全局过滤器可以自定义过滤逻辑
// 三种过滤器的排序方式：先看值，值越小优先级越高；当值相同时，按照默认过滤器，路由过滤器和全局过滤器的方式排序
public class AuthorizeFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();
        String authorization = params.getFirst("authorization");
        if (authorization != null){
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED); // 设置状态码为未登录
        return response.setComplete(); // 直接设置相响应未完成也就是没有登录成功
    }
}
