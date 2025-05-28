package com.delicious.model;

import com.delicious.service.PriceCalculator;

public class Chips extends PriceCalculator {

    private final String type;

    public Chips(String type) {
        this.type = type;
    }

    public static double calculateChipsPrice(Chips chips) {
        return 1.50;
    }

    public String getType() {
        return type;
    }
}

