package com.saas.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when booking operations fail.
 * This could be due to:
 * - Invalid booking dates
 * - Car unavailability
 * - Booking conflicts
 * - Invalid status transitions
 * - Payment issues
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingException extends BusinessValidationException {
    private final String bookingId;
    private final String carId;

    public BookingException(String message) {
        super(message);
        this.bookingId = null;
        this.carId = null;
    }

    public BookingException(String message, String bookingId) {
        super(message);
        this.bookingId = bookingId;
        this.carId = null;
    }

    public BookingException(String message, String bookingId, String carId) {
        super(message);
        this.bookingId = bookingId;
        this.carId = carId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCarId() {
        return carId;
    }

    public static class InvalidDateRange extends BookingException {
        public InvalidDateRange() {
            super("Invalid booking date range");
        }

        public InvalidDateRange(String message) {
            super(message);
        }
    }

    public static class CarUnavailable extends BookingException {
        public CarUnavailable(String carId) {
            super("Car is not available for booking", null, carId);
        }

        public CarUnavailable(String message, String carId) {
            super(message, null, carId);
        }
    }

    public static class BookingConflict extends BookingException {
        public BookingConflict(String carId) {
            super("Booking conflict: Car is already booked for the selected dates", null, carId);
        }
    }

    public static class InvalidStatusTransition extends BookingException {
        public InvalidStatusTransition(String bookingId, String message) {
            super(message, bookingId);
        }
    }

    public static class PaymentFailed extends BookingException {
        public PaymentFailed(String bookingId) {
            super("Payment processing failed for booking", bookingId);
        }

        public PaymentFailed(String bookingId, String message) {
            super(message, bookingId);
        }
    }

    public static class BookingCancellationFailed extends BookingException {
        public BookingCancellationFailed(String bookingId) {
            super("Unable to cancel booking", bookingId);
        }

        public BookingCancellationFailed(String bookingId, String message) {
            super(message, bookingId);
        }
    }

    public static class BookingModificationFailed extends BookingException {
        public BookingModificationFailed(String bookingId) {
            super("Unable to modify booking", bookingId);
        }

        public BookingModificationFailed(String bookingId, String message) {
            super(message, bookingId);
        }
    }
}