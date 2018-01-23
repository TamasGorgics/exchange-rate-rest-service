package com.tgorgics.demo.persistence.model;

import com.tgorgics.demo.persistence.model.base.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Data
public class ExchangeRate extends BaseEntity {

    @Size(max = 3)
    @NotEmpty
    private String currencyFrom;

    @Size(max = 3)
    @NotEmpty
    private String currencyTo;

    @NotNull
    private BigDecimal buyingRate;

    @NotNull
    private BigDecimal sellingRate;

}


