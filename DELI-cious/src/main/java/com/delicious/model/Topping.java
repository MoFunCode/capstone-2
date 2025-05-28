package com.delicious.model;
import com.delicious.model.enums.ToppingType;

public class Topping {

    private final ToppingType type;
    private final boolean isExtra;

    public Topping(ToppingType type, boolean isExtra) {
        this.type = type;
        this.isExtra = isExtra;
    }

    public ToppingType getType() {
        return type;
    }

    public boolean isExtra() {
        return isExtra;
    }

    @Override
    public String toString() {
        return (isExtra ? "Extra " : "") + type.getDisplayName();
    }
}

