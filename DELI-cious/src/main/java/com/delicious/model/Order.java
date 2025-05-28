package com.delicious.model;

import com.delicious.service.PriceCalculator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<Sandwich> sandwiches = new ArrayList<>();
    private final List<Drink> drinks = new ArrayList<>();
    private final List<Chips> chips = new ArrayList<>();
    private final LocalDateTime orderDate = LocalDateTime.now();

    public void addSandwich(Sandwich sandwich) {
        sandwiches.add(sandwich);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Sandwich sandwich : sandwiches) {
            total += PriceCalculator.calculateSandwichPrice(sandwich);
        }
        return total;
    }

}

