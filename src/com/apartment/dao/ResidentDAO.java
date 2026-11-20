package com.apartment.dao;

import com.apartment.model.Resident;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Residents.
 */
public class ResidentDAO {

    public List<Resident> getAllResidents() throws SQLException {
        List<Resident> list = new ArrayList<>();
        String sql = "SELECT * FROM residents ORDER BY resident_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Resident(
                    rs.getString("resident_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("apartment_id"),
                    rs.getString("resident_type"),
                    rs.getString("move_in_date"),
                    rs.getString("emergency_contact")
                ));
            }
        }
        return list;
    }

    public Resident getResidentById(String residentId) throws SQLException {
        String sql = "SELECT * FROM residents WHERE resident_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, residentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Resident(
                        rs.getString("resident_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("apartment_id"),
                        rs.getString("resident_type"),
                        rs.getString("move_in_date"),
                        rs.getString("emergency_contact")
                    );
                }
            }
        }
        return null;
    }

    public Resident getResidentByApartment(String apartmentId) throws SQLException {
        String sql = "SELECT * FROM residents WHERE apartment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, apartmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Resident(
                        rs.getString("resident_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("apartment_id"),
                        rs.getString("resident_type"),
                        rs.getString("move_in_date"),
                        rs.getString("emergency_contact")
                    );
                }
            }
        }
        return null;
    }

    public boolean addResident(Resident resident) throws SQLException {
        String sql = "INSERT INTO residents (resident_id, name, email, phone, apartment_id, resident_type, move_in_date, emergency_contact) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resident.getResidentId());
            pstmt.setString(2, resident.getName());
            pstmt.setString(3, resident.getEmail());
            pstmt.setString(4, resident.getPhone());
            pstmt.setString(5, resident.getApartmentId());
            pstmt.setString(6, resident.getResidentType());
            pstmt.setString(7, resident.getMoveInDate());
            pstmt.setString(8, resident.getEmergencyContact());
            return pstmt.executeUpdate() > 0;
        }
    }
}
