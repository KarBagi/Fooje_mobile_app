package com.example.foojeserver.repo;

import com.example.foojeserver.model.Client;
import com.example.foojeserver.model.Restauration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restauration, Long> {

    Restauration findByEmail(String email);

    /*@Query(value = "SELECT RESTAURANT_ID FROM restaurant " +
            "WHERE ACOS(SIN(RADIANS(:userLatitude)) * SIN(RADIANS(latitude)) + " +
            "COS(RADIANS(:userLatitude)) * COS(RADIANS(latitude)) * COS(RADIANS(:userLongitude - longitude))) * 6371000 <= :dystansWMetach",
            nativeQuery = true)
    List<Long> findRestaurantsInDistance(
            @Param("userLatitude") double userLatitude,
            @Param("userLongitude") double userLongitude,
            @Param("dystansWMetach") double dystansWMetach);*/

    @Query(value = "SELECT * FROM restaurant " +
            "WHERE ST_Distance_Sphere(POINT(:clientLongitude, :clientLatitude), POINT(longitude, latitude)) <= :maxDistance; ", nativeQuery = true)
    List<Restauration> findByDistance(
            @Param("clientLatitude") double clientLatitude,
            @Param("clientLongitude") double clientLongitude,
            @Param("maxDistance") int maxDistance);
}