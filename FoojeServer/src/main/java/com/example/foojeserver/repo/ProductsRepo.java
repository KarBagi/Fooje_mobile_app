package com.example.foojeserver.repo;

import com.example.foojeserver.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepo extends JpaRepository<Products, Long> {
    List<Products> findByRestaurantId(long restaurantId);
    List<Products> findByProductId(long productId);
}
