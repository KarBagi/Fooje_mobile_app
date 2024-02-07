package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Client;
import com.example.foojeserver.model.FoodType;
import com.example.foojeserver.repo.ClientRepo;
import com.example.foojeserver.repo.FoodTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-type")
public class FoodTypeController {

    @Autowired
    private FoodTypeRepo foodTypeRepo;

    @GetMapping("/{foodTypeId}")
    public ResponseEntity<FoodType> getClientById(@PathVariable Long foodTypeId) {
        return foodTypeRepo.findById(foodTypeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<FoodType>> getAllClients() {
        List<FoodType> foodTypes = foodTypeRepo.findAll();
        return ResponseEntity.ok(foodTypes);
    }

    @PostMapping("/add")
    public ResponseEntity<FoodType> addCart(@RequestBody FoodType foodType) {
        FoodType savedFoodType = foodTypeRepo.save(foodType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFoodType);
    }
}
