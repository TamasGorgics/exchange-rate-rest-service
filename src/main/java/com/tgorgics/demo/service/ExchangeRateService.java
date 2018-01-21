package com.tgorgics.demo.service;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.service.request.ConversionRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional(readOnly = true)
public interface ExchangeRateService {

    List<ExchangeRate> getExchangeRatesFor(String currencyFrom);

    ExchangeRate getRateBetween(String currencyFrom, String currencyTo);

    BigDecimal convert(ConversionRequest request);
}
