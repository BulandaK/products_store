package com.example.products.mapper;

import com.example.products.model.Product;
import com.example.products.dto.ProductDto;
import com.example.products.dto.ProductCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    @Mapping(target = "productConfigurations", ignore = true)
    Product toEntity(ProductCommand command);
}