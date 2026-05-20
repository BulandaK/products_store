package com.example.products.repository;


import com.example.products.model.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndType(String name, String type);

    boolean existsByNameAndType(@NotBlank String name, @NotBlank String type);
}
