package com.irongrp.challenge.model;

import com.irongrp.challenge.Packager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PackageCombination {
    private List<Pack> packages = new ArrayList<>();
    private long totalValue = 0;
    private BigDecimal totalWeight = new BigDecimal(0);

    public List<Pack> getPackages() {
        return packages;
    }

    public long getTotalValue() {
        return totalValue;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public PackageCombination copy() {
        PackageCombination copy = new PackageCombination();
        copy.packages.addAll(this.packages);
        copy.totalValue = this.totalValue;
        copy.totalWeight = new BigDecimal(this.totalWeight.doubleValue());
        return copy;
    }

    public int compareTo(PackageCombination combination) {
        if (combination.totalValue == this.totalValue) {
            return combination.totalWeight.compareTo(this.totalWeight);
        }
        return this.totalValue-combination.totalValue>0 ? 1 : -1;
    }

    public void add(Pack pack) {
        packages.add(pack);
        totalValue += pack.getValue();
        totalWeight = totalWeight.add(pack.getWeight());
    }
}
