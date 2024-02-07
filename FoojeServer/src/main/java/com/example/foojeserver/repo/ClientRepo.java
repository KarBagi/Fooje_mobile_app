package com.example.foojeserver.repo;

import com.example.foojeserver.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}
