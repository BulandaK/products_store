package com.example.products.service;


import com.example.products.dto.ProductConfigurationDto;
import com.example.products.mapper.ProductConfigurationMapper;
import com.example.products.model.Product;
import com.example.products.model.ProductConfiguration;
import com.example.products.repository.ProductConfigurationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductConfigurationServiceTest {

    ProductConfigurationMapper mapper;
    ProductConfigurationRepository productConfigurationRepository;
    ProductConfigurationService productConfigurationService;

    @BeforeEach
    void setup() {
        this.mapper = Mappers.getMapper(ProductConfigurationMapper.class);
        this.productConfigurationRepository = Mockito.mock(ProductConfigurationRepository.class);
        productConfigurationService = new ProductConfigurationService(productConfigurationRepository, mapper);
    }

    @Test
    void GivenProductId_WhenGetConfiguration_ReturnListOfConfiguration() {
        //given
        Long parentId = 1L;
        Product laptop = new Product(1L, "Lenovo laptop", new BigDecimal("2000"), "electronic", null);
        Product mouse = new Product(1L, "mouse", new BigDecimal("20"), "electronic", null);
        ProductConfiguration configuration = new ProductConfiguration(1L, "mouse in combo", new BigDecimal("20"), laptop, mouse);
        List<ProductConfiguration> configurationList = List.of(configuration);

        when(productConfigurationRepository.getProductConfigurationByParentProductId(parentId)).thenReturn(configurationList);
        //then

        List<ProductConfigurationDto> result = productConfigurationService.getConfigurationsByParentProductId(parentId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.getFirst().id()),
                () -> Assertions.assertEquals("mouse in combo", result.getFirst().name()),
                () -> Assertions.assertEquals(new BigDecimal("20"), result.getFirst().price())

        );
    }

}
