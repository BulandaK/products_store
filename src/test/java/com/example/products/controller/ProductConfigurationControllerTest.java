package com.example.products.controller;

import com.example.products.dto.ProductConfigurationDto;
import com.example.products.service.ProductConfigurationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductConfigurationController.class)
public class ProductConfigurationControllerTest {
    @MockitoBean
    private ProductConfigurationService productConfigurationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void GivenProductId_WhenGetConfigurations_ReturnListProductConfigurationDto() throws Exception {
        Long parentId = 1L;
        ProductConfigurationDto dto = new ProductConfigurationDto(1L, "mouse for free", new BigDecimal("0"), 1L, 2L, "mouse", "electronic");
        List<ProductConfigurationDto> listDto = List.of(dto);

        when(productConfigurationService.getConfigurationsByParentProductId(any())).thenReturn(listDto);

        mockMvc.perform(get("/api/v1/productConfiguration/{parentId}", parentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("mouse for free"))
                .andExpect(jsonPath("$[0].price").value("0"));

    }
}
