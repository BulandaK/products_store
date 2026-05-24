package com.example.products.dto;

import java.math.BigDecimal;

public record ProductConfigurationDto(
        Long id,
        String name,
        BigDecimal price,
        Long parentProductId,
        Long childProductId,
        String childProductName,
        String childProductType
) {
}
