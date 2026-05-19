package com.example.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductUpdateCommand(
        @NotBlank
        String name,
        @Min(0)
        BigDecimal price,
        @NotBlank
        String type
) {
}
