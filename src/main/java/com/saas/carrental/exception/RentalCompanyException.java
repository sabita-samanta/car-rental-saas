package com.saas.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when rental company operations fail.
 * This could be due to:
 * - Invalid company details
 * - Company registration issues
 * - Company status issues
 * - Company fleet management issues
 * - Company authorization issues
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RentalCompanyException extends BusinessValidationException {
    private final String companyId;

    public RentalCompanyException(String message) {
        super(message);
        this.companyId = null;
    }

    public RentalCompanyException(String message, String companyId) {
        super(message);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public static class InvalidCompanyDetails extends RentalCompanyException {
        public InvalidCompanyDetails() {
            super("Invalid company details provided");
        }

        public InvalidCompanyDetails(String message) {
            super(message);
        }
    }

    public static class CompanyNotFound extends RentalCompanyException {
        public CompanyNotFound(String companyId) {
            super("Company not found", companyId);
        }
    }

    public static class CompanyAlreadyExists extends RentalCompanyException {
        public CompanyAlreadyExists(String email) {
            super("Company with email already exists: " + email);
        }
    }

    public static class CompanyStatusInvalid extends RentalCompanyException {
        public CompanyStatusInvalid(String companyId, String message) {
            super(message, companyId);
        }
    }

    public static class CompanyVerificationRequired extends RentalCompanyException {
        public CompanyVerificationRequired(String companyId) {
            super("Company requires verification", companyId);
        }
    }

    public static class CompanyLicenseExpired extends RentalCompanyException {
        public CompanyLicenseExpired(String companyId) {
            super("Company business license has expired", companyId);
        }
    }

    public static class FleetLimitExceeded extends RentalCompanyException {
        public FleetLimitExceeded(String companyId) {
            super("Company has exceeded their fleet size limit", companyId);
        }
    }

    public static class UnauthorizedCompanyAccess extends RentalCompanyException {
        public UnauthorizedCompanyAccess(String companyId) {
            super("Unauthorized access to company resources", companyId);
        }
    }

    public static class InvalidBillingInformation extends RentalCompanyException {
        public InvalidBillingInformation(String companyId) {
            super("Invalid billing information", companyId);
        }

        public InvalidBillingInformation(String companyId, String message) {
            super(message, companyId);
        }
    }

    public static class CompanySubscriptionInvalid extends RentalCompanyException {
        public CompanySubscriptionInvalid(String companyId) {
            super("Company subscription is invalid or expired", companyId);
        }

        public CompanySubscriptionInvalid(String companyId, String message) {
            super(message, companyId);
        }
    }
}