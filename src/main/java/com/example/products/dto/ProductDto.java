package com.example.products.dto;

import com.example.products.model.ProductConfiguration;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        String type,
        List<ProductConfiguration> productConfigurations
) {
}
