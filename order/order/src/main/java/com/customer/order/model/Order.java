package com.customer.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity // it's a class representing a table in a database
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String customerNumber;
    String productId;
    int quantity;
    Date orderDate;

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
