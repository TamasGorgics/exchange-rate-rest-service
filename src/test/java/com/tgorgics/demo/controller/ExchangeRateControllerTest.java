package com.tgorgics.demo.controller;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.service.ExchangeRateService;
import com.tgorgics.demo.service.request.ConversionRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeRateController.class)
public class ExchangeRateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Before
    public void setUp() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom("NOK");
        exchangeRate.setCurrencyTo("HUF");
        exchangeRate.setBuyingRate(BigDecimal.valueOf(35.15));
        exchangeRate.setSellingRate(BigDecimal.valueOf(31.72));

        ConversionRequest buyingRequest = new ConversionRequest();
        buyingRequest.setAmount(BigDecimal.valueOf(20));
        buyingRequest.setCurrencyFrom("NOK");
        buyingRequest.setCurrencyTo("HUF");
        buyingRequest.setRateType("BUYING");

        ConversionRequest sellingRequest = new ConversionRequest();
        sellingRequest.setAmount(BigDecimal.valueOf(20));
        sellingRequest.setCurrencyFrom("NOK");
        sellingRequest.setCurrencyTo("HUF");
        sellingRequest.setRateType("SELLING");

        Mockito.when(exchangeRateService.getExchangeRatesFor("NOK")).thenReturn(Arrays.asList(exchangeRate));
        Mockito.when(exchangeRateService.getRateBetween("NOK", "HUF")).thenReturn(exchangeRate);
        Mockito.when(exchangeRateService.convert(buyingRequest)).thenReturn(BigDecimal.valueOf(31.72).multiply(BigDecimal.valueOf(20)));
        Mockito.when(exchangeRateService.convert(sellingRequest)).thenReturn(BigDecimal.valueOf(35.15).multiply(BigDecimal.valueOf(20)));
    }

    @Test
    public void whenGetRatesFor_thenReturnJson() throws Exception {
        mockMvc.perform(get("/rates?currencyFrom=NOK")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].currencyFrom", is("NOK")))
                .andExpect(jsonPath("$[0].currencyTo", is("HUF")))
                .andExpect(jsonPath("$[0].buyingRate", is(35.15)))
                .andExpect(jsonPath("$[0].sellingRate", is(31.72)));
    }

}
