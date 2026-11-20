package com.apartment.model;

/**
 * Model representing a Resident Facility / Maintenance Complaint.
 * Course Alignment: 25ITT32 (Object Modeling, Status Tracking)
 */
public class Complaint {
    private String complaintId;
    private String residentId;
    private String apartmentId;
    private String category;      // Water Supply, Electrical, Cleanliness, Elevator, Security, Plumbing, Parking, Other
    private String priority;      // Low, Medium, High, Critical
    private String description;
    private String complaintDate;
    private String resolutionDate;
    private String assignedStaff;
    private String status;        // Open, In Progress, Resolved, Closed
    private String residentRemarks;

    public Complaint() {}

    public Complaint(String complaintId, String residentId, String apartmentId, 
                     String category, String priority, String description, 
                     String complaintDate, String resolutionDate, String assignedStaff, 
                     String status, String residentRemarks) {
        this.complaintId = complaintId;
        this.residentId = residentId;
        this.apartmentId = apartmentId;
        this.category = category;
        this.priority = priority;
        this.description = description;
        this.complaintDate = complaintDate;
        this.resolutionDate = resolutionDate;
        this.assignedStaff = assignedStaff;
        this.status = status;
        this.residentRemarks = residentRemarks;
    }

    public String getComplaintId() { return complaintId; }
    public void setComplaintId(String complaintId) { this.complaintId = complaintId; }

    public String getResidentId() { return residentId; }
    public void setResidentId(String residentId) { this.residentId = residentId; }

    public String getApartmentId() { return apartmentId; }
    public void setApartmentId(String apartmentId) { this.apartmentId = apartmentId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getComplaintDate() { return complaintDate; }
    public void setComplaintDate(String complaintDate) { this.complaintDate = complaintDate; }

    public String getResolutionDate() { return resolutionDate; }
    public void setResolutionDate(String resolutionDate) { this.resolutionDate = resolutionDate; }

    public String getAssignedStaff() { return assignedStaff; }
    public void setAssignedStaff(String assignedStaff) { this.assignedStaff = assignedStaff; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResidentRemarks() { return residentRemarks; }
    public void setResidentRemarks(String residentRemarks) { this.residentRemarks = residentRemarks; }

    @Override
    public String toString() {
        return String.format("[%s] Apt: %s | %s (%s) | Date: %s | Status: %s | Staff: %s",
                complaintId, apartmentId, category, priority, complaintDate, status, assignedStaff);
    }
}
