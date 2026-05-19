package com.example.products.controller;

import com.example.products.dto.*;
import com.example.products.model.Product;
import com.example.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductDto add(@Valid @RequestBody ProductCommand productCommand) {
        return productService.add(productCommand);
    }

    @GetMapping
    public Page<ProductDto> get(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id ,@Valid @RequestBody ProductUpdateCommand command) {
        return productService.update(id,command);
    }

    @PatchMapping("/{id}")
    public ProductDto patch(@PathVariable Long id, @Valid @RequestBody ProductPatchCommand command) {
        return productService.patch(id, command);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }


//
//    public ProductDto update() {
//
//    }
}
