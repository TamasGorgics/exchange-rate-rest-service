package com.tgorgics.demo.repository;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.persistence.repository.ExchangeRateRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void whenFindByCurrencyFrom_thenReturnExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom("EUR");
        exchangeRate.setCurrencyTo("HUF");
        exchangeRate.setBuyingRate(new BigDecimal(289.15));
        exchangeRate.setSellingRate(new BigDecimal(300.12));
        entityManager.persist(exchangeRate);

        List<ExchangeRate> found = exchangeRateRepository.findByCurrencyFrom("EUR");

        // the list should contain only 1 element
        Assertions.assertThat(found.size()).isEqualTo(1);

        // get that element
        ExchangeRate foundExchangeRate = found.get(0);

        // check all fields
        Assertions.assertThat(foundExchangeRate.getBuyingRate()).isEqualTo(exchangeRate.getBuyingRate());
        Assertions.assertThat(foundExchangeRate.getSellingRate()).isEqualTo(exchangeRate.getSellingRate());
        Assertions.assertThat(foundExchangeRate.getCurrencyFrom()).isEqualTo(exchangeRate.getCurrencyFrom());
        Assertions.assertThat(foundExchangeRate.getCurrencyTo()).isEqualTo(exchangeRate.getCurrencyTo());
    }

    @Test
    public void whenFindByCurrencyFromAndCurrencyTo_thenReturnExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom("USD");
        exchangeRate.setCurrencyTo("HUF");
        exchangeRate.setBuyingRate(new BigDecimal(259.78));
        exchangeRate.setSellingRate(new BigDecimal(270.56));
        entityManager.persist(exchangeRate);

        ExchangeRate foundExchangeRate = exchangeRateRepository.findByCurrencyFromAndCurrencyTo("USD", "HUF");

        Assertions.assertThat(foundExchangeRate.getBuyingRate()).isEqualTo(exchangeRate.getBuyingRate());
        Assertions.assertThat(foundExchangeRate.getSellingRate()).isEqualTo(exchangeRate.getSellingRate());
        Assertions.assertThat(foundExchangeRate.getCurrencyFrom()).isEqualTo(exchangeRate.getCurrencyFrom());
        Assertions.assertThat(foundExchangeRate.getCurrencyTo()).isEqualTo(exchangeRate.getCurrencyTo());
    }

    @Test
    public void whenNotFindByCurrencyFrom_thenReturnEmptyList() {
        List<ExchangeRate> found = exchangeRateRepository.findByCurrencyFrom("HUF");

        Assertions.assertThat(found.size()).isEqualTo(0);
    }

    @Test
    public void whenNotFindByCurrencyFromAndCurrencyTo_thenReturnNull() {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findByCurrencyFromAndCurrencyTo("HUF", "USD");

        Assertions.assertThat(foundExchangeRate).isEqualTo(null);
    }
}
