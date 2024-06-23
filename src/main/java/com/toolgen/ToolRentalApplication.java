package com.toolgen;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ToolRentalApplication {

    private static Map<String, Tool> tools = new HashMap<>();

    static {
        tools.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        tools.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        tools.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        tools.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter tool code: ");
            String toolCode = scanner.nextLine().trim().toUpperCase();
            if (!tools.containsKey(toolCode)) {
                throw new IllegalArgumentException("Invalid tool code.");
            }

            System.out.print("Enter rental day count: ");
            int rentalDays = Integer.parseInt(scanner.nextLine().trim());
            if (rentalDays < 1) {
                throw new IllegalArgumentException("Rental day count must be 1 or greater.");
            }

            System.out.print("Enter discount percent: ");
            int discount = Integer.parseInt(scanner.nextLine().trim());
            if (discount < 0 || discount > 100) {
                throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
            }

            System.out.print("Enter checkout date (YYYY-MM-DD): ");
            LocalDate checkoutDate = LocalDate.parse(scanner.nextLine().trim());

            Tool tool = tools.get(toolCode);
            RentalAgreement agreement = new RentalAgreement(tool, rentalDays, discount, checkoutDate);
            agreement.calculateTotalCharge();

            agreement.printAgreement();

        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        scanner.close();
    }
}
