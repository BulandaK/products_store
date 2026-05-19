package com.example.products.dto;

import com.example.products.model.ProductConfiguration;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        Double price,
        String type,
        List<ProductConfiguration> productConfigurations
) {
}
