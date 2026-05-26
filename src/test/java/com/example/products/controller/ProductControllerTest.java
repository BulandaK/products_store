package com.example.products.controller;

import com.example.products.dto.ProductCommand;
import com.example.products.dto.ProductDto;
import com.example.products.dto.ProductPatchCommand;
import com.example.products.dto.ProductUpdateCommand;
import com.example.products.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @MockitoBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void GivenCommand_WhenAdd_ReturnDto() throws Exception {
        // Given
        ProductCommand command = new ProductCommand("PlayStation", new BigDecimal("2400"), "electronic");
        ProductDto responseDto = new ProductDto(1L, "PlayStation", new BigDecimal("2400"), "electronic", null);

        when(productService.add(any(ProductCommand.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("PlayStation"));
    }

    @Test
    void GivenPageable_WhenGet_ThenReturnPage() throws Exception {
        Pageable page = PageRequest.of(0, 10);
        ProductDto responseDto = new ProductDto(1L, "PlayStation", new BigDecimal("2400"), "electronic", null);
        Page<ProductDto> responsePage = new PageImpl<>(List.of(responseDto));

        when(productService.getProducts(page)).thenReturn(responsePage);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content[0].id").value(1L));

    }

    @Test
    void GivenIdAndUpdateCommand_WhenPut_ReturnProductDto() throws Exception {
        Long id = 1L;
        ProductUpdateCommand command = new ProductUpdateCommand("play station 5", new BigDecimal("2400"), "electronic");
        ProductDto responseDto = new ProductDto(1L,"play station 5", new BigDecimal("2400"), "electronic",null);

        when(productService.update(id,command)).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/products/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("play station 5"));
    }
    @Test
    void GivenIdAndUpdateCommand_WhenPatch_ReturnProductDto() throws Exception {
        Long id = 1L;
        ProductPatchCommand command = new ProductPatchCommand("play station 5", new BigDecimal("2400"), "electronic");
        ProductDto responseDto = new ProductDto(1L,"play station 5", new BigDecimal("2400"), "electronic",null);

        when(productService.patch(id,command)).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/products/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("play station 5"));
    }
    @Test
    void GivenId_WhenDelete_ReturnVoid() throws Exception {
        Long id = 1L;

        doNothing().when(productService).delete(id);

        mockMvc.perform(delete("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(productService, times(1)).delete(id);
    }
}
