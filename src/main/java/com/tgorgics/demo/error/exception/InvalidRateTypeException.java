package com.tgorgics.demo.error.exception;

import lombok.Getter;

public class InvalidRateTypeException extends RuntimeException {

    @Getter
    private String rateType;

    public InvalidRateTypeException(String rateType) {
        super("Invalid exchange rate type: " + rateType);
        this.rateType = rateType;
    }

}
