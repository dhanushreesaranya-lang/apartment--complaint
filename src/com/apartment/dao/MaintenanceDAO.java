package com.apartment.dao;

import com.apartment.model.MaintenancePayment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Maintenance Payments.
 */
public class MaintenanceDAO {

    public List<MaintenancePayment> getAllPayments() throws SQLException {
        List<MaintenancePayment> list = new ArrayList<>();
        String sql = "SELECT * FROM maintenance_payments ORDER BY billing_month DESC, payment_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractPayment(rs));
            }
        }
        return list;
    }

    public List<MaintenancePayment> getPendingPayments() throws SQLException {
        List<MaintenancePayment> list = new ArrayList<>();
        String sql = "SELECT * FROM maintenance_payments WHERE payment_status IN ('Pending', 'Overdue') ORDER BY due_date ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractPayment(rs));
            }
        }
        return list;
    }

    public boolean recordPayment(String paymentId, double amountPaid, String paymentDate, String paymentMode) throws SQLException {
        String sql = "UPDATE maintenance_payments SET amount_paid = ?, payment_date = ?, payment_mode = ?, payment_status = 'Paid' WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amountPaid);
            pstmt.setString(2, paymentDate);
            pstmt.setString(3, paymentMode);
            pstmt.setString(4, paymentId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean addBill(MaintenancePayment p) throws SQLException {
        String sql = "INSERT INTO maintenance_payments (payment_id, apartment_id, resident_id, billing_month, amount_due, amount_paid, due_date, payment_date, payment_mode, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getPaymentId());
            pstmt.setString(2, p.getApartmentId());
            pstmt.setString(3, p.getResidentId());
            pstmt.setString(4, p.getBillingMonth());
            pstmt.setDouble(5, p.getAmountDue());
            pstmt.setDouble(6, p.getAmountPaid());
            pstmt.setString(7, p.getDueDate());
            pstmt.setString(8, p.getPaymentDate());
            pstmt.setString(9, p.getPaymentMode());
            pstmt.setString(10, p.getPaymentStatus());
            return pstmt.executeUpdate() > 0;
        }
    }

    private MaintenancePayment extractPayment(ResultSet rs) throws SQLException {
        return new MaintenancePayment(
            rs.getString("payment_id"),
            rs.getString("apartment_id"),
            rs.getString("resident_id"),
            rs.getString("billing_month"),
            rs.getDouble("amount_due"),
            rs.getDouble("amount_paid"),
            rs.getString("due_date"),
            rs.getString("payment_date"),
            rs.getString("payment_mode"),
            rs.getString("payment_status")
        );
    }
}
