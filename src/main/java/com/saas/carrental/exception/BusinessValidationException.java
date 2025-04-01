package com.saas.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a business rule validation fails.
 * This could be due to:
 * - Invalid booking dates
 * - Car not available for booking
 * - Invalid status transitions
 * - Insufficient permissions
 * - Other business rule violations
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessValidationException extends RuntimeException {
    private final String field;
    private final String code;

    public BusinessValidationException(String message) {
        super(message);
        this.field = null;
        this.code = null;
    }

    public BusinessValidationException(String message, String field) {
        super(message);
        this.field = field;
        this.code = null;
    }

    public BusinessValidationException(String message, String field, String code) {
        super(message);
        this.field = field;
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }
}