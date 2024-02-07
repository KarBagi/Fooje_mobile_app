package com.example.foojeserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "favorites")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITES_ID")
    private long favoritesId;

    private long clientId;
    private long restaurantId;

    public long getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(long favoritesId) {
        this.favoritesId = favoritesId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
