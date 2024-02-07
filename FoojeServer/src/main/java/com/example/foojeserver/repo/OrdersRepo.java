package com.example.foojeserver.repo;

import com.example.foojeserver.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
    @Query("SELECT MAX(o.ordersId) FROM Orders o WHERE o.clientId = :clientId AND o.restaurantId = :restaurantId")
    Long findMaxOrderIdByClientIdAndRestaurantId(@Param("clientId") long clientId, @Param("restaurantId") long restaurantId);

    List<Orders> findByRestaurantId(long restaurantId);
    List<Orders> findByClientId(long clientId);
}