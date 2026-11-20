package com.apartment.exception;

/**
 * Base custom application exception.
 * Course Alignment: 25ITT32 (Unit 3: Exception Handling - User defined Exception)
 */
public class ApartmentException extends Exception {
    public ApartmentException(String message) {
        super(message);
    }

    public ApartmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
