package com.delicious.cli;

import com.delicious.model.*;
import com.delicious.model.enums.*;
import com.delicious.service.PriceCalculator;
import com.delicious.utils.ReceiptFileManager;


import java.util.Scanner;

public class DeliApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static Order currentOrder;

    public static void main(String[] args) {
        System.out.println("Welcome to DELI-cious!");

        while (true) {
            showHomeScreen();
        }
    }

    private static void showHomeScreen() {
        System.out.println("\nHome");
        System.out.println("1) New Order");
        System.out.println("0) Exit");
        System.out.print("Please select an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                startNewOrder();
                break;
            case 0:
                System.out.println("Thank you for visiting us!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void startNewOrder() {
        currentOrder = new Order();
        showOrderScreen();
    }

    private static void showOrderScreen() {
        while (true) {
            System.out.println("\nOrder Screen");
            System.out.println("1) Add Sandwich");
            System.out.println("2) Add Drink");
            System.out.println("3) Add Chips");
            System.out.println("4) Checkout");
            System.out.println("0) Cancel Order");
            System.out.print("Please select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addSandwich();
                    break;
                case 2:
                    addDrink();
                    break;
                case 3:
                    addChips();
                    break;
                case 4:
                    checkout();
                    return;
                case 0:
                    System.out.println("Order cancelled.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addSandwich() {
        System.out.println("\nAdd Sandwich");

        System.out.println("Select your bread:");
        BreadType[] breadTypes = BreadType.values();
        for (int i = 0; i < breadTypes.length; i++) {
            System.out.println((i + 1) + ") " + breadTypes[i].getDisplayName());
        }
        System.out.print("Choose bread (1-" + breadTypes.length + "): ");
        int breadChoice = getIntInput();

        if (breadChoice < 1 || breadChoice > breadTypes.length) {
            System.out.println("Invalid choice. Returning to order screen.");
            return;
        }
        BreadType selectedBread = breadTypes[breadChoice - 1];

        System.out.println("\nSandwich size:");
        SandwichSize[] sizes = SandwichSize.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.println((i + 1) + ") " + sizes[i].getDisplayName() + " - $" +
                    String.format("%.2f", sizes[i].getBasePrice()));
        }
        System.out.print("Choose size (1-" + sizes.length + "): ");
        int sizeChoice = getIntInput();

        if (sizeChoice < 1 || sizeChoice > sizes.length) {
            System.out.println("Invalid choice. Returning to order screen.");
            return;
        }
        SandwichSize selectedSize = sizes[sizeChoice - 1];

        Sandwich sandwich = new Sandwich(selectedSize, selectedBread, false);
        addToppings(sandwich);

        System.out.print("\nWould you like the sandwich toasted? (y/n): ");
        String toastedChoice = scanner.nextLine().trim().toLowerCase();
        boolean isToasted = toastedChoice.equals("y") || toastedChoice.equals("yes");

        Sandwich finalSandwich = new Sandwich(selectedSize, selectedBread, isToasted);

        for (Topping topping : sandwich.getToppings()) {
            finalSandwich.addTopping(topping);
        }

        currentOrder.addSandwich(finalSandwich);
        System.out.println("Sandwich added to order!");
    }

    private static void addToppings(Sandwich sandwich) {

        addMeats(sandwich);
        addCheeses(sandwich);
        addRegularToppings(sandwich);
        addSauces(sandwich);
    }

    private static void addMeats(Sandwich sandwich) {
        System.out.println("\nMeats");
        ToppingType[] meats = {ToppingType.STEAK, ToppingType.HAM, ToppingType.SALAMI,
                ToppingType.ROAST_BEEF, ToppingType.CHICKEN, ToppingType.BACON};

        for (int i = 0; i < meats.length; i++) {
            System.out.println((i + 1) + ") " + meats[i].getDisplayName());
        }
        System.out.println("0) No meat");

        System.out.print("Select meat (0-" + meats.length + "): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= meats.length) {
            ToppingType selectedMeat = meats[choice - 1];

            System.out.print("Would you like extra " + selectedMeat.getDisplayName().toLowerCase() + "? (y/n): ");
            String extraChoice = scanner.nextLine().trim().toLowerCase();
            boolean isExtra = extraChoice.equals("y") || extraChoice.equals("yes");

            sandwich.addTopping(new Topping(selectedMeat, isExtra));
        }
    }

    private static void addCheeses(Sandwich sandwich) {
        System.out.println("\nCheeses");
        ToppingType[] cheeses = {ToppingType.AMERICAN, ToppingType.PROVOLONE,
                ToppingType.CHEDDAR, ToppingType.SWISS};

        for (int i = 0; i < cheeses.length; i++) {
            System.out.println((i + 1) + ") " + cheeses[i].getDisplayName());
        }
        System.out.println("0) No cheese");

        System.out.print("Select cheese (0-" + cheeses.length + "): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= cheeses.length) {
            ToppingType selectedCheese = cheeses[choice - 1];

            System.out.print("Would you like extra " + selectedCheese.getDisplayName().toLowerCase() + "? (y/n): ");
            String extraChoice = scanner.nextLine().trim().toLowerCase();
            boolean isExtra = extraChoice.equals("y") || extraChoice.equals("yes");

            sandwich.addTopping(new Topping(selectedCheese, isExtra));
        }
    }

    private static void addRegularToppings(Sandwich sandwich) {
        System.out.println("\nRegular Toppings (Free!)");
        ToppingType[] regularToppings = {ToppingType.LETTUCE, ToppingType.PEPPERS, ToppingType.ONIONS,
                ToppingType.TOMATOES, ToppingType.JALAPEÑOS, ToppingType.CUCUMBERS,
                ToppingType.PICKLES, ToppingType.GUACAMOLE, ToppingType.MUSHROOMS};

        System.out.println("Select toppings (enter numbers separated by spaces, or 0 for none):");
        for (int i = 0; i < regularToppings.length; i++) {
            System.out.println((i + 1) + ") " + regularToppings[i].getDisplayName());
        }

        System.out.print("Your choices: ");
        String input = scanner.nextLine().trim();

        if (!input.equals("0")) {
            String[] choices = input.split("\\s+");
            for (String choice : choices) {
                try {
                    int toppingChoice = Integer.parseInt(choice);
                    if (toppingChoice >= 1 && toppingChoice <= regularToppings.length) {
                        sandwich.addTopping(new Topping(regularToppings[toppingChoice - 1], false));
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private static void addSauces(Sandwich sandwich) {
        System.out.println("\nSauces (Free!)");
        ToppingType[] sauces = {ToppingType.MAYO, ToppingType.MUSTARD, ToppingType.KETCHUP,
                ToppingType.RANCH, ToppingType.THOUSAND_ISLANDS, ToppingType.VINAIGRETTE,
                ToppingType.AU_JUS, ToppingType.SAUCE};

        System.out.println("Select sauces (enter numbers separated by spaces, or 0 for none):");
        for (int i = 0; i < sauces.length; i++) {
            System.out.println((i + 1) + ") " + sauces[i].getDisplayName());
        }

        System.out.print("Your choices: ");
        String input = scanner.nextLine().trim();

        if (!input.equals("0")) {
            String[] choices = input.split("\\s+");
            for (String choice : choices) {
                try {
                    int sauceChoice = Integer.parseInt(choice);
                    if (sauceChoice >= 1 && sauceChoice <= sauces.length) {
                        sandwich.addTopping(new Topping(sauces[sauceChoice - 1], false));
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private static void addDrink() {
        System.out.println("\nAdd Drink");

        System.out.println("Select drink size:");
        System.out.println("1) Small - $2.00");
        System.out.println("2) Medium - $2.50");
        System.out.println("3) Large - $3.00");
        System.out.print("Choose size (1-3): ");

        int sizeChoice = getIntInput();
        String size;

        switch (sizeChoice) {
            case 1:
                size = "small";
                break;
            case 2:
                size = "medium";
                break;
            case 3:
                size = "large";
                break;
            default:
                System.out.println("Invalid choice. Returning to order screen.");
                return;
        }

        System.out.print("Enter drink flavor: ");
        String flavor = scanner.nextLine().trim();

        if (flavor.isEmpty()) {
            flavor = "Cola";
        }

        currentOrder.addDrink(new Drink(size, flavor));
        System.out.println("Drink added to order!");
    }

    private static void addChips() {
        System.out.println("\n=== Add Chips ===");
        System.out.print("Enter chip type: ");
        String chipType = scanner.nextLine().trim();

        if (chipType.isEmpty()) {
            chipType = "Regular";
        }

        currentOrder.addChips(new Chips(chipType));
        System.out.println("Chips added to order! ($1.50)");
    }

    private static void checkout() {
        System.out.println("\nORDER SUMMARY");

        if (!currentOrder.getSandwiches().isEmpty()) {
            System.out.println("\nSandwiches:");
            for (int i = 0; i < currentOrder.getSandwiches().size(); i++) {
                Sandwich sandwich = currentOrder.getSandwiches().get(i);
                System.out.println((i + 1) + ". " + formatSandwich(sandwich));
            }
        }

        if (!currentOrder.getDrinks().isEmpty()) {
            System.out.println("\nDrinks:");
            for (int i = 0; i < currentOrder.getDrinks().size(); i++) {
                Drink drink = currentOrder.getDrinks().get(i);
                System.out.println((i + 1) + ". " + drink.getSize() + " " + drink.getFlavor());
            }
        }

        if (!currentOrder.getChips().isEmpty()) {
            System.out.println("\nChips:");
            for (int i = 0; i < currentOrder.getChips().size(); i++) {
                Chips chips = currentOrder.getChips().get(i);
                System.out.println((i + 1) + ". " + chips.getType());
            }
        }

        System.out.printf("\nTOTAL: $%.2f\n", currentOrder.calculateTotal());

        System.out.println("\n1) Confirm Order");
        System.out.println("0) Cancel Order");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        if (choice == 1) {
            saveReceipt();
            System.out.println("Order confirmed! Receipt saved. Thank you!");
        } else {
            System.out.println("Order cancelled.");
        }
    }

    private static String formatSandwich(Sandwich sandwich) {
        String result = "";

        result = sandwich.getSize().getDisplayName() + " " + sandwich.getBreadType().getDisplayName() + " sandwich";

        if (sandwich.isToasted()) {
            result = result + " (Toasted)";
        }

        if (!sandwich.getToppings().isEmpty()) {
            result = result + " with ";
            String toppings = "";
            for (Topping topping : sandwich.getToppings()) {
                if (!toppings.isEmpty()) {
                    toppings = toppings + ", ";
                }
                toppings = toppings + topping.toString();
            }
            result = result + toppings;
        }

        result = result + String.format(" - $%.2f", PriceCalculator.calculateSandwichPrice(sandwich));

        return result;
    }

    private static void saveReceipt() {
        ReceiptFileManager.saveReceipt(currentOrder);
    }

    private static int getIntInput() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (Exception e) {
            return -1;
        }
    }
}


