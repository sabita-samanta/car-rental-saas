package com.saas.carrental.util;

import com.saas.carrental.exception.BusinessValidationException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * Utility class for common validation operations.
 */
public final class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile("^[A-Z0-9-]{2,10}$");

    private ValidationUtils() {
        // Private constructor to prevent instantiation
    }

    public static void validateEmail(String email) {
        if (!StringUtils.hasText(email) || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessValidationException("Invalid email format");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber) || !PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new BusinessValidationException("Invalid phone number format");
        }
    }

    public static void validateLicensePlate(String licensePlate) {
        if (!StringUtils.hasText(licensePlate) || !LICENSE_PLATE_PATTERN.matcher(licensePlate).matches()) {
            throw new BusinessValidationException("Invalid license plate format");
        }
    }

    public static void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new BusinessValidationException("Start date and end date must not be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new BusinessValidationException("Start date must be before end date");
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new BusinessValidationException("Start date must be in the future");
        }
    }

    public static void validatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new BusinessValidationException("Price must be greater than zero");
        }
    }

    public static void validateYear(Integer year) {
        int currentYear = LocalDateTime.now().getYear();
        if (year == null || year < 1900 || year > currentYear + 1) {
            throw new BusinessValidationException("Invalid year");
        }
    }

    public static void validatePassword(String password) {
        if (!StringUtils.hasText(password) || password.length() < 8) {
            throw new BusinessValidationException("Password must be at least 8 characters long");
        }

        if (!Pattern.compile(".*[0-9].*").matcher(password).matches()) {
            throw new BusinessValidationException("Password must contain at least one digit");
        }

        if (!Pattern.compile(".*[a-z].*").matcher(password).matches()) {
            throw new BusinessValidationException("Password must contain at least one lowercase letter");
        }

        if (!Pattern.compile(".*[A-Z].*").matcher(password).matches()) {
            throw new BusinessValidationException("Password must contain at least one uppercase letter");
        }

        if (!Pattern.compile(".*[@#$%^&+=].*").matcher(password).matches()) {
            throw new BusinessValidationException("Password must contain at least one special character");
        }
    }

    public static void validateName(String name, String fieldName) {
        if (!StringUtils.hasText(name) || name.length() < 2 || name.length() > 50) {
            throw new BusinessValidationException(fieldName + " must be between 2 and 50 characters");
        }
    }

    public static void validateAddress(String address) {
        if (!StringUtils.hasText(address) || address.length() > 200) {
            throw new BusinessValidationException("Address must not be empty and not exceed 200 characters");
        }
    }

    public static void validateZipCode(String zipCode) {
        if (!StringUtils.hasText(zipCode) || !Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$").matcher(zipCode).matches()) {
            throw new BusinessValidationException("Invalid ZIP code format");
        }
    }

    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new BusinessValidationException(fieldName + " must not be null");
        }
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new BusinessValidationException(fieldName + " must not be empty");
        }
    }
}