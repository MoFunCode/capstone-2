package com.delicious.cli;

import com.delicious.model.*;
import com.delicious.model.enums.*;
import com.delicious.service.PriceCalculator;
import com.delicious.utils.FileManager;


import java.util.Arrays;
import java.util.Scanner;

public class DeliApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static Order currentOrder;

    private static void showHomeScreen() {
        while (true) {
            System.out.println("\n=== DELI-cious ===");
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> {
                    currentOrder = new Order();
                    showOrderScreen();
                }
                case 0 -> {
                    System.out.println("Thank you for visiting DELI-cious!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showOrderScreen() {
        while (true) {
            System.out.println("\n=== Order Screen ===");
            System.out.println("1) Add Sandwich");
            System.out.println("2) Add Drink");
            System.out.println("3) Add Chips");
            System.out.println("4) Checkout");
            System.out.println("0) Cancel Order");
            System.out.print("Select an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> addSandwich();
                case 2 -> addDrink();
                case 3 -> addChips();
                case 4 -> checkout();
                case 0 -> {
                    currentOrder = null;
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addSandwich() {
        System.out.println("\n=== Add Sandwich ===");

        System.out.println("Select your bread:");
        BreadType[] breads = BreadType.values();
        for (int i = 0; i < breads.length; i++) {
            System.out.println((i + 1) + ") " + breads[i].getDisplayName());
        }
        System.out.print("Choice: ");
        int breadChoice = getIntInput() - 1;
        if (breadChoice < 0 || breadChoice >= breads.length) {
            System.out.println("Invalid choice.");
            return;
        }
        BreadType selectedBread = breads[breadChoice];

        System.out.println("\nSandwich size:");
        SandwichSize[] sizes = SandwichSize.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.println((i + 1) + ") " + sizes[i].getDisplayName() + " - $" + sizes[i].getBasePrice());
        }
        System.out.print("Choice: ");
        int sizeChoice = getIntInput() - 1;
        if (sizeChoice < 0 || sizeChoice >= sizes.length) {
            System.out.println("Invalid choice.");
            return;
        }
        SandwichSize selectedSize = sizes[sizeChoice];

        Sandwich sandwich = new Sandwich(selectedSize, selectedBread, false);

        addToppings(sandwich);

        System.out.print("\nWould you like the sandwich toasted? (y/n): ");
        String toasted = scanner.nextLine().trim().toLowerCase();

        Sandwich finalSandwich = new Sandwich(selectedSize, selectedBread, toasted.equals("y"));

        for (Topping topping : sandwich.getToppings()) {
            finalSandwich.addTopping(topping);
        }

        currentOrder.addSandwich(finalSandwich);
        System.out.println("Sandwich added to order!");
    }

    private static void addToppings(Sandwich sandwich) {
        addToppingsByCategory(sandwich, ToppingType.ToppingCategory.MEAT, "Meats");
        addToppingsByCategory(sandwich, ToppingType.ToppingCategory.CHEESE, "Cheeses");
        addToppingsByCategory(sandwich, ToppingType.ToppingCategory.REGULAR, "Regular Toppings");
        addToppingsByCategory(sandwich, ToppingType.ToppingCategory.SAUCE, "Sauces");
        addToppingsByCategory(sandwich, ToppingType.ToppingCategory.SIDE, "Sides");
    }

    private static void addToppingsByCategory(Sandwich sandwich, ToppingType.ToppingCategory category, String categoryName) {
        ToppingType[] toppings = Arrays.stream(ToppingType.values())
                .filter(t -> t.getCategory() == category)
                .toArray(ToppingType[]::new);

        if (toppings.length == 0) return;

        System.out.println("\n" + categoryName + ":");
        for (int i = 0; i < toppings.length; i++) {
            String priceInfo = toppings[i].isPremium() ? " (Premium)" : " (Free)";
            System.out.println((i + 1) + ") " + toppings[i].getDisplayName() + priceInfo);
        }
        System.out.println("0) Skip " + categoryName.toLowerCase());

        while (true) {
            System.out.print("Select " + categoryName.toLowerCase() + " (0 to finish): ");
            int choice = getIntInput();

            if (choice == 0) break;

            if (choice < 1 || choice > toppings.length) {
                System.out.println("Invalid choice.");
                continue;
            }

            ToppingType selectedTopping = toppings[choice - 1];

            boolean isExtra = false;
            if (selectedTopping.isPremium()) {
                System.out.print("Extra " + selectedTopping.getDisplayName() + "? (y/n): ");
                String extra = scanner.nextLine().trim().toLowerCase();
                isExtra = extra.equals("y");
            }

            sandwich.addTopping(new Topping(selectedTopping, isExtra));
            System.out.println("Added " + (isExtra ? "extra " : "") + selectedTopping.getDisplayName());
        }
    }

    private static void addDrink() {
        System.out.println("\n=== Add Drink ===");

        System.out.println("Select drink size:");
        System.out.println("1) Small - $2.00");
        System.out.println("2) Medium - $2.50");
        System.out.println("3) Large - $3.00");
        System.out.print("Choice: ");

        String size = switch (getIntInput()) {
            case 1 -> "Small";
            case 2 -> "Medium";
            case 3 -> "Large";
            default -> {
                System.out.println("Invalid choice.");
                yield null;
            }
        };

        if (size == null) return;

        System.out.print("Enter drink flavor: ");
        String flavor = scanner.nextLine().trim();

        if (flavor.isEmpty()) {
            System.out.println("Flavor cannot be empty.");
            return;
        }

        currentOrder.addDrink(new Drink(size, flavor));
        System.out.println("Drink added to order!");
    }

    private static void addChips() {
        System.out.println("\n=== Add Chips ===");
        System.out.print("Enter chip type: ");
        String type = scanner.nextLine().trim();

        if (type.isEmpty()) {
            System.out.println("Chip type cannot be empty.");
            return;
        }

        currentOrder.addChips(new Chips(type));
        System.out.println("Chips added to order!");
    }

    private static void checkout() {
        System.out.println("\n=== Checkout ===");
        displayOrderSummary();

        System.out.println("\n1) Confirm Order");
        System.out.println("2) Cancel Order");
        System.out.print("Choice: ");

        int choice = getIntInput();
        if (choice == 1) {
            FileManager.saveReceipt(currentOrder);
            System.out.println("Order confirmed! Receipt saved.");
            currentOrder = null;
            return;
        } else if (choice == 2) {
            currentOrder = null;
            System.out.println("Order cancelled.");
            return;
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void displayOrderSummary() {
        System.out.println("\n----Order Summary----");

        if (!currentOrder.getSandwiches().isEmpty()) {
            System.out.println("\nSandwiches:");
            for (int i = 0; i < currentOrder.getSandwiches().size(); i++) {
                Sandwich sandwich = currentOrder.getSandwiches().get(i);
                System.out.printf("%d. %s %s%s - $%.2f%n",
                        i + 1,
                        sandwich.getSize().getDisplayName(),
                        sandwich.getBreadType().getDisplayName(),
                        sandwich.isToasted() ? " (Toasted)" : "",
                        PriceCalculator.calculateSandwichPrice(sandwich));

                if (!sandwich.getToppings().isEmpty()) {
                    System.out.print("   Toppings: ");
                    for (int j = 0; j < sandwich.getToppings().size(); j++) {
                        if (j > 0) System.out.print(", ");
                        System.out.print(sandwich.getToppings().get(j).toString());
                    }
                    System.out.println();
                }
            }
        }

        if (!currentOrder.getDrinks().isEmpty()) {
            System.out.println("\nDrinks:");
            for (Drink drink : currentOrder.getDrinks()) {
                System.out.printf("- %s %s - $%.2f%n",
                        drink.getSize(),
                        drink.getFlavor(),
                        PriceCalculator.calculateDrinkPrice(drink));
            }
        }

        if (!currentOrder.getChips().isEmpty()) {
            System.out.println("\nChips:");
            for (Chips chips : currentOrder.getChips()) {
                System.out.printf("- %s - $%.2f%n",
                        chips.getType(),
                        PriceCalculator.calculateChipsPrice(chips));
            }
        }

        System.out.printf("\nTotal: $%.2f%n", currentOrder.calculateTotal());
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        showHomeScreen();
    }
}
