package com.apartment.exception;

/**
 * Thrown when user input validation fails.
 */
public class InvalidInputException extends ApartmentException {
    public InvalidInputException(String message) {
        super("Input Validation Error: " + message);
    }
}
