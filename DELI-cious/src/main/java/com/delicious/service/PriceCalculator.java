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
                if (topping.getType().getCategory() == ToppingType.ToppingCategory.MEAT) {
                    price += getMeatPrice(sandwich.getSize(), topping.isExtra());
                } else if (topping.getType().getCategory() == ToppingType.ToppingCategory.CHEESE) {
                    price += getCheesePrice(sandwich.getSize(), topping.isExtra());
                }
            }
        }

        return price;
    }

    private static double getMeatPrice(SandwichSize size, boolean isExtra) {
        double basePrice = switch (size) {
            case FOUR_INCH -> 1.00;
            case EIGHT_INCH -> 2.00;
            case TWELVE_INCH -> 3.00;
        };

        if (isExtra) {
            double extraPrice = switch (size) {
                case FOUR_INCH -> 0.50;
                case EIGHT_INCH -> 1.00;
                case TWELVE_INCH -> 1.50;
            };
            return basePrice + extraPrice;
        }

        return basePrice;
    }

    private static double getCheesePrice(SandwichSize size, boolean isExtra) {
        double basePrice = switch (size) {
            case FOUR_INCH -> 0.75;
            case EIGHT_INCH -> 1.50;
            case TWELVE_INCH -> 2.25;
        };

        if (isExtra) {
            double extraPrice = switch (size) {
                case FOUR_INCH -> 0.30;
                case EIGHT_INCH -> 0.60;
                case TWELVE_INCH -> 0.90;
            };
            return basePrice + extraPrice;
        }

        return basePrice;
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

