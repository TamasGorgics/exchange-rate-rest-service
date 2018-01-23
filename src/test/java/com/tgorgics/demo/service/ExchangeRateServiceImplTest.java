package com.tgorgics.demo.service;

import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.persistence.repository.ExchangeRateRepository;
import com.tgorgics.demo.service.request.ConversionRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class ExchangeRateServiceImplTest {

    @TestConfiguration
    static class ExchangeRateServiceImplTestContextConfiguration {

        @Bean
        public ExchangeRateService exchangeRateService(ExchangeRateRepository exchangeRateRepository) {
            return new ExchangeRateServiceImpl(exchangeRateRepository);
        }
    }

    @Autowired
    private ExchangeRateService exchangeRateService;

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    @Before
    public void setUp() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom("SEK");
        exchangeRate.setCurrencyTo("HUF");
        exchangeRate.setBuyingRate(BigDecimal.valueOf(33.15));
        exchangeRate.setSellingRate(BigDecimal.valueOf(29.72));

        Mockito.when(exchangeRateRepository.findByCurrencyFrom("SEK")).thenReturn(Arrays.asList(exchangeRate));
        Mockito.when(exchangeRateRepository.findByCurrencyFromAndCurrencyTo("SEK", "HUF")).thenReturn(exchangeRate);
    }

    @Test
    public void whenGetExchangeRateFor_ThenReturnExchangeRate() {
        List<ExchangeRate> foundList = this.exchangeRateService.getExchangeRatesFor("SEK");

        // the list should contain only 1 element
        Assertions.assertThat(foundList.size()).isEqualTo(1);

        // get that element
        ExchangeRate foundExchangeRate = foundList.get(0);

        Assertions.assertThat(foundExchangeRate.getCurrencyFrom()).isEqualTo("SEK");
        Assertions.assertThat(foundExchangeRate.getCurrencyTo()).isEqualTo("HUF");
        Assertions.assertThat(foundExchangeRate.getBuyingRate()).isEqualTo(BigDecimal.valueOf(33.15));
        Assertions.assertThat(foundExchangeRate.getSellingRate()).isEqualTo(BigDecimal.valueOf(29.72));
    }

    @Test
    public void whenGetRateBetween_ThenReturnExchangeRate() {
        ExchangeRate foundExchangeRate = this.exchangeRateService.getRateBetween("SEK", "HUF");

        Assertions.assertThat(foundExchangeRate.getCurrencyFrom()).isEqualTo("SEK");
        Assertions.assertThat(foundExchangeRate.getCurrencyTo()).isEqualTo("HUF");
        Assertions.assertThat(foundExchangeRate.getBuyingRate()).isEqualTo(BigDecimal.valueOf(33.15));
        Assertions.assertThat(foundExchangeRate.getSellingRate()).isEqualTo(BigDecimal.valueOf(29.72));
    }

    @Test
    public void whenConvertBuying_thenReturnAmount() {
        ConversionRequest req = new ConversionRequest();
        req.setAmount(BigDecimal.valueOf(20));
        req.setCurrencyFrom("SEK");
        req.setCurrencyTo("HUF");
        req.setRateType("BUYING");

        BigDecimal result = this.exchangeRateService.convert(req);

        Assertions.assertThat(result).isEqualTo(BigDecimal.valueOf(33.15).multiply(BigDecimal.valueOf(20)));
    }

    @Test
    public void whenConvertSelling_thenReturnAmount() {
        ConversionRequest req = new ConversionRequest();
        req.setAmount(BigDecimal.valueOf(20));
        req.setCurrencyFrom("SEK");
        req.setCurrencyTo("HUF");
        req.setRateType("SELLING");

        BigDecimal result = this.exchangeRateService.convert(req);

        Assertions.assertThat(result).isEqualTo(BigDecimal.valueOf(29.72).multiply(BigDecimal.valueOf(20)));
    }
}
