package com.diego.priceservice.infrastructure.controller;

import com.diego.priceservice.PriceServiceApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PriceServiceApplication.class)
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return priceList 1 at 10:00 on June 14")
    public void testPriceAt10AM() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Should return priceList 2 at 16:00 on June 14")
    public void testPriceAt4PM() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Should return priceList 1 at 21:00 on June 14")
    public void testPriceAt9PM() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Should return priceList 3 at 10:00 on June 15")
    public void testPriceAt10AMDay15() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Should return priceList 4 at 21:00 on June 16")
    public void testPriceAt9PMDay16() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("Should return 404 when no price is found")
    public void testPriceNotFound() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2023-01-01T10:00:00")
                        .param("productId", "99999")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRICE_NOT_FOUND"));
    }

    @Test
    @DisplayName("Should return 400 when parameter is missing")
    public void testMissingParameter() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_PARAMETER"));
    }

    @Test
    @DisplayName("Should return 400 when parameter has invalid format")
    public void testInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "14-06-2020 10:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PARAMETER"));
    }
}
