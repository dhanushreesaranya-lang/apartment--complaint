package com.apartment.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton Database Connection Manager.
 * Course Alignment: 25ITT32 (Unit 4: Database Connection / JDBC)
 */
public class DatabaseConnection {
    private static Connection connection = null;

    private static String getDatabaseUrl() {
        // Look for database relative to project directory
        File dbFile = new File("database/apartment_system.db");
        if (!dbFile.exists()) {
            File parentDb = new File("../database/apartment_system.db");
            if (parentDb.exists()) {
                return "jdbc:sqlite:" + parentDb.getAbsolutePath();
            }
        }
        return "jdbc:sqlite:" + dbFile.getAbsolutePath();
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                System.err.println("SQLite JDBC Driver not found in classpath.");
            }
            connection = DriverManager.getConnection(getDatabaseUrl());
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {}
        }
    }
}
