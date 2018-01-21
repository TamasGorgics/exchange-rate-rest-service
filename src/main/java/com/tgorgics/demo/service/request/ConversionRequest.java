package com.tgorgics.demo.service.request;

import com.tgorgics.demo.validation.Currency;
import com.tgorgics.demo.validation.RateType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ConversionRequest {

    @Currency
    @NotEmpty
    private String currencyFrom;

    @Currency
    @NotEmpty
    private String currencyTo;

    @NotNull
    @Min(0)
    private BigDecimal amount;

    @RateType
    @NotEmpty
    private String rateType;

}
