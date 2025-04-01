package com.saas.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when authentication fails.
 * This could be due to:
 * - Invalid credentials
 * - Expired tokens
 * - Invalid tokens
 * - Missing authentication
 * - Insufficient permissions
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
    private final String errorCode;

    public AuthenticationException(String message) {
        super(message);
        this.errorCode = "AUTHENTICATION_ERROR";
    }

    public AuthenticationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static class InvalidCredentials extends AuthenticationException {
        public InvalidCredentials() {
            super("Invalid username or password", "INVALID_CREDENTIALS");
        }
    }

    public static class TokenExpired extends AuthenticationException {
        public TokenExpired() {
            super("Authentication token has expired", "TOKEN_EXPIRED");
        }
    }

    public static class InvalidToken extends AuthenticationException {
        public InvalidToken() {
            super("Invalid authentication token", "INVALID_TOKEN");
        }
    }

    public static class InsufficientPermissions extends AuthenticationException {
        public InsufficientPermissions() {
            super("Insufficient permissions to perform this action", "INSUFFICIENT_PERMISSIONS");
        }
    }

    public static class AccountLocked extends AuthenticationException {
        public AccountLocked() {
            super("Account is locked", "ACCOUNT_LOCKED");
        }
    }

    public static class AccountDisabled extends AuthenticationException {
        public AccountDisabled() {
            super("Account is disabled", "ACCOUNT_DISABLED");
        }
    }
}