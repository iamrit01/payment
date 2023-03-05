package com.example.pradhuman.services;

import com.example.pradhuman.entities.Order;
import com.example.pradhuman.entities.OrderStatus;
import com.example.pradhuman.repositories.OrderRepository;
import com.example.pradhuman.repositories.UserRepository;
import com.example.pradhuman.utils.Jutil;
import com.example.pradhuman.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Order> getAllByUserID(String userId) {
        return orderRepository.getAllOrdersByUserId(userId);
    }

    @Override
    public Order createOrder(Order order) throws UserNotFoundException {
         if(!userRepository.findById(order.getUserId()).isPresent())
             throw new UserNotFoundException(String.format("User not found for id : %s", order.getUserId()));
        if(Jutil.isPriceValid(order)){
            order.setOrderId(UUID.randomUUID().toString());
            order.setStatus(OrderStatus.SUCCESS.getStatus());
            orderRepository.save(order);
        }else {
            order.setStatus(OrderStatus.FAIL.getStatus());
            throw new RuntimeException("Price Validation Failed price should be > 0");
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Order getById(String id) throws RuntimeException {
        if (orderRepository.findById(id).isPresent())
            return orderRepository.findById(id).get();
        throw new RuntimeException(String.format("Order not found for id : %s", id));
    }

    @Override
    public Order updateOrder(Order order) throws UserNotFoundException {
        if(orderRepository.findById(order.getOrderId()).isPresent() &&
         userRepository.findById(order.getUserId()).isPresent()){
            Order oldOrder = orderRepository.findById(order.getOrderId()).get();
            Jutil.getUpdatedOrder(oldOrder, order);
            if(oldOrder.getStatus().equals(OrderStatus.FAIL))
                throw new RuntimeException("Items price Validation failed. price should be > 0");
        }
        throw new UserNotFoundException(String.format("Oder or user for orderId : %s and userId : %s not found",
                order.getOrderId(),order.getUserId()));
    }

    @Override
    public boolean deleteOrder(String orderId) throws RuntimeException {
        if(orderRepository.findById(orderId).isPresent()) {
            Order order = orderRepository.findById(orderId).get();
            order.setDeleted(true);
            orderRepository.save(order);
            return true;
        }
        throw new RuntimeException(String.format("Order not found for id : %s", orderId));
    }
}
