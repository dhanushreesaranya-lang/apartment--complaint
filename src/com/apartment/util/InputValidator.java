package com.apartment.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating resident and apartment inputs.
 * Course Alignment: 25ITT32 (String Handling & Regex)
 */
public class InputValidator {
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^\\+?[0-9]{10,13}$");
    private static final Pattern DATE_PATTERN = 
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidDate(String date) {
        return date != null && DATE_PATTERN.matcher(date.trim()).matches();
    }

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
