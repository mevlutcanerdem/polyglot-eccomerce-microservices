package com.customer.order.service;

import com.customer.order.model.Order;
import com.customer.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public Order saveOrder(Order order){

        // repository
        order.setOrderDate(new Date());

       Order createdOrder = orderRepository.save(order);
    // orderMessageService.send(order);
       return createdOrder;
    }

    public List<Order> listOrders(String customerNumber){
        return orderRepository.findByCustomerNumber(customerNumber);

    }
}
