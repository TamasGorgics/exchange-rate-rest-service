package com.tgorgics.demo.controller;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.service.ExchangeRateService;
import com.tgorgics.demo.service.request.ConversionRequest;
import com.tgorgics.demo.validation.Currency;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rates")
@Validated
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public List<ExchangeRate> getRatesFor(@RequestParam("currencyFrom") @NotEmpty @Currency String currencyFrom) {
        return this.exchangeRateService.getExchangeRatesFor(currencyFrom);
    }

    @GetMapping("/converter")
    public BigDecimal convert(@Valid @RequestBody ConversionRequest request) {
        return this.exchangeRateService.convert(request);
    }
}
