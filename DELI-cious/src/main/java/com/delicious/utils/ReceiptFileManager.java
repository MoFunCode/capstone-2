package com.delicious.utils;

import com.delicious.model.Chips;
import com.delicious.model.Drink;
import com.delicious.model.Order;
import com.delicious.model.Sandwich;
import com.delicious.service.PriceCalculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ReceiptFileManager {

    private static final String RECEIPTS_FOLDER = "src/main/resources/receiptsFolder";

    public static void saveReceipt(Order order) {
        try {
            File receiptsDir = new File(RECEIPTS_FOLDER);

            String timestamp = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            String filename = timestamp + ".txt";
            File receiptFile = new File(receiptsDir, filename);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFile))) {
                writeReceiptContent(writer, order);
            }

            System.out.println("Receipt saved as: " + filename);

        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    private static void writeReceiptContent(BufferedWriter writer, Order order) throws IOException {
        writer.write("Receipt\n");
        writer.write("Order Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n");

        if (!order.getSandwiches().isEmpty()) {
            writer.write("SANDWICHES:\n");
            for (Sandwich sandwich : order.getSandwiches()) {
                writer.write("- " + formatSandwichForReceipt(sandwich) + "\n");
            }
            writer.write("\n");
        }

        if (!order.getDrinks().isEmpty()) {
            writer.write("DRINKS:\n");
            for (Drink drink : order.getDrinks()) {
                writer.write("- " + drink.getSize() + " " + drink.getFlavor() +
                        " - $" + String.format("%.2f", PriceCalculator.calculateDrinkPrice(drink)) + "\n");
            }
            writer.write("\n");
        }

        if (!order.getChips().isEmpty()) {
            writer.write("CHIPS:\n");
            for (Chips chips : order.getChips()) {
                writer.write("- " + chips.getType() + " - $1.50\n");
            }
            writer.write("\n");
        }

        writer.write("TOTAL: $" + String.format("%.2f", order.calculateTotal()) + "\n");
        writer.write("\nThank you for choosing DELI-cious!\n");
    }

    private static String formatSandwichForReceipt(Sandwich sandwich) {
        StringBuilder sb = new StringBuilder();
        sb.append(sandwich.getSize().getDisplayName())
                .append(" ")
                .append(sandwich.getBreadType().getDisplayName())
                .append(" sandwich");

        if (sandwich.isToasted()) {
            sb.append(" (Toasted)");
        }

        if (!sandwich.getToppings().isEmpty()) {
            sb.append(" with ");
            for (int i = 0; i < sandwich.getToppings().size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(sandwich.getToppings().get(i).toString());
            }
        }

        sb.append(String.format(" - $%.2f", PriceCalculator.calculateSandwichPrice(sandwich)));

        return sb.toString();
    }
}





