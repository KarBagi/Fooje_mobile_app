package com.example.foojeserver.controllers;

import com.example.foojeserver.model.Favorites;
import com.example.foojeserver.repo.FavoritesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesRepo favoritesRepo;

    @GetMapping("/{favoritesId}")
    public ResponseEntity<Favorites> getFavoritesById(@PathVariable Long favoritesId) {
        return favoritesRepo.findById(favoritesId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Favorites>> getAllFavorites() {
        List<Favorites> favorites = favoritesRepo.findAll();
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(@RequestBody Favorites newFavorite) {
        try {
            Favorites savedFavorite = favoritesRepo.save(newFavorite);
            return new ResponseEntity<>("Favorite added successfully with ID: " + savedFavorite.getFavoritesId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding favorite: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{clientId}/{restaurantId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable long clientId,
                                                      @PathVariable long restaurantId) {
        try {
            Optional<Favorites> favorites = favoritesRepo.findByClientIdAndRestaurantId(clientId, restaurantId);

            if (favorites.isPresent()) {
                favoritesRepo.delete(favorites.get());
                return ResponseEntity.ok("Usunięto z ulubionych");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono rekordu do usunięcia");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas usuwania z ulubionych");
        }
    }

    @GetMapping("/byClientId")
    public ResponseEntity<List<Favorites>> getFavoritesByClientId(
            @RequestParam("clientId") long clientId) {

        List<Favorites> restaurantIds = favoritesRepo.findByClientId(clientId);

        return new ResponseEntity<>(restaurantIds, HttpStatus.OK);
    }
}

