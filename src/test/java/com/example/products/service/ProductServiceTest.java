package com.example.products.service;

import com.example.products.dto.ProductCommand;
import com.example.products.dto.ProductDto;
import com.example.products.dto.ProductPatchCommand;
import com.example.products.dto.ProductUpdateCommand;
import com.example.products.exception.ProductAlreadyExistsException;
import com.example.products.exception.ProductException;
import com.example.products.exception.ProductNotFoundException;
import com.example.products.mapper.ProductMapper;
import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Nested
    class AddProductTest {
        @Test
        void GivenProductCommand_WhenAdd_ReturnProductDto() {
            //given
            ProductCommand productCommand = new ProductCommand("play station", new BigDecimal("2400.00"), "electronic");
            Product entity = new Product(null, "play station", new BigDecimal("2400.00"), "electronic", null);
            Product savedEntity = new Product(1L, "play station", new BigDecimal("2400.00"), "electronic", null);
            ProductDto productDto = new ProductDto(1L, "play station", new BigDecimal("2400.00"), "electronic", null);

            when(productRepository.existsByNameAndType(productCommand.name(), productCommand.type())).thenReturn(false);
            when(productMapper.toEntity(any())).thenReturn(entity);
            when(productRepository.save(any())).thenReturn(savedEntity);
            when(productMapper.toDto(any())).thenReturn(productDto);
            //when

            ProductDto result = productService.add(productCommand);

            Assertions.assertAll(
                    () -> Assertions.assertNotNull(result),
                    () -> Assertions.assertEquals("play station", result.name()),
                    () -> Assertions.assertEquals("electronic", result.type()),
                    () -> Assertions.assertEquals(1L, result.id())
            );
        }

        @Test
        void GivenProductCommand_WhenAdd_ThrowProductAlreadyExistException() {
            ProductCommand productCommand = new ProductCommand("play station", new BigDecimal("2400.00"), "electronic");
            when(productRepository.existsByNameAndType(productCommand.name(), productCommand.type())).thenReturn(true);

            ProductException exception = Assertions.assertThrows(ProductAlreadyExistsException.class,
                    () -> productService.add(productCommand));

            Assertions.assertEquals("Product with this name and type already exists", exception.getMessage());
            verify(productRepository, never()).save(any());
            verifyNoMoreInteractions(productRepository);
        }
    }

    @Nested
    class GetProductsTest {
        @Test
        void GivenPageable_WhenGetProducts_ThenReturnPageProductDto() {
            Pageable pageable = PageRequest.of(0, 10);
            Product product = new Product(1L, "play station", new BigDecimal("2400.00"), "electronic", null);
            ProductDto dto = new ProductDto(1L, "play station", new BigDecimal("2400.00"), "electronic", null);
            Page<Product> productsPage = new PageImpl<>(List.of(product));

            when(productRepository.findAll(pageable)).thenReturn(productsPage);
            when(productMapper.toDto(product)).thenReturn(dto);

            Page<ProductDto> result = productService.getProducts(pageable);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(1L, result.getContent().getFirst().id()),
                    () -> Assertions.assertEquals("play station", result.getContent().getFirst().name()),
                    () -> Assertions.assertEquals("electronic", result.getContent().getFirst().type())
            );
        }
    }

    @Nested
    class DeleteProduct {
        @Test
        void GivenProductId_WhenDeleteProduct_ReturnVoid() {
            //given
            Long id = 1L;
            Optional<Product> optionalProduct = Optional.of(new Product(1L, "play station", new BigDecimal("2400.00"), "electronic", null));
            when(productRepository.findById(id)).thenReturn(optionalProduct);
            //when
            productService.delete(id);
            //then
            verify(productRepository, times(1)).delete(optionalProduct.get());
        }

        @Test
        void GivenProductId_WhenDelete_ThrowError() {
            Long id = 1L;
            Optional<Product> empty = Optional.empty();
            when(productRepository.findById(id)).thenReturn(empty);

            ProductException exception = Assertions.assertThrows(ProductNotFoundException.class,
                    () -> productService.delete(id));

            Assertions.assertEquals("Product not found", exception.getMessage());
            verify(productRepository, never()).save(any());
            verifyNoMoreInteractions(productRepository);
        }
    }

    @Nested
    class UpdateProduct {
        @Test
        void GivenProductUpdateCommand_WhenUpdate_ReturnDto() {
            Long id = 1L;
            ProductUpdateCommand command = new ProductUpdateCommand("xbox", new BigDecimal("2200.00"), "electronic");
            Product product = new Product(1L, "play station", new BigDecimal("2400.00"), "electronic", null);
            ProductDto dto = new ProductDto(1L, "xbox", new BigDecimal("2200.00"), "console", null);

            when(productRepository.existsByNameAndType(command.name(), command.type())).thenReturn(false);
            when(productRepository.findById(any())).thenReturn(Optional.of(product));
            when(productMapper.toDto(any())).thenReturn(dto);

            ProductDto result = productService.update(id, command);

            Assertions.assertAll(
                    () -> Assertions.assertEquals("xbox", result.name()),
                    () -> Assertions.assertEquals("console", result.type())
            );

        }

        @Test
        void GivenProductId_WhenUpdate_ThrowProductNotFoundException() {
            Long id = 2L;
            ProductUpdateCommand command = new ProductUpdateCommand("play station", new BigDecimal("2200.00"), "electronic");
            when(productRepository.findById(any())).thenReturn(Optional.empty());

            ProductException exception = Assertions.assertThrows(ProductNotFoundException.class,
                    () -> productService.update(id, command));

            Assertions.assertEquals("Product with id %d not found".formatted(id), exception.getMessage());
            verify(productRepository, never()).save(any());

        }

        @Test
        void GivenProductUpdateCommand_WhenUpdate_ThrowProductAlreadyExistsException() {
            Long id = 1L;
            ProductUpdateCommand command = new ProductUpdateCommand("xbox", new BigDecimal("2200.00"), "electronic");

            when(productRepository.existsByNameAndType(anyString(), anyString())).thenReturn(true);

            ProductException exception = Assertions.assertThrows(ProductAlreadyExistsException.class,
                    () -> productService.update(id, command));

            Assertions.assertEquals("Product with this name and type already exists", exception.getMessage());
            verify(productRepository, never()).save(any());
            verifyNoMoreInteractions(productRepository);
        }
    }

    @Nested
    class PatchProduct {
        @Test
        void GivenPatchCommand_WhenPatch_ReturnProductDto() {
            Long id = 1L;
            ProductPatchCommand command = new ProductPatchCommand(null, new BigDecimal("1999.99"), null);
            Product product = new Product(1L, "play station", new BigDecimal("2400.00"), "electronic", null);
            ProductDto dto = new ProductDto(1L, "play station", new BigDecimal("1999.99"), "electronic", null);

            when(productRepository.findById(any())).thenReturn(Optional.of(product));
            when(productMapper.toDto(any())).thenReturn(dto);

            ProductDto result = productService.patch(id, command);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(new BigDecimal("1999.99"), result.price())
            );
        }

        @Test
        void GivenProductId_WhenPatch_ThrowProductNotFound() {
            Long id = 1L;


            when(productRepository.findById(any())).thenReturn(Optional.empty());

            ProductException exception = Assertions.assertThrows(ProductNotFoundException.class,
                    () -> productService.patch(id,new ProductPatchCommand(null,null,null)));

            Assertions.assertEquals("Product not found",exception.getMessage());
            verify(productRepository,never()).save(any());

        }
    }
}
