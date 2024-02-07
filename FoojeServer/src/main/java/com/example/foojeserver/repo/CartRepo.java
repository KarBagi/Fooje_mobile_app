package com.example.foojeserver.repo;

import com.example.foojeserver.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    List<Cart> findByClientId(Long clientId);
    @Query(value = "SELECT * FROM cart " + "WHERE c.clientId = :CLIENT_ID", nativeQuery = true)
    List<Cart> findUniqueProductsByClientId(@Param("CLIENT_ID") Long clientId);
    void deleteByClientIdAndProductId(long clientId, long productId);

    @Transactional
    void deleteByClientId(long clientId);
}