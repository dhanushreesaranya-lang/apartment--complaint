package com.apartment.model;

/**
 * Model representing an Apartment unit in the complex.
 * Course Alignment: 25ITT32 (Classes, Encapsulation, Methods)
 */
public class Apartment {
    private String apartmentId;
    private String blockNumber;
    private int floorNumber;
    private String apartmentType; // e.g., 1BHK, 2BHK, 3BHK, Penthouse
    private int squareFeet;
    private String occupancyStatus; // Occupied, Vacant, Under Maintenance

    public Apartment() {}

    public Apartment(String apartmentId, String blockNumber, int floorNumber, 
                     String apartmentType, int squareFeet, String occupancyStatus) {
        this.apartmentId = apartmentId;
        this.blockNumber = blockNumber;
        this.floorNumber = floorNumber;
        this.apartmentType = apartmentType;
        this.squareFeet = squareFeet;
        this.occupancyStatus = occupancyStatus;
    }

    public String getApartmentId() { return apartmentId; }
    public void setApartmentId(String apartmentId) { this.apartmentId = apartmentId; }

    public String getBlockNumber() { return blockNumber; }
    public void setBlockNumber(String blockNumber) { this.blockNumber = blockNumber; }

    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }

    public String getApartmentType() { return apartmentType; }
    public void setApartmentType(String apartmentType) { this.apartmentType = apartmentType; }

    public int getSquareFeet() { return squareFeet; }
    public void setSquareFeet(int squareFeet) { this.squareFeet = squareFeet; }

    public String getOccupancyStatus() { return occupancyStatus; }
    public void setOccupancyStatus(String occupancyStatus) { this.occupancyStatus = occupancyStatus; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Floor: %d | Type: %s | %d sq.ft | Status: %s",
                apartmentId, blockNumber, floorNumber, apartmentType, squareFeet, occupancyStatus);
    }
}
