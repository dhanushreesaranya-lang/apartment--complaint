package com.apartment.model;

/**
 * Model representing a Resident in an apartment.
 * Course Alignment: 25ITT32 (Object Modeling, Encapsulation)
 */
public class Resident {
    private String residentId;
    private String name;
    private String email;
    private String phone;
    private String apartmentId;
    private String residentType; // Owner, Tenant
    private String moveInDate;   // YYYY-MM-DD
    private String emergencyContact;

    public Resident() {}

    public Resident(String residentId, String name, String email, String phone, 
                    String apartmentId, String residentType, String moveInDate, String emergencyContact) {
        this.residentId = residentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.apartmentId = apartmentId;
        this.residentType = residentType;
        this.moveInDate = moveInDate;
        this.emergencyContact = emergencyContact;
    }

    public String getResidentId() { return residentId; }
    public void setResidentId(String residentId) { this.residentId = residentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getApartmentId() { return apartmentId; }
    public void setApartmentId(String apartmentId) { this.apartmentId = apartmentId; }

    public String getResidentType() { return residentType; }
    public void setResidentType(String residentType) { this.residentType = residentType; }

    public String getMoveInDate() { return moveInDate; }
    public void setMoveInDate(String moveInDate) { this.moveInDate = moveInDate; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Apt: %s | Phone: %s | Type: %s | Move-In: %s",
                residentId, name, apartmentId, phone, residentType, moveInDate);
    }
}
