package com.apartment.dao;

import com.apartment.model.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Resident Complaints.
 */
public class ComplaintDAO {

    public List<Complaint> getAllComplaints() throws SQLException {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints ORDER BY complaint_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractComplaint(rs));
            }
        }
        return list;
    }

    public List<Complaint> getActiveComplaints() throws SQLException {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE status IN ('Open', 'In Progress') ORDER BY complaint_date ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractComplaint(rs));
            }
        }
        return list;
    }

    public boolean registerComplaint(Complaint c) throws SQLException {
        String sql = "INSERT INTO complaints (complaint_id, resident_id, apartment_id, category, priority, description, complaint_date, resolution_date, assigned_staff, status, resident_remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getComplaintId());
            pstmt.setString(2, c.getResidentId());
            pstmt.setString(3, c.getApartmentId());
            pstmt.setString(4, c.getCategory());
            pstmt.setString(5, c.getPriority());
            pstmt.setString(6, c.getDescription());
            pstmt.setString(7, c.getComplaintDate());
            pstmt.setString(8, c.getResolutionDate());
            pstmt.setString(9, c.getAssignedStaff());
            pstmt.setString(10, c.getStatus());
            pstmt.setString(11, c.getResidentRemarks());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(String complaintId, String newStatus, String resolutionDate, String assignedStaff, String remarks) throws SQLException {
        String sql = "UPDATE complaints SET status = ?, resolution_date = ?, assigned_staff = COALESCE(?, assigned_staff), resident_remarks = COALESCE(?, resident_remarks) WHERE complaint_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, resolutionDate);
            pstmt.setString(3, assignedStaff);
            pstmt.setString(4, remarks);
            pstmt.setString(5, complaintId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Complaint extractComplaint(ResultSet rs) throws SQLException {
        return new Complaint(
            rs.getString("complaint_id"),
            rs.getString("resident_id"),
            rs.getString("apartment_id"),
            rs.getString("category"),
            rs.getString("priority"),
            rs.getString("description"),
            rs.getString("complaint_date"),
            rs.getString("resolution_date"),
            rs.getString("assigned_staff"),
            rs.getString("status"),
            rs.getString("resident_remarks")
        );
    }
}
