package com.delicious.model;

import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {

    private final SandwichSize size;
    private final BreadType breadType;
    private final boolean isToasted;
    private final List<Topping> toppings;

    public Sandwich(SandwichSize size, BreadType breadType, boolean isToasted) {
        this.size = size;
        this.breadType = breadType;
        this.isToasted = isToasted;
        this.toppings = new ArrayList<>();
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    public SandwichSize getSize() {
        return size;
    }

    public BreadType getBreadType() {
        return breadType;
    }

    public boolean isToasted() {
        return isToasted;
    }

    public List<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }

    @Override
    public String toString() {
        return "Sandwich{" +
                "size=" + size +
                ", breadType=" + breadType +
                ", isToasted=" + isToasted +
                ", toppings=" + toppings +
                '}';
    }
}
