package com.tgorgics.demo.persistence.repository;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    List<ExchangeRate> findByCurrencyFrom(String currencyFrom);

    ExchangeRate findByCurrencyFromAndCurrencyTo(String currencyFrom, String currencyTo);
}
