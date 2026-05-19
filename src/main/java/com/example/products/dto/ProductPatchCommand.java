package com.example.products.dto;

import java.math.BigDecimal;

public record ProductPatchCommand(
        String name,
        BigDecimal price,
        String type
) {
}
