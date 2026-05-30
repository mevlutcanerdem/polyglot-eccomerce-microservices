package com.customer.order.controller;

import com.customer.order.model.Order;
import com.customer.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    // create order
    @PostMapping
    public Order createOrder(@RequestBody Order order){
        return  orderService.saveOrder(order);
    }

    // list orders

    @GetMapping
    public List<Order> listOrders(@RequestParam String customerNumber){
        return orderService.listOrders(customerNumber);

    }
}
