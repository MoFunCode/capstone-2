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
        this.sandwiches.add(sandwich);
    }

    public void addDrink(Drink drink) {
        this.drinks.add(drink);

    }

    public void addChips(Chips chips) {
        this.chips.add(chips);

    }

    public List<Drink> getDrinks() {
        return new ArrayList<>(drinks);
    }

    public List<Chips> getChips() {
        return new ArrayList<>(chips);
    }

    public List<Sandwich> getSandwiches() {
        return new ArrayList<>(sandwiches);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Sandwich sandwich : sandwiches) {
            total += PriceCalculator.calculateSandwichPrice(sandwich);
        }
        for (Drink drink : drinks) {
            total += PriceCalculator.calculateDrinkPrice(drink);
        }

        for (Chips chip : chips) {
            total += PriceCalculator.calculateChipsPrice(chip);
        }

        return total;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}

