package com.delicious.service;

import com.delicious.model.Chips;
import com.delicious.model.Drink;
import com.delicious.model.Sandwich;
import com.delicious.model.Topping;
import com.delicious.model.enums.SandwichSize;

public class PriceCalculator {
    public static double calculateSandwichPrice(Sandwich sandwich) {
        double price = sandwich.getSize().getBasePrice();

        for (Topping topping : sandwich.getToppings()) {
            if (topping.getType().isPremium()) {
                price += topping.isExtra() ?
                        sandwich.getSize().getBasePrice() * 0.1 :
                        sandwich.getSize().getBasePrice() * 0.2;
            }
        }
        return price;
    }

    public static double calculateDrinkPrice(Drink drink) {
        return switch (drink.getSize().toUpperCase()) {
            case "SMALL" -> 2.00;
            case "MEDIUM" -> 2.50;
            case "LARGE" -> 3.00;
            default -> 0.0;
        };
    }

    public static double calculateChipsPrice(Chips chips) {
        return 1.50;
    }
}

