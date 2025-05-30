package com.delicious.model.enums;

public enum ToppingType {


    STEAK("Steak", true),
    HAM("Ham", true),
    SALAMI("Salami", true),
    ROAST_BEEF("Roast Beef", true),
    CHICKEN("Chicken", true),
    BACON("Bacon", true),

    AMERICAN("American", true),
    PROVOLONE("Provolone", true),
    CHEDDAR("Cheddar", true),
    SWISS("Swiss", true),

    LETTUCE("Lettuce", false),
    PEPPERS("Peppers", false),
    ONIONS("Onions", false),
    TOMATOES("Tomatoes", false),
    JALAPEÑOS("Jalapeños", false),
    CUCUMBERS("Cucumbers", false),
    PICKLES("Pickles", false),
    GUACAMOLE("Guacamole", false),
    MUSHROOMS("Mushrooms", false),

    MAYO("Mayo", false),
    MUSTARD("Mustard", false),
    KETCHUP("Ketchup", false),
    RANCH("Ranch", false),
    THOUSAND_ISLANDS("Thousand Islands", false),
    VINAIGRETTE("Vinaigrette", false),

    AU_JUS("Au Jus", false),
    SAUCE("Sauce", false);

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

