package service;

import model.*;
import java.util.*;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order placeOrder(List<FoodItem> items, String userId) {
        Order order = new Order(items);
        orders.add(order);

        FileLogger.log("orders.txt",
                "UserID: " + userId +
                        " | OrderID: " + order.getOrderId() +
                        " | Total: " + order.getTotal());

        System.out.println("Order placed successfully!");
        return order;
    }
}