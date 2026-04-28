package service;

import model.*;
import java.util.*;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order placeOrder(List<FoodItem> items, User user, String address) {

        Order o = new Order(items, address, user);
        orders.add(o);

        String role = (user instanceof PremiumCustomer) ? "PremiumCustomer" : "Customer";

        String logData =
                "OrderID: " + o.getOrderId() +
                " | Role: " + role +
                " | UserID: " + user.getUserId() +
                " | Address: " + address +
                " | Total: " + o.getTotal();

        FileLogger.log("data/orders.txt", logData);

        return o;
    }
}