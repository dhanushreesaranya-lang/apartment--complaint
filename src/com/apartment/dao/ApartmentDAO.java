package com.apartment.dao;

import com.apartment.model.Apartment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Apartments.
 */
public class ApartmentDAO {

    public List<Apartment> getAllApartments() throws SQLException {
        List<Apartment> list = new ArrayList<>();
        String sql = "SELECT * FROM apartments ORDER BY block_number, apartment_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Apartment(
                    rs.getString("apartment_id"),
                    rs.getString("block_number"),
                    rs.getInt("floor_number"),
                    rs.getString("apartment_type"),
                    rs.getInt("square_feet"),
                    rs.getString("occupancy_status")
                ));
            }
        }
        return list;
    }

    public Apartment getApartmentById(String apartmentId) throws SQLException {
        String sql = "SELECT * FROM apartments WHERE apartment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, apartmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Apartment(
                        rs.getString("apartment_id"),
                        rs.getString("block_number"),
                        rs.getInt("floor_number"),
                        rs.getString("apartment_type"),
                        rs.getInt("square_feet"),
                        rs.getString("occupancy_status")
                    );
                }
            }
        }
        return null;
    }

    public boolean addApartment(Apartment apt) throws SQLException {
        String sql = "INSERT INTO apartments (apartment_id, block_number, floor_number, apartment_type, square_feet, occupancy_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, apt.getApartmentId());
            pstmt.setString(2, apt.getBlockNumber());
            pstmt.setInt(3, apt.getFloorNumber());
            pstmt.setString(4, apt.getApartmentType());
            pstmt.setInt(5, apt.getSquareFeet());
            pstmt.setString(6, apt.getOccupancyStatus());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateOccupancyStatus(String apartmentId, String newStatus) throws SQLException {
        String sql = "UPDATE apartments SET occupancy_status = ? WHERE apartment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, apartmentId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
