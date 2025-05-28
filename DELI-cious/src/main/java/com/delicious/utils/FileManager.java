package com.delicious.utils;

import com.delicious.model.*;
import com.delicious.service.PriceCalculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private static final String RECEIPTS_FOLDER = "/Users/erimo/pluralsight/capstone-2/DELI-cious/src/main/resources/receiptsFolder";
    private static final DateTimeFormatter FILENAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public static void saveReceipt(Order order) throws IOException {
        createReceiptsDirectory();

        String filename = generateReceiptFilename();
        String fullPath = RECEIPTS_FOLDER + File.separator + filename;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            writeReceiptContent(writer, order);
        }

        System.out.println("Receipt saved: " + filename);
    }

    private static void createReceiptsDirectory() throws IOException {
        Path receiptsPath = Paths.get(RECEIPTS_FOLDER);
        if (!Files.exists(receiptsPath)) {
            Files.createDirectories(receiptsPath);
        }
    }

    private static String generateReceiptFilename() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FILENAME_FORMAT) + ".txt";
    }

    private static void writeReceiptContent(BufferedWriter writer, Order order) throws IOException {
        writer.write("DELI-cious Receipt");
        writer.newLine();
        writer.write("==================");
        writer.newLine();
        writer.write("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        writer.newLine();
        writer.newLine();

        writer.write("ORDER DETAILS:");
        writer.newLine();
        writer.write("--------------");
        writer.newLine();

        List<Sandwich> sandwiches = order.getSandwiches();
        if (sandwiches != null && !sandwiches.isEmpty()) {
            writer.write("SANDWICHES:");
            writer.newLine();
            for (int i = 0; i < sandwiches.size(); i++) {
                Sandwich sandwich = sandwiches.get(i);
                writer.write((i + 1) + ". " + formatSandwich(sandwich));
                writer.newLine();
            }
            writer.newLine();
        }

        List<Drink> drinks = order.getDrinks();
        if (drinks != null && !drinks.isEmpty()) {
            writer.write("DRINKS:");
            writer.newLine();
            for (Drink drink : drinks) {
                double drinkPrice = PriceCalculator.calculateDrinkPrice(drink);
                writer.write(String.format("- %s %s: $%.2f", drink.getSize(), drink.getFlavor(), drinkPrice));
                writer.newLine();
            }
            writer.newLine();
        }

        List<Chips> chips = order.getChips();
        if (chips != null && !chips.isEmpty()) {
            writer.write("CHIPS:");
            writer.newLine();
            for (Chips chip : chips) {
                double chipPrice = PriceCalculator.calculateChipsPrice(chip);
                writer.write(String.format("- %s: $%.2f", chip.getType(), chipPrice));
                writer.newLine();
            }
            writer.newLine();
        }

        writer.write("==================");
        writer.newLine();
        writer.write(String.format("TOTAL: $%.2f", order.calculateTotal()));
        writer.newLine();
        writer.write("==================");
        writer.newLine();
        writer.newLine();
        writer.write("Thank you for choosing DELI-cious!");
        writer.newLine();
    }

    private static String formatSandwich(Sandwich sandwich) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s bread", sandwich.getSize().getDisplayName(), sandwich.getBreadType().getDisplayName()));

        if (sandwich.isToasted()) {
            sb.append(" (Toasted)");
        }

        List<Topping> toppings = sandwich.getToppings();
        if (!toppings.isEmpty()) {
            sb.append("\n   Toppings: ");
            sb.append(toppings.stream()
                    .map(Topping::toString)
                    .collect(Collectors.joining(", ")));
        }

        double sandwichPrice = PriceCalculator.calculateSandwichPrice(sandwich);
        sb.append(String.format("\n   Price: $%.2f", sandwichPrice));

        return sb.toString();
    }
}


