package com.tgorgics.demo.persistence.repository;

import com.tgorgics.demo.enums.ECurrency;
import com.tgorgics.demo.persistence.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    List<ExchangeRate> findByCurrencyFrom(ECurrency currencyFrom);

    ExchangeRate findByCurrencyFromAndCurrencyTo(ECurrency currencyFrom, ECurrency currencyTo);
}
