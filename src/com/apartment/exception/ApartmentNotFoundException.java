package com.apartment.exception;

/**
 * Thrown when an apartment unit cannot be found.
 */
public class ApartmentNotFoundException extends ApartmentException {
    public ApartmentNotFoundException(String apartmentId) {
        super("Apartment Unit '" + apartmentId + "' was not found in the records.");
    }
}
