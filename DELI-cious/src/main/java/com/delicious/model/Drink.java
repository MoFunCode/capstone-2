package com.delicious.model;

public class Drink {

    private final String size;  //could use an enum later
    private final String flavor;

    public Drink(String size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }
}

