package com.example.foojeserver.repo;

import com.example.foojeserver.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> getOrderDetailsByOrdersId(long ordersId);
}
