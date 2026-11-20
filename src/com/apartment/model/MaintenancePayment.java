package com.apartment.model;

/**
 * Model representing a Monthly Maintenance Fee Charge and Payment Record.
 * Course Alignment: 25ITT32 (Data Modeling, Business Logic)
 */
public class MaintenancePayment {
    private String paymentId;
    private String apartmentId;
    private String residentId;
    private String billingMonth; // Format: YYYY-MM
    private double amountDue;
    private double amountPaid;
    private String dueDate;
    private String paymentDate;
    private String paymentMode;   // Online/UPI, Credit Card, Net Banking, Cash, Cheque, Pending
    private String paymentStatus; // Paid, Pending, Overdue

    public MaintenancePayment() {}

    public MaintenancePayment(String paymentId, String apartmentId, String residentId, 
                              String billingMonth, double amountDue, double amountPaid, 
                              String dueDate, String paymentDate, String paymentMode, String paymentStatus) {
        this.paymentId = paymentId;
        this.apartmentId = apartmentId;
        this.residentId = residentId;
        this.billingMonth = billingMonth;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getApartmentId() { return apartmentId; }
    public void setApartmentId(String apartmentId) { this.apartmentId = apartmentId; }

    public String getResidentId() { return residentId; }
    public void setResidentId(String residentId) { this.residentId = residentId; }

    public String getBillingMonth() { return billingMonth; }
    public void setBillingMonth(String billingMonth) { this.billingMonth = billingMonth; }

    public double getAmountDue() { return amountDue; }
    public void setAmountDue(double amountDue) { this.amountDue = amountDue; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public double getBalance() { return amountDue - amountPaid; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    @Override
    public String toString() {
        return String.format("[%s] Apt: %s | Month: %s | Due: ₹%.2f | Paid: ₹%.2f | Status: %s",
                paymentId, apartmentId, billingMonth, amountDue, amountPaid, paymentStatus);
    }
}
