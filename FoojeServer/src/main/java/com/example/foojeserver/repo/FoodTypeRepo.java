package com.example.foojeserver.repo;

import com.example.foojeserver.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepo extends JpaRepository<FoodType, Long> {
    // Tutaj możesz dodać dodatkowe metody zapytań niestandardowych, jeśli są potrzebne

    // Przykład zapytania niestandardowego - znajdź klienta po adresie email
}
