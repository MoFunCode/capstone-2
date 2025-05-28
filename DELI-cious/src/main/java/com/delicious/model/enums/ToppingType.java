package com.delicious.model.enums;

public enum ToppingType {
    STEAK("Steak", true),
    HAM("Ham", true),
    SALAMI("Salami", true),
    BACON("Bacon", true),
    AMERICAN_CHEESE("American Cheese", true),
    CHEDDAR("Cheddar", true),

    // Regular toppings (veggies/sauces)
    LETTUCE("Lettuce", false),
    TOMATO("Tomato", false),
    ONIONS("Onions", false),
    MAYO("Mayo", false),
    MUSTARD("Mustard", false);

    private final String displayName;
    private final boolean isPremium;

    ToppingType(String displayName, boolean isPremium) {
        this.displayName = displayName;
        this.isPremium = isPremium;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPremium() {
        return isPremium;
    }
}
