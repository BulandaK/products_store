package com.example.products.controller;

import com.example.products.dto.ProductConfigurationDto;
import com.example.products.service.ProductConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/productsConfiguration")
public class ProductConfigurationController {
    private final ProductConfigurationService productConfigurationService;

    @GetMapping("/{parentId}")
    List<ProductConfigurationDto> getConfigurations(@PathVariable Long parentId) {
        return productConfigurationService.getConfigurationsByParentProductId(parentId);
    }


}
