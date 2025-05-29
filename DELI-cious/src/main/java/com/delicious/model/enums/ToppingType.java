package com.delicious.model.enums;

public enum ToppingType {

    STEAK("Steak", true, ToppingCategory.MEAT),
    HAM("Ham", true, ToppingCategory.MEAT),
    SALAMI("Salami", true, ToppingCategory.MEAT),
    ROAST_BEEF("Roast Beef", true, ToppingCategory.MEAT),
    CHICKEN("Chicken", true, ToppingCategory.MEAT),
    BACON("Bacon", true, ToppingCategory.MEAT),

    AMERICAN_CHEESE("American", true, ToppingCategory.CHEESE),
    PROVOLONE("Provolone", true, ToppingCategory.CHEESE),
    CHEDDAR("Cheddar", true, ToppingCategory.CHEESE),
    SWISS("Swiss", true, ToppingCategory.CHEESE),

    LETTUCE("Lettuce", false, ToppingCategory.REGULAR),
    PEPPERS("Peppers", false, ToppingCategory.REGULAR),
    ONIONS("Onions", false, ToppingCategory.REGULAR),
    TOMATOES("Tomatoes", false, ToppingCategory.REGULAR),
    JALAPEÑOS("Jalapeños", false, ToppingCategory.REGULAR),
    CUCUMBERS("Cucumbers", false, ToppingCategory.REGULAR),
    PICKLES("Pickles", false, ToppingCategory.REGULAR),
    GUACAMOLE("Guacamole", false, ToppingCategory.REGULAR),
    MUSHROOMS("Mushrooms", false, ToppingCategory.REGULAR),

    MAYO("Mayo", false, ToppingCategory.SAUCE),
    MUSTARD("Mustard", false, ToppingCategory.SAUCE),
    KETCHUP("Ketchup", false, ToppingCategory.SAUCE),
    RANCH("Ranch", false, ToppingCategory.SAUCE),
    THOUSAND_ISLANDS("Thousand Islands", false, ToppingCategory.SAUCE),
    VINAIGRETTE("Vinaigrette", false, ToppingCategory.SAUCE),

    AU_JUS("Au Jus", false, ToppingCategory.SIDE),
    SAUCE("Sauce", false, ToppingCategory.SIDE);

    private final String displayName;
    private final boolean isPremium;
    private final ToppingCategory category;

    ToppingType(String displayName, boolean isPremium, ToppingCategory category) {
        this.displayName = displayName;
        this.isPremium = isPremium;
        this.category = category;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public ToppingCategory getCategory() {
        return category;
    }

    public enum ToppingCategory {
        MEAT, CHEESE, REGULAR, SAUCE, SIDE
    }
}
