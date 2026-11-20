package com.apartment.service;

import com.apartment.dao.ApartmentDAO;
import com.apartment.dao.ComplaintDAO;
import com.apartment.dao.MaintenanceDAO;
import com.apartment.dao.ResidentDAO;
import com.apartment.exception.ApartmentException;
import com.apartment.exception.ApartmentNotFoundException;
import com.apartment.exception.InvalidInputException;
import com.apartment.exception.ResidentNotFoundException;
import com.apartment.model.Apartment;
import com.apartment.model.Complaint;
import com.apartment.model.MaintenancePayment;
import com.apartment.model.Resident;
import com.apartment.util.InputValidator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Layer coordinating business logic between DAOs and Presentation Layer.
 * Course Alignment: 25ITT32 (Java Collections, Lambdas & Streams, Exception Handling)
 */
public class ApartmentService {
    private final ApartmentDAO apartmentDAO;
    private final ResidentDAO residentDAO;
    private final MaintenanceDAO maintenanceDAO;
    private final ComplaintDAO complaintDAO;

    public ApartmentService() {
        this.apartmentDAO = new ApartmentDAO();
        this.residentDAO = new ResidentDAO();
        this.maintenanceDAO = new MaintenanceDAO();
        this.complaintDAO = new ComplaintDAO();
    }

    // -------------------------------------------------------------------------
    // APARTMENT OPERATIONS
    // -------------------------------------------------------------------------
    public List<Apartment> getAllApartments() throws ApartmentException {
        try {
            return apartmentDAO.getAllApartments();
        } catch (SQLException e) {
            throw new ApartmentException("Failed to fetch apartments: " + e.getMessage(), e);
        }
    }

    public void registerApartment(Apartment apt) throws ApartmentException {
        if (!InputValidator.isNotEmpty(apt.getApartmentId())) {
            throw new InvalidInputException("Apartment ID cannot be empty.");
        }
        try {
            if (apartmentDAO.getApartmentById(apt.getApartmentId()) != null) {
                throw new InvalidInputException("Apartment ID already exists: " + apt.getApartmentId());
            }
            apartmentDAO.addApartment(apt);
        } catch (SQLException e) {
            throw new ApartmentException("Error saving apartment: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // RESIDENT OPERATIONS
    // -------------------------------------------------------------------------
    public List<Resident> getAllResidents() throws ApartmentException {
        try {
            return residentDAO.getAllResidents();
        } catch (SQLException e) {
            throw new ApartmentException("Failed to fetch residents: " + e.getMessage(), e);
        }
    }

    public void registerResident(Resident resident) throws ApartmentException {
        if (!InputValidator.isNotEmpty(resident.getName())) {
            throw new InvalidInputException("Resident name cannot be empty.");
        }
        if (!InputValidator.isValidEmail(resident.getEmail())) {
            throw new InvalidInputException("Invalid email format: " + resident.getEmail());
        }
        if (!InputValidator.isValidPhone(resident.getPhone())) {
            throw new InvalidInputException("Invalid phone number format: " + resident.getPhone());
        }

        try {
            Apartment apt = apartmentDAO.getApartmentById(resident.getApartmentId());
            if (apt == null) {
                throw new ApartmentNotFoundException(resident.getApartmentId());
            }
            residentDAO.addResident(resident);
            apartmentDAO.updateOccupancyStatus(resident.getApartmentId(), "Occupied");
        } catch (SQLException e) {
            throw new ApartmentException("Database error saving resident: " + e.getMessage(), e);
        }
    }

    public Resident getResident(String residentId) throws ApartmentException {
        try {
            Resident r = residentDAO.getResidentById(residentId);
            if (r == null) {
                throw new ResidentNotFoundException(residentId);
            }
            return r;
        } catch (SQLException e) {
            throw new ApartmentException("Error querying resident: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // MAINTENANCE PAYMENT OPERATIONS
    // -------------------------------------------------------------------------
    public List<MaintenancePayment> getAllPayments() throws ApartmentException {
        try {
            return maintenanceDAO.getAllPayments();
        } catch (SQLException e) {
            throw new ApartmentException("Error fetching payments: " + e.getMessage(), e);
        }
    }

    public List<MaintenancePayment> getPendingPayments() throws ApartmentException {
        try {
            return maintenanceDAO.getPendingPayments();
        } catch (SQLException e) {
            throw new ApartmentException("Error fetching pending payments: " + e.getMessage(), e);
        }
    }

    public void processPayment(String paymentId, double amount, String mode) throws ApartmentException {
        if (amount <= 0) {
            throw new InvalidInputException("Payment amount must be greater than zero.");
        }
        try {
            String today = LocalDate.now().toString();
            boolean updated = maintenanceDAO.recordPayment(paymentId, amount, today, mode);
            if (!updated) {
                throw new InvalidInputException("Payment ID not found: " + paymentId);
            }
        } catch (SQLException e) {
            throw new ApartmentException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // COMPLAINT OPERATIONS
    // -------------------------------------------------------------------------
    public List<Complaint> getAllComplaints() throws ApartmentException {
        try {
            return complaintDAO.getAllComplaints();
        } catch (SQLException e) {
            throw new ApartmentException("Error retrieving complaints: " + e.getMessage(), e);
        }
    }

    public List<Complaint> getActiveComplaints() throws ApartmentException {
        try {
            return complaintDAO.getActiveComplaints();
        } catch (SQLException e) {
            throw new ApartmentException("Error retrieving active complaints: " + e.getMessage(), e);
        }
    }

    public void fileComplaint(Complaint complaint) throws ApartmentException {
        if (!InputValidator.isNotEmpty(complaint.getDescription())) {
            throw new InvalidInputException("Complaint description cannot be empty.");
        }
        try {
            complaintDAO.registerComplaint(complaint);
        } catch (SQLException e) {
            throw new ApartmentException("Error registering complaint: " + e.getMessage(), e);
        }
    }

    public void updateComplaintStatus(String complaintId, String newStatus, String staff, String remarks) throws ApartmentException {
        try {
            String resDate = newStatus.equalsIgnoreCase("Resolved") ? LocalDate.now().toString() : null;
            boolean updated = complaintDAO.updateStatus(complaintId, newStatus, resDate, staff, remarks);
            if (!updated) {
                throw new InvalidInputException("Complaint ID not found: " + complaintId);
            }
        } catch (SQLException e) {
            throw new ApartmentException("Error updating complaint: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // REPORTING & ANALYTICS (Unit 5: Lambdas and Streams)
    // -------------------------------------------------------------------------
    public Map<String, Long> getComplaintCountByCategory() throws ApartmentException {
        return getAllComplaints().stream()
                .collect(Collectors.groupingBy(Complaint::getCategory, Collectors.counting()));
    }

    public double getTotalOutstandingBalance() throws ApartmentException {
        return getPendingPayments().stream()
                .mapToDouble(MaintenancePayment::getBalance)
                .sum();
    }
}
