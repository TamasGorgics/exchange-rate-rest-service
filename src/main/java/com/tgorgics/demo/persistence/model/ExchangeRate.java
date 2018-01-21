package com.tgorgics.demo.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tgorgics.demo.persistence.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class ExchangeRate extends BaseEntity {

    private String currencyFrom;

    private String currencyTo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
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


