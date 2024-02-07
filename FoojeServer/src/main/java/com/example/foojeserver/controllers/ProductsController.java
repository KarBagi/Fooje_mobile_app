package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Products;
import com.example.foojeserver.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsRepo productRepo;

    @PostMapping("/add")
    public ResponseEntity<Products> addCart(@RequestBody Products product) {

        Products savedProduct = productRepo.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/byrestaurantid")
    public ResponseEntity<List<Products>> getProductsByRestaurantId(
            @RequestParam("restaurantId") long restaurantId) {

        List<Products> productsList = productRepo.findByRestaurantId(restaurantId);

        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    @GetMapping("/byproductid")
    public ResponseEntity<List<Products>> getProductsByProductId(
            @RequestParam("productId") long productId) {

        List<Products> productsList = productRepo.findByProductId(productId);

        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }
}