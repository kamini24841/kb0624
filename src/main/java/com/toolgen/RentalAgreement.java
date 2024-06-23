package com.toolgen;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.text.NumberFormat;
import java.util.Locale;

public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private int discount;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double totalCharge;
    private double finalCharge;
    private double discountAmount;
    private int chargeableDays;

    public RentalAgreement(Tool tool, int rentalDays, int discount, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.discount = discount;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(rentalDays);
    }

    public double calculateTotalCharge() {
        totalCharge = 0;
        chargeableDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);

        for (int i = 0; i < rentalDays; i++) {
            boolean isWeekend = currentDate.getDayOfWeek().getValue() == 6
                    || currentDate.getDayOfWeek().getValue() == 7;
            boolean isHoliday = isHoliday(currentDate);

            System.out.println(
                    "Current Date: " + currentDate + ", Is Weekend: " + isWeekend + ", Is Holiday: " + isHoliday);

            if (isHoliday && tool.isHolidayCharge()) {
                totalCharge += tool.getDailyCharge();
                chargeableDays++;
            } else if (isWeekend && tool.isWeekendCharge()) {
                totalCharge += tool.getDailyCharge();
                chargeableDays++;
            } else if (currentDate.getDayOfWeek().getValue() < 6 && !(isHoliday || isWeekend)) {
                totalCharge += tool.getDailyCharge();
                chargeableDays++;
            }
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        System.out.println("Total charge before rounding: " + totalCharge);
        totalCharge = Math.round(totalCharge * 100.0) / 100.0;
        System.out.println("Total charge after rounding: " + totalCharge);

        discountAmount = Math.round((totalCharge * discount / 100.0) * 100.0) / 100.0;
        System.out.println("Discount amount: " + discountAmount);

        finalCharge = totalCharge - discountAmount;
        finalCharge = Math.round(finalCharge * 100.0) / 100.0;
        System.out.println("Final charge: " + finalCharge);

        return finalCharge;
    }

    private boolean isHoliday(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);
        if (independenceDay.getDayOfWeek().getValue() == 6) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().getValue() == 7) {
            independenceDay = independenceDay.plusDays(1);
        }

        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1);
        while (laborDay.getDayOfWeek().getValue() != 1) {
            laborDay = laborDay.plusDays(1);
        }

        return date.equals(independenceDay) || date.equals(laborDay);
    }

    public void printAgreement() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMinimumFractionDigits(0);

        System.out.println("Tool code: " + tool.getCode());
        System.out.println("Tool type: " + tool.getType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + checkoutDate.format(dateFormatter));
        System.out.println("Due date: " + dueDate.format(dateFormatter));
        System.out.println("Daily rental charge: " + currencyFormatter.format(tool.getDailyCharge()));
        System.out.println("Chargeable days: " + chargeableDays);
        System.out.println("Pre-discount charge: " + currencyFormatter.format(totalCharge));
        System.out.println("Discount percent: " + percentFormatter.format(discount / 100.0));
        System.out.println("Discount amount: " + currencyFormatter.format(discountAmount));
        System.out.println("Final charge: " + currencyFormatter.format(finalCharge));
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public int getChargeableDays() {
        return chargeableDays;
    }
}
