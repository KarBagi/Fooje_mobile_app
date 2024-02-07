package com.example.foojeserver.controllers;

import com.example.foojeserver.model.OrderDetails;
import com.example.foojeserver.repo.OrderDetailsRepo;
import com.example.foojeserver.repo.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @PostMapping("/add")
    public ResponseEntity<String> addOrderDetails(@RequestBody OrderDetails orderDetails) {
        try {
            OrderDetails savedOrderDetails = orderDetailsRepo.save(orderDetails);
            return ResponseEntity.ok("Dodano nowy rekord do order_details z ID: " + savedOrderDetails.getOrdersDetailsId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas dodawania do order_details");
        }
    }

    @GetMapping("/by-orders/{ordersId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrdersId(@PathVariable long ordersId) {
        List<OrderDetails> orderDetailsList = orderDetailsRepo.getOrderDetailsByOrdersId(ordersId);

        if (orderDetailsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orderDetailsList);
        }
    }
}
