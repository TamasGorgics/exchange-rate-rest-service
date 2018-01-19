package com.tgorgics.demo.persistence.model;

import com.tgorgics.demo.enums.ECurrency;
import com.tgorgics.demo.persistence.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class ExchangeRate extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ECurrency currencyFrom;

    @Enumerated(EnumType.STRING)
    private ECurrency currencyTo;

    private Instant validAt;

    private BigDecimal buyingRate;

    private BigDecimal sellingRate;

    @PrePersist
    protected void onCreation() {
        this.validAt = Instant.now();
    }

    protected void onUpdate() {
        this.validAt = Instant.now();
    }
}


