package com.example.products.service;

import com.example.products.dto.ProductConfigurationDto;
import com.example.products.mapper.ProductConfigurationMapper;
import com.example.products.model.ProductConfiguration;
import com.example.products.repository.ProductConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductConfigurationService {

    private final ProductConfigurationRepository productConfigurationRepository;
    private final ProductConfigurationMapper productConfigurationMapper;


    public List<ProductConfigurationDto> getConfigurationsByParentProductId(Long parentId) {
        List<ProductConfiguration> configurations = productConfigurationRepository.getProductConfigurationByParentProductId(parentId);
        return productConfigurationMapper.toDtoList(configurations);
    }
}
