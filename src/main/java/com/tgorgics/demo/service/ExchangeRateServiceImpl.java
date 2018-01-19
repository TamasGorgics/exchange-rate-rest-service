package com.tgorgics.demo.service;

import com.tgorgics.demo.enums.ECurrency;
import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.persistence.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRate> getExchangeRatesFor(ECurrency currencyFrom) {
        return this.exchangeRateRepository.findByCurrencyFrom(currencyFrom);
    }

    @Override
    public BigDecimal getRateBetween(BigDecimal amount, String currencyFrom, String currencyTo) {
        return this.exchangeRateRepository.findByCurrencyFromAndCurrencyTo(ECurrency.valueOf(currencyFrom), ECurrency.valueOf(currencyTo)).getBuyingRate().multiply(amount);
    }
}
