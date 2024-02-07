package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Client;
import com.example.foojeserver.model.Restauration;
import com.example.foojeserver.repo.RestaurantRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurations")
public class RestaurationController {

    @Autowired
    private RestaurantRepo restaurationRepo;

    @GetMapping("/{restaurationId}")
    public ResponseEntity<Restauration> getRestaurationById(@PathVariable Long restaurationId) {
        return restaurationRepo.findById(restaurationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Restauration>> getAllRestaurations() {
        List<Restauration> restaurations = restaurationRepo.findAll();
        return ResponseEntity.ok(restaurations);
    }

    @PostMapping("/add")
    public ResponseEntity<Restauration> addRestauration(@RequestBody Restauration restauration) {
        if (restaurationRepo.existsById(restauration.getRestaurantId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        System.out.println(ResponseEntity.status(HttpStatus.CREATED).body(restauration));
        Restauration savedRestauration = restaurationRepo.save(restauration);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestauration);
    }

    @PostMapping("/login")
    public String login(@RequestBody Restauration restaurationLoginRequest) {
        Restauration existingRestauration = restaurationRepo.findByEmail(restaurationLoginRequest.getEmail());

        if (existingRestauration != null && existingRestauration.getPassword().equals(restaurationLoginRequest.getPassword())) {
            return "Zalogowano jako restauracja;" + existingRestauration.getRestaurantId();
        } else {
            return "Błąd logowania;0";
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Restauration>> getRestaurantsWithinDistance(
            @RequestParam("latitude") double userLatitude,
            @RequestParam("longitude") double userLongitude,
            @RequestParam("distanceInMeters") double distanceInMeters) {

        List<Restauration> nearbyRestaurants = new ArrayList<>();

        List<Restauration> allRestaurants = restaurationRepo.findAll();

        for (Restauration restaurant : allRestaurants) {
            double restaurantDistance = calculateDistance(
                    userLatitude, userLongitude,
                    restaurant.getLatitude(), restaurant.getLongitude());

            if (restaurantDistance <= distanceInMeters) {
                restaurant.setLatitude(0);
                restaurant.setLongitude(0);
                restaurant.setPassword("");
                nearbyRestaurants.add(restaurant);
            }
        }

        return new ResponseEntity<>(nearbyRestaurants, HttpStatus.OK);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}


