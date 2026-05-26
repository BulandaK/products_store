package com.example.products.repository;

import com.example.products.model.ProductConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductConfigurationRepository extends JpaRepository<ProductConfiguration,Long> {

    List<ProductConfiguration> getProductConfigurationByParentProductId(Long parentProductId);
}
