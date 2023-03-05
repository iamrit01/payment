package com.example.pradhuman.utils;

import com.example.pradhuman.entities.Order;

import java.util.List;

public class OrderResponse extends BaseEntityResponse<Order>{

    public OrderResponse(Order order){
        super(order);
    }

    public OrderResponse(List<Order> orders){
        super(orders);
    }
}
