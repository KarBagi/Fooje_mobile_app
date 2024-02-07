package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Cart;
import com.example.foojeserver.model.Orders;
import com.example.foojeserver.repo.CartRepo;
import com.example.foojeserver.repo.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersRepo ordersRepo;

    @PostMapping("/add")
    public ResponseEntity<Orders> addCart(@RequestBody Orders orders) {

        Orders savedOrder = ordersRepo.save(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/maxOrderId/{clientId}/{restaurantId}")
    public ResponseEntity<Long> getMaxOrderId(@PathVariable long clientId, @PathVariable long restaurantId) {
        try {
            Long maxOrderId = ordersRepo.findMaxOrderIdByClientIdAndRestaurantId(clientId, restaurantId);
            if (maxOrderId != null) {
                return ResponseEntity.ok(maxOrderId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public ResponseEntity<List<Orders>> getOrdersByRestaurantId(@PathVariable long restaurantId) {
        List<Orders> orders = ordersRepo.findByRestaurantId(restaurantId);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<Orders>> getOrdersByClientId(@PathVariable long clientId) {
        List<Orders> orders = ordersRepo.findByClientId(clientId);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PutMapping("/{ordersId}/updateState/{newState}")
    public ResponseEntity<String> updateOrderState(@PathVariable long ordersId, @PathVariable String newState) {
        System.out.println("Received request with ordersId: " + ordersId + " and newState: " + newState);
        try {
            Optional<Orders> ordersOptional = ordersRepo.findById(ordersId);

            if (ordersOptional.isPresent()) {
                Orders orders = ordersOptional.get();
                orders.setState(newState);

                ordersRepo.save(orders);

                return ResponseEntity.ok("Zaktualizowano status zamówienia");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono zamówienia o podanym ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas aktualizacji statusu zamówienia");
        }
    }
}
