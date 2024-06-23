package com.toolgen;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class RentalAgreementTest {

    @Test
    public void test1() {
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RentalAgreement(tool, 5, 101, LocalDate.of(2015, 9, 3));
        });

        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
    }

    @Test
    public void test2() {
        Tool tool = new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
        RentalAgreement agreement = new RentalAgreement(tool, 3, 10, LocalDate.of(2020, 7, 2));
        agreement.calculateTotalCharge();
        agreement.printAgreement();

        System.out.println("Expected chargeable days: 2");
        System.out.println("Expected total charge: 3.98");
        System.out.println("Expected discount amount: 0.40");
        System.out.println("Expected final charge: 3.58");

        assertEquals(2, agreement.getChargeableDays(), "Chargeable days mismatch.");
        assertEquals(3.98, agreement.getTotalCharge(), "Total charge mismatch.");
        assertEquals(0.40, agreement.getDiscountAmount(), "Discount amount mismatch.");
        assertEquals(3.58, agreement.getFinalCharge(), "Final charge mismatch.");
    }

    @Test
    public void test3() {
        Tool tool = new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true);
        RentalAgreement agreement = new RentalAgreement(tool, 5, 25, LocalDate.of(2015, 7, 2));
        agreement.calculateTotalCharge();

        assertEquals(3, agreement.getChargeableDays());
        assertEquals(4.47, agreement.getTotalCharge());
        assertEquals(1.12, agreement.getDiscountAmount());
        assertEquals(3.35, agreement.getFinalCharge());
    }

    @Test
    public void test4() {
        Tool tool = new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false);
        RentalAgreement agreement = new RentalAgreement(tool, 6, 0, LocalDate.of(2015, 9, 3));
        agreement.calculateTotalCharge();

        assertEquals(3, agreement.getChargeableDays());
        assertEquals(8.97, agreement.getTotalCharge());
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(8.97, agreement.getFinalCharge());
    }

    @Test
    public void test5() {
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
        RentalAgreement agreement = new RentalAgreement(tool, 9, 0, LocalDate.of(2015, 7, 2));
        agreement.calculateTotalCharge();

        assertEquals(5, agreement.getChargeableDays());
        assertEquals(14.95, agreement.getTotalCharge());
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(14.95, agreement.getFinalCharge());
    }

    @Test
    public void test6() {
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
        RentalAgreement agreement = new RentalAgreement(tool, 6, 0, LocalDate.of(2020, 7, 2));
        agreement.calculateTotalCharge();

        assertEquals(3, agreement.getChargeableDays());
        assertEquals(8.97, agreement.getTotalCharge());
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(8.97, agreement.getFinalCharge());
    }
}