package com.irongrp.challenge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Pack {

    private long index;
    private BigDecimal weight;
    private long value;

    public long getIndex() {
        return index;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public long getValue() {
        return value;
    }

    public BigDecimal valueToWeight() {
        return new BigDecimal(value).divide(weight, RoundingMode.UNNECESSARY);
    }

    public Pack index(long index) {
        this.index = index;
        return this;
    }

    public Pack weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public Pack value(long value) {
        this.value = value;
        return this;
    }

}
