package com.tgorgics.demo.error.exception;

import lombok.Getter;

public class ExchangeRateNotFound extends RuntimeException {

    @Getter
    private String currencyFrom;

    @Getter
    private String currencyTo;

    public ExchangeRateNotFound(String currencyFrom, String currencyTo) {
        super(String.format("Cannot find exchange rate from %s to %s", currencyFrom, currencyTo));
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }
}
