package com.example.products.service;

import com.example.products.dto.ProductCommand;
import com.example.products.dto.ProductDto;
import com.example.products.dto.ProductPatchCommand;
import com.example.products.dto.ProductUpdateCommand;
import com.example.products.mapper.ProductMapper;
import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto add(ProductCommand productCommand) {
        Product product = productMapper.toEntity(productCommand);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Product> toRemove = productRepository.findById(id);
        toRemove.ifPresent(productRepository::delete);
    }

    @Transactional
    public ProductDto update(Long id, @Valid ProductUpdateCommand command) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setPrice(command.price());
                    product.setType(command.type());
                    product.setName(command.name());
                    return productMapper.toDto(product);
                }).orElseThrow();
    }

    public ProductDto patch(Long id, @Valid ProductPatchCommand command) {
        Product product = productRepository.findById(id).orElseThrow();

        if(command.name() != null) {
            product.setName(command.name());
        }

        if (command.price() != null) {
            product.setPrice(command.price());
        }
        if (command.type() != null) {
            product.setType(command.type());
        }

        return productMapper.toDto(product);
    }
}
