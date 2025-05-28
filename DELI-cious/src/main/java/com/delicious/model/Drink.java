package com.delicious.model;

import com.delicious.service.PriceCalculator;

public class Drink extends PriceCalculator {

    private final String size;  //could use an enum later
    private final String flavor;

    public Drink(String size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    public String getSize() {
        return size;
    }

    public String getFlavor() {
        return flavor;
    }

}

