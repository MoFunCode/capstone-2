package com.delicious.service;

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
}

