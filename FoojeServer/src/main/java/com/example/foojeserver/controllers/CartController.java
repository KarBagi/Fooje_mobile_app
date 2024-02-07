package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Cart;
import com.example.foojeserver.model.Favorites;
import com.example.foojeserver.repo.CartRepo;
import com.example.foojeserver.repo.ProductsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartRepo cartRepo;

    @GetMapping("/{clientId}/uniqueProducts")
    public ResponseEntity<List<Cart>> getUniqueProductsByClientId(@PathVariable Long clientId) {
        List<Cart> uniqueProducts = cartRepo.findUniqueProductsByClientId(clientId);

        return ResponseEntity.ok(uniqueProducts);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartRepo.findAll();
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {

        Cart savedCart = cartRepo.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCart);
    }


    @GetMapping("/getByClientId/{clientId}")
    public ResponseEntity<List<Cart>> getCartByClientId(@PathVariable long clientId) {
        try {
            List<Cart> cartList = cartRepo.findByClientId(clientId);

            return ResponseEntity.ok(cartList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Transactional
    @DeleteMapping("/remove/{clientId}/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable long clientId, @PathVariable long productId) {
        try {
            cartRepo.deleteByClientIdAndProductId(clientId, productId);
            return ResponseEntity.ok("Usunięto z koszyka");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd podczas usuwania z koszyka");
        }
    }

    @DeleteMapping("/removeByClientId/{clientId}")
    public ResponseEntity<String> removeByClientId(@PathVariable long clientId) {
        try {
            cartRepo.deleteByClientId(clientId);
            return ResponseEntity.ok("Usunięto rekordy z koszyka dla clientId: " + clientId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd podczas usuwania rekordów z koszyka");
        }
    }
}
