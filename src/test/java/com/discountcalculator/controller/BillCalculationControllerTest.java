package com.discountcalculator.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.discountcalculator.integration.fx.ForeignExchangeService;
import com.discountcalculator.integration.fx.FxRate;
import com.discountcalculator.model.BillCalculationRequest;
import com.discountcalculator.model.Item;
import com.discountcalculator.model.ItemCategory;
import com.discountcalculator.model.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class BillCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ForeignExchangeService foreignExchangeService;

    private final String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes());

    @Test
    void calculateBill_ShouldReturnCalculatedResponse() throws Exception {

        when(foreignExchangeService.getFxRatesByBaseCurrency("USD"))
                .thenReturn(getMockedFxRates());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
                        .content(objectMapper.writeValueAsString(getMockedRequestData()))
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.netPayableAmount").isNotEmpty())
                .andExpect(jsonPath("$.billTotal").isNotEmpty());
    }

    private static BillCalculationRequest getMockedRequestData() {
        BillCalculationRequest request = new BillCalculationRequest();
        request.setItems(new ArrayList<>());
        Item p1 = new Item();
        p1.setCategory(ItemCategory.GROCERIES);
        p1.setName("Test");
        p1.setPrice(BigDecimal.valueOf(100));
        request.getItems().add(p1);
        request.setUserType(UserType.Employee);
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("AED");
        return request;
    }

    private static FxRate getMockedFxRates() {

        Map<String, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put("USD", BigDecimal.ONE);
        conversionRates.put("AED", BigDecimal.valueOf(3.67));
        conversionRates.put("PKR", BigDecimal.valueOf(278.51));
        conversionRates.put("EUR", BigDecimal.valueOf(0.95));

        return new FxRate("USD", conversionRates);

    }
}
