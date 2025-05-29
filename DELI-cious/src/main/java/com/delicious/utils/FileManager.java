package com.delicious.utils;

import com.delicious.model.*;
import com.delicious.service.PriceCalculator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileManager {

    public static void saveReceipt(Order order) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String filename = "/Users/erimo/pluralsight/capstone-2/DELI-cious/src/main/resources/receiptsFolder" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("DELI-cious Receipt\n");
            writer.write("--------------\n");
            writer.write("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n");

            writer.write("ORDER DETAILS:\n");
            writer.write("--------------\n");

            if (!order.getSandwiches().isEmpty()) {
                writer.write("\nSANDWICHES:\n");
                int sandwichNumber = 1;
                for (Sandwich sandwich : order.getSandwiches()) {
                    writer.write(sandwichNumber + ". ");
                    writeSandwichDetails(writer, sandwich);
                    sandwichNumber++;
                }
            }

            if (!order.getDrinks().isEmpty()) {
                writer.write("\nDRINKS:\n");
                for (Drink drink : order.getDrinks()) {
                    double price = PriceCalculator.calculateDrinkPrice(drink);
                    writer.write("- " + drink.getSize() + " " + drink.getFlavor() + ": $" + String.format("%.2f", price) + "\n");
                }
            }

            if (!order.getChips().isEmpty()) {
                writer.write("\nCHIPS:\n");
                for (Chips chips : order.getChips()) {
                    double price = PriceCalculator.calculateChipsPrice(chips);
                    writer.write("- " + chips.getType() + ": $" + String.format("%.2f", price) + "\n");
                }
            }

            writer.write("--------------\n");
            writer.write("TOTAL: $" + String.format("%.2f", order.calculateTotal()) + "\n");
            writer.write("--------------\n");
            writer.write("Thank you for choosing DELI-cious!\n");

            System.out.println("Receipt saved: " + filename);

        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    private static void writeSandwichDetails(FileWriter writer, Sandwich sandwich) throws IOException {
        writer.write(sandwich.getSize().getDisplayName() + " " + sandwich.getBreadType().getDisplayName() + " bread");

        if (sandwich.isToasted()) {
            writer.write(" (Toasted)");
        }

        writer.write("\n");

        if (!sandwich.getToppings().isEmpty()) {
            writer.write("   Toppings: ");
            boolean first = true;
            for (Topping topping : sandwich.getToppings()) {
                if (!first) {
                    writer.write(", ");
                }
                writer.write(topping.toString());
                first = false;
            }
            writer.write("\n");
        }

        double price = PriceCalculator.calculateSandwichPrice(sandwich);
        writer.write("   Price: $" + String.format("%.2f", price) + "\n\n");
    }
}


