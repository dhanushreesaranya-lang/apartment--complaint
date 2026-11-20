package com.apartment.exception;

/**
 * Thrown when a resident record cannot be found.
 */
public class ResidentNotFoundException extends ApartmentException {
    public ResidentNotFoundException(String residentId) {
        super("Resident with ID '" + residentId + "' was not found in the system.");
    }
}
