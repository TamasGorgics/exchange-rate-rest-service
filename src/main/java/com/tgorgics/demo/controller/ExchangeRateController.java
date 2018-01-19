package com.tgorgics.demo.controller;

import com.tgorgics.demo.enums.ECurrency;
import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rates")
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public List<ExchangeRate> getRatesFor(@RequestParam("currencyFrom") String currencyFrom) {
        return this.exchangeRateService.getExchangeRatesFor(ECurrency.valueOf(currencyFrom));
    }

    @GetMapping("/converter") //TODO: át kell adni a buying vagy sellinget, kell egy currencyFactory component
    public BigDecimal getRateBetween(@RequestParam("amount") BigDecimal amount, @RequestParam("currencyFrom") String currencyFrom, @RequestParam("currencyTo") String currencyTo) {
        return this.exchangeRateService.getRateBetween(amount, currencyFrom, currencyTo);
    }
}
