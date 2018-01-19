package com.tgorgics.demo.service;

import com.tgorgics.demo.enums.ECurrency;
import com.tgorgics.demo.persistence.model.ExchangeRate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional(readOnly = true)
public interface ExchangeRateService {

    List<ExchangeRate> getExchangeRatesFor(ECurrency currencyFrom);

    BigDecimal getRateBetween(BigDecimal amount, String currencyFrom, String currencyTo);
}
