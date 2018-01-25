package com.tgorgics.demo.service;

import com.tgorgics.demo.enums.RateType;
import com.tgorgics.demo.error.exception.ExchangeRateNotFound;
import com.tgorgics.demo.error.exception.InvalidRateTypeException;
import com.tgorgics.demo.persistence.model.ExchangeRate;
import com.tgorgics.demo.persistence.repository.ExchangeRateRepository;
import com.tgorgics.demo.service.request.ConversionRequest;
import lombok.NoArgsConstructor;
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
    public List<ExchangeRate> getExchangeRatesFor(String currencyFrom) {
		List<ExchangeRate> result = this.exchangeRateRepository.findByCurrencyFrom(currencyFrom);
		
		if (result.isEmpty()) {
			throw new ExchangeRateNotFound(currencyFrom);
		}
		
        return result;
    }

    @Override
    public ExchangeRate getRateBetween(String currencyFrom, String currencyTo) {
		ExchangeRate result = this.exchangeRateRepository.findByCurrencyFromAndCurrencyTo(currencyFrom, currencyTo);
		
		if (result == null) {
			throw new ExchangeRateNotFound(currencyFrom, currencyTo);
		}
		
        return result;
    }

    @Override
    public BigDecimal convert(ConversionRequest request) {
        ExchangeRate exchangeRate = this.getRateBetween(request.getCurrencyFrom(), request.getCurrencyTo());
		
		if (exchangeRate == null) {
			throw new ExchangeRateNotFound(request.getCurrencyFrom(), request.getCurrencyTo());
		}
		
        BigDecimal rate = null;
        RateType rateType = null;
        try {
            rateType = RateType.valueOf(request.getRateType().toUpperCase());
        } catch (IllegalArgumentException e) {
            // This should never happen if controller-level validation is correct
            throw new InvalidRateTypeException(request.getRateType());
        }

        switch (rateType) {
            case BUYING: rate = exchangeRate.getBuyingRate(); break;
            case SELLING:rate = exchangeRate.getSellingRate();
        }

        if (rate == null) {
            // This should never happen if controller-level validation is correct
            throw new ExchangeRateNotFound(request.getCurrencyFrom(), request.getCurrencyTo());
        }

        return rate.multiply(request.getAmount());
    }
}
