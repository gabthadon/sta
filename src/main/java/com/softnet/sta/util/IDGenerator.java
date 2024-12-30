package com.softnet.sta.util;

import com.softnet.sta.constant.IdValues;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Year;
import java.util.Random;


public class IDGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateID(String IdName) {
        int DIVIDER_LENGTH = 4;
        int DIVIDER_LENGTH1 = 3;

        // Get current year and month
        String yearAndMonth = getYearAndMonthPrefix();

        // Generate random digits, excluding the prefix length
        String randomDigits1 = generateRandomDigits(DIVIDER_LENGTH - yearAndMonth.length());
        String randomDigits2 = generateRandomDigits(DIVIDER_LENGTH1);

        // Return the formatted ID
        return String.format("%s%s%s%s", IdName, yearAndMonth, randomDigits1, randomDigits2);
    }

    private static String generateRandomDigits(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 1; i <= length; i++) {
            int digit = random.nextInt(10); // Generate random digits
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }

    private static String getYearAndMonthPrefix() {
        // Get the current year and month
        int currentYear = Year.now().getValue();
        int currentMonth = LocalDate.now().getMonthValue();

        // Extract last two digits of the year
        String yearLastTwoDigits = String.valueOf(currentYear).substring(2);

        // Format month to always be two digits (e.g., "09" for September)
        String monthFormatted = String.format("%02d", currentMonth);

        // Combine year and month
        return yearLastTwoDigits + monthFormatted;
    }



    public static String generateIDForCourseCode(String IdName) {
        int year = Year.now().getValue();
        int DIVIDER_LENGTH = 2;
        int DIVIDER_LENGTH1 = 1;

        String randomDigits1 = generateRandomDigits(DIVIDER_LENGTH);
        String randomDigits2 = generateRandomDigits(DIVIDER_LENGTH1);

        return String.format("%s%s%s", IdName, randomDigits1, randomDigits2);
    }



    // Method to generate a discount code of a specified length
    public static String generateDiscountCode(int length) {
        StringBuilder discountCode = new StringBuilder(length);

        // Randomly pick characters from the CHARACTERS string
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(IdValues.CHARACTERS.length());
            discountCode.append(IdValues.CHARACTERS.charAt(index));
        }

        return discountCode.toString();
    }



}
