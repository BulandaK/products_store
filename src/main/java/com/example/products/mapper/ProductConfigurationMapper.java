package com.example.products.mapper;

import com.example.products.dto.ProductConfigurationDto;
import com.example.products.model.ProductConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductConfigurationMapper {
    @Mapping(source = "parentProduct.id", target = "parentProductId")
    @Mapping(source = "childProduct.id", target = "childProductId")
    @Mapping(source = "childProduct.name", target = "childProductName")
    @Mapping(source = "childProduct.type", target = "childProductType")
    ProductConfigurationDto toDto(ProductConfiguration productConfiguration);

    List<ProductConfigurationDto> toDtoList(List<ProductConfiguration> configurations);
}
