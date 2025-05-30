package com.delicious.service;

import com.delicious.model.Chips;
import com.delicious.model.Drink;
import com.delicious.model.Sandwich;
import com.delicious.model.Topping;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;

public class PriceCalculator {

    public static double calculateSandwichPrice(Sandwich sandwich) {
        double price = sandwich.getSize().getBasePrice();

        for (Topping topping : sandwich.getToppings()) {
            if (topping.getType().isPremium()) {
                price += getPremiumToppingPrice(sandwich.getSize(), topping.isExtra());
            }
        }

        return price;
    }

    private static double getPremiumToppingPrice(SandwichSize size, boolean isExtra) {
        double basePrice = switch (size) {
            case FOUR_INCH -> 1.00;
            case EIGHT_INCH -> 2.00;
            case TWELVE_INCH -> 3.00;
        };

        if (isExtra) {
            double extraCost = switch (size) {
                case FOUR_INCH -> 0.50;
                case EIGHT_INCH -> 1.00;
                case TWELVE_INCH -> 1.50;
            };
            return basePrice + extraCost;
        }

        return basePrice;
    }

    public static double calculateDrinkPrice(Drink drink) {
        return switch (drink.getSize().toLowerCase()) {
            case "small" -> 2.00;
            case "medium" -> 2.50;
            case "large" -> 3.00;
            default -> 0.0;
        };
    }

    public static double calculateChipsPrice(Chips chips) {
        return 1.50;
    }
}
