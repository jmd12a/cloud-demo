package OrderMsg;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * @author Jmd
 * @create 2023-03-2023/3/18-12:07
 * @Description：
 */

@Data
public class Order {

    private Long orderId;
    private String orderDesc;

    public static List<Order> OrderList(){
        /*
        * 模拟订单的流程，同一个人的订单id相同，相同的订单id下单步骤顺序相同，不同的订单之间是无序的
        * */
        ArrayList<Order> orderList = new ArrayList<>();
        Order order = new Order();

        order = new Order();
        order.setOrderId(1065l);
        order.setOrderDesc("创建订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(1065l);
        order.setOrderDesc("支付订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2032l);
        order.setOrderDesc("创建订单");

        order = new Order();
        order.setOrderId(1065l);
        order.setOrderDesc("订单完成");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2736l);
        order.setOrderDesc("创建订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(1065l);
        order.setOrderDesc("物流跟踪");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2736l);
        order.setOrderDesc("支付订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2736l);
        order.setOrderDesc("完成订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2032l);
        order.setOrderDesc("支付订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2032l);
        order.setOrderDesc("完成订单");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2736l);
        order.setOrderDesc("物流跟踪");
        orderList.add(order);

        order = new Order();
        order.setOrderId(2032l);
        order.setOrderDesc("物流跟踪");
        orderList.add(order);

        return orderList;
    }

}
