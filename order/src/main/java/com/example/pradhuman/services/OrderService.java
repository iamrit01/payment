package com.example.pradhuman.services;

import com.example.pradhuman.entities.Order;
import com.example.pradhuman.utils.UserNotFoundException;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order) throws UserNotFoundException;
    List<Order> getAll();

    List<Order> getAllByUserID(String userId);
    Order getById(String id) throws RuntimeException;
    Order updateOrder(Order order) throws UserNotFoundException;
    boolean deleteOrder(String orderId) throws RuntimeException;
}
