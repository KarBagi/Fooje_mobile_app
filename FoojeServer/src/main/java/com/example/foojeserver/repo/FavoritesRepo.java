package com.example.foojeserver.repo;

import com.example.foojeserver.model.Favorites;
import com.example.foojeserver.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepo extends JpaRepository<Favorites, Long> {
    List<Favorites> findByClientId(long clientId);
    Optional<Favorites> findByClientIdAndRestaurantId(long clientId, long restaurantId);

}