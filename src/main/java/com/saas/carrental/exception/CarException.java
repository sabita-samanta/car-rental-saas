package com.saas.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when car operations fail.
 * This could be due to:
 * - Invalid car details
 * - Car status transitions
 * - Car maintenance issues
 * - Car registration issues
 * - Car availability issues
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarException extends BusinessValidationException {
    private final String carId;
    private final String companyId;

    public CarException(String message) {
        super(message);
        this.carId = null;
        this.companyId = null;
    }

    public CarException(String message, String carId) {
        super(message);
        this.carId = carId;
        this.companyId = null;
    }

    public CarException(String message, String carId, String companyId) {
        super(message);
        this.carId = carId;
        this.companyId = companyId;
    }

    public String getCarId() {
        return carId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public static class InvalidCarDetails extends CarException {
        public InvalidCarDetails() {
            super("Invalid car details provided");
        }

        public InvalidCarDetails(String message) {
            super(message);
        }
    }

    public static class CarNotFound extends CarException {
        public CarNotFound(String carId) {
            super("Car not found", carId);
        }
    }

    public static class CarAlreadyExists extends CarException {
        public CarAlreadyExists(String licensePlate) {
            super("Car with license plate already exists: " + licensePlate);
        }
    }

    public static class InvalidStatusTransition extends CarException {
        public InvalidStatusTransition(String carId, String message) {
            super(message, carId);
        }
    }

    public static class CarMaintenanceRequired extends CarException {
        public CarMaintenanceRequired(String carId) {
            super("Car requires maintenance", carId);
        }

        public CarMaintenanceRequired(String carId, String message) {
            super(message, carId);
        }
    }

    public static class CarRegistrationExpired extends CarException {
        public CarRegistrationExpired(String carId) {
            super("Car registration has expired", carId);
        }
    }

    public static class CarNotAvailable extends CarException {
        public CarNotAvailable(String carId) {
            super("Car is not available", carId);
        }

        public CarNotAvailable(String carId, String message) {
            super(message, carId);
        }
    }

    public static class UnauthorizedCarAccess extends CarException {
        public UnauthorizedCarAccess(String carId, String companyId) {
            super("Unauthorized access to car", carId, companyId);
        }
    }

    public static class CarPricingError extends CarException {
        public CarPricingError(String carId) {
            super("Invalid pricing information for car", carId);
        }

        public CarPricingError(String carId, String message) {
            super(message, carId);
        }
    }
}