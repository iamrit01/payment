package com.example.pradhuman.repositories;

import com.example.pradhuman.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "select * from order1 o where o.deleted is false ",nativeQuery = true)
    List<Order> getAllOrders();

    @Query(value = "select * from order1 o where o.userId = :userId and o.deleted is false", nativeQuery = true)
    List<Order> getAllOrdersByUserId(@Param("userId") String userId);
}
