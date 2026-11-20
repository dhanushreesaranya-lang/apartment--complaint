package com.apartment;

import com.apartment.exception.ApartmentException;
import com.apartment.model.Apartment;
import com.apartment.model.Complaint;
import com.apartment.model.MaintenancePayment;
import com.apartment.model.Resident;
import com.apartment.service.ApartmentService;
import com.apartment.util.TableFormatter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main Application Entry Point with interactive console menu.
 * Course Alignment: 25ITT32 (Java Programming - Full Application Integration)
 * Scenario 18: Apartment Maintenance and Resident Complaint Management System
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ApartmentService service = new ApartmentService();

    public static void main(String[] args) {
        System.out.println("===========================================================================");
        System.out.println("   VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS) - ERODE     ");
        System.out.println("                 DEPARTMENT OF INFORMATION TECHNOLOGY                      ");
        System.out.println("  Scenario 18: Apartment Maintenance & Resident Complaint Management System ");
        System.out.println("===========================================================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Enter your choice (1-6): ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        handleApartmentMenu();
                        break;
                    case "2":
                        handleResidentMenu();
                        break;
                    case "3":
                        handleMaintenanceMenu();
                        break;
                    case "4":
                        handleComplaintMenu();
                        break;
                    case "5":
                        handleReportsMenu();
                        break;
                    case "6":
                        running = false;
                        System.out.println("\nThank you for using Apartment Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("[!] Invalid option. Please choose between 1 and 6.");
                }
            } catch (ApartmentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[UNEXPECTED ERROR] " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                 MAIN NAVIGATION MENU                      ");
        System.out.println("=".repeat(60));
        System.out.println("  [1] Apartment Unit Management");
        System.out.println("  [2] Resident Profile Management");
        System.out.println("  [3] Monthly Maintenance & Payment Tracking");
        System.out.println("  [4] Resident Complaint Lifecycle Management");
        System.out.println("  [5] Analytical Reports & Summary Statistics");
        System.out.println("  [6] Exit Application");
        System.out.println("-".repeat(60));
    }

    // -------------------------------------------------------------------------
    // 1. APARTMENT SUBMENU
    // -------------------------------------------------------------------------
    private static void handleApartmentMenu() throws ApartmentException {
        System.out.println("\n--- APARTMENT UNIT MANAGEMENT ---");
        System.out.println("1. View All Apartment Units");
        System.out.println("2. Add New Apartment Unit");
        System.out.print("Select: ");
        String ch = scanner.nextLine().trim();

        if ("1".equals(ch)) {
            List<Apartment> list = service.getAllApartments();
            TableFormatter.printHeader("APARTMENT UNITS DIRECTORY");
            int[] widths = {10, 10, 6, 12, 10, 20};
            TableFormatter.printSeparator(widths);
            TableFormatter.printRow(widths, "Unit ID", "Block", "Floor", "Type", "Area(sqft)", "Occupancy Status");
            TableFormatter.printSeparator(widths);
            for (Apartment a : list) {
                TableFormatter.printRow(widths, a.getApartmentId(), a.getBlockNumber(), 
                        String.valueOf(a.getFloorNumber()), a.getApartmentType(), 
                        String.valueOf(a.getSquareFeet()), a.getOccupancyStatus());
            }
            TableFormatter.printSeparator(widths);
            System.out.println("Total units registered: " + list.size());
        } else if ("2".equals(ch)) {
            System.out.print("Enter Unit ID (e.g., C-301): ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter Block (e.g., Block C): ");
            String block = scanner.nextLine().trim();
            System.out.print("Enter Floor Number: ");
            int floor = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Apartment Type (1BHK/2BHK/3BHK/Penthouse): ");
            String type = scanner.nextLine().trim();
            System.out.print("Enter Square Feet Area: ");
            int sqft = Integer.parseInt(scanner.nextLine().trim());

            Apartment apt = new Apartment(id, block, floor, type, sqft, "Vacant");
            service.registerApartment(apt);
            System.out.println("[SUCCESS] New Apartment Unit " + id + " registered successfully as Vacant.");
        }
    }

    // -------------------------------------------------------------------------
    // 2. RESIDENT SUBMENU
    // -------------------------------------------------------------------------
    private static void handleResidentMenu() throws ApartmentException {
        System.out.println("\n--- RESIDENT PROFILE MANAGEMENT ---");
        System.out.println("1. View All Residents");
        System.out.println("2. Add New Resident");
        System.out.println("3. Search Resident by ID");
        System.out.print("Select: ");
        String ch = scanner.nextLine().trim();

        if ("1".equals(ch)) {
            List<Resident> list = service.getAllResidents();
            TableFormatter.printHeader("RESIDENTS DIRECTORY");
            int[] widths = {8, 20, 25, 12, 8, 8, 12};
            TableFormatter.printSeparator(widths);
            TableFormatter.printRow(widths, "Res ID", "Name", "Email", "Phone", "Unit", "Type", "Move-In Date");
            TableFormatter.printSeparator(widths);
            for (Resident r : list) {
                TableFormatter.printRow(widths, r.getResidentId(), r.getName(), r.getEmail(), 
                        r.getPhone(), r.getApartmentId(), r.getResidentType(), r.getMoveInDate());
            }
            TableFormatter.printSeparator(widths);
            System.out.println("Total residents registered: " + list.size());
        } else if ("2".equals(ch)) {
            System.out.print("Enter Resident ID (e.g., RES014): ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter Resident Full Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Contact Phone: ");
            String phone = scanner.nextLine().trim();
            System.out.print("Enter Assigned Apartment ID (e.g., A-302): ");
            String aptId = scanner.nextLine().trim();
            System.out.print("Enter Resident Type (Owner/Tenant): ");
            String type = scanner.nextLine().trim();
            System.out.print("Enter Emergency Contact Phone: ");
            String emContact = scanner.nextLine().trim();

            Resident r = new Resident(id, name, email, phone, aptId, type, LocalDate.now().toString(), emContact);
            service.registerResident(r);
            System.out.println("[SUCCESS] Resident " + name + " assigned to Unit " + aptId + " successfully.");
        } else if ("3".equals(ch)) {
            System.out.print("Enter Resident ID to search: ");
            String id = scanner.nextLine().trim();
            Resident r = service.getResident(id);
            System.out.println("\n[RESIDENT DETAILS]");
            System.out.println("Resident ID       : " + r.getResidentId());
            System.out.println("Full Name         : " + r.getName());
            System.out.println("Email             : " + r.getEmail());
            System.out.println("Phone             : " + r.getPhone());
            System.out.println("Apartment Assigned: " + r.getApartmentId());
            System.out.println("Occupancy Type    : " + r.getResidentType());
            System.out.println("Move-in Date      : " + r.getMoveInDate());
            System.out.println("Emergency Contact : " + r.getEmergencyContact());
        }
    }

    // -------------------------------------------------------------------------
    // 3. MAINTENANCE SUBMENU
    // -------------------------------------------------------------------------
    private static void handleMaintenanceMenu() throws ApartmentException {
        System.out.println("\n--- MAINTENANCE & PAYMENT TRACKING ---");
        System.out.println("1. View All Maintenance Billing Records");
        System.out.println("2. View Outstanding / Pending Payments");
        System.out.println("3. Record Payment for an Apartment");
        System.out.print("Select: ");
        String ch = scanner.nextLine().trim();

        if ("1".equals(ch)) {
            List<MaintenancePayment> list = service.getAllPayments();
            displayPaymentsTable("ALL MAINTENANCE CHARGES & PAYMENTS", list);
        } else if ("2".equals(ch)) {
            List<MaintenancePayment> list = service.getPendingPayments();
            displayPaymentsTable("PENDING & OVERDUE MAINTENANCE DUES", list);
        } else if ("3".equals(ch)) {
            System.out.print("Enter Payment Bill ID (e.g., PAY-2026-03-02): ");
            String pid = scanner.nextLine().trim();
            System.out.print("Enter Amount to Pay: ₹");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter Payment Mode (Online/UPI, Credit Card, Net Banking, Cash, Cheque): ");
            String mode = scanner.nextLine().trim();

            service.processPayment(pid, amount, mode);
            System.out.println("[SUCCESS] Payment of ₹" + amount + " recorded successfully for bill " + pid + ".");
        }
    }

    private static void displayPaymentsTable(String title, List<MaintenancePayment> list) {
        TableFormatter.printHeader(title);
        int[] widths = {15, 8, 8, 8, 9, 9, 10, 10, 12, 9};
        TableFormatter.printSeparator(widths);
        TableFormatter.printRow(widths, "Payment ID", "Unit", "Resident", "Month", "Due(₹)", "Paid(₹)", "Due Date", "Pay Date", "Mode", "Status");
        TableFormatter.printSeparator(widths);
        for (MaintenancePayment p : list) {
            TableFormatter.printRow(widths, p.getPaymentId(), p.getApartmentId(), p.getResidentId(),
                    p.getBillingMonth(), String.format("%.2f", p.getAmountDue()), 
                    String.format("%.2f", p.getAmountPaid()), p.getDueDate(), 
                    (p.getPaymentDate() == null ? "-" : p.getPaymentDate()), 
                    p.getPaymentMode(), p.getPaymentStatus());
        }
        TableFormatter.printSeparator(widths);
        System.out.println("Records displayed: " + list.size());
    }

    // -------------------------------------------------------------------------
    // 4. COMPLAINT SUBMENU
    // -------------------------------------------------------------------------
    private static void handleComplaintMenu() throws ApartmentException {
        System.out.println("\n--- RESIDENT COMPLAINT MANAGEMENT ---");
        System.out.println("1. View All Complaints");
        System.out.println("2. View Active (Open / In Progress) Complaints");
        System.out.println("3. Register New Complaint");
        System.out.println("4. Update Complaint Status & Assign Staff");
        System.out.print("Select: ");
        String ch = scanner.nextLine().trim();

        if ("1".equals(ch)) {
            List<Complaint> list = service.getAllComplaints();
            displayComplaintsTable("RESIDENT COMPLAINT LOGS", list);
        } else if ("2".equals(ch)) {
            List<Complaint> list = service.getActiveComplaints();
            displayComplaintsTable("ACTIVE SERVICE REQUESTS (UNRESOLVED)", list);
        } else if ("3".equals(ch)) {
            String cid = "CMP-2026-" + String.format("%03d", (int)(Math.random() * 900) + 100);
            System.out.print("Enter Resident ID filing complaint: ");
            String resId = scanner.nextLine().trim();
            System.out.print("Enter Apartment Unit ID: ");
            String aptId = scanner.nextLine().trim();
            System.out.println("Categories: Water Supply | Electrical | Cleanliness | Elevator | Security | Plumbing | Parking | Other");
            System.out.print("Enter Category: ");
            String cat = scanner.nextLine().trim();
            System.out.print("Enter Priority (Low/Medium/High/Critical): ");
            String prio = scanner.nextLine().trim();
            System.out.print("Enter Detailed Description: ");
            String desc = scanner.nextLine().trim();

            Complaint c = new Complaint(cid, resId, aptId, cat, prio, desc, 
                    LocalDate.now().toString(), null, "Unassigned", "Open", "");
            service.fileComplaint(c);
            System.out.println("[SUCCESS] Complaint registered successfully! Ticket ID: " + cid);
        } else if ("4".equals(ch)) {
            System.out.print("Enter Complaint Ticket ID (e.g., CMP-2026-014): ");
            String cid = scanner.nextLine().trim();
            System.out.print("Enter New Status (In Progress / Resolved / Closed): ");
            String status = scanner.nextLine().trim();
            System.out.print("Enter Assigned Staff Name: ");
            String staff = scanner.nextLine().trim();
            System.out.print("Enter Resolution Remarks: ");
            String remarks = scanner.nextLine().trim();

            service.updateComplaintStatus(cid, status, staff, remarks);
            System.out.println("[SUCCESS] Complaint " + cid + " updated to status '" + status + "'.");
        }
    }

    private static void displayComplaintsTable(String title, List<Complaint> list) {
        TableFormatter.printHeader(title);
        int[] widths = {12, 8, 14, 8, 30, 11, 12, 18};
        TableFormatter.printSeparator(widths);
        TableFormatter.printRow(widths, "Ticket ID", "Unit", "Category", "Priority", "Description", "Date", "Status", "Assigned Staff");
        TableFormatter.printSeparator(widths);
        for (Complaint c : list) {
            TableFormatter.printRow(widths, c.getComplaintId(), c.getApartmentId(), c.getCategory(),
                    c.getPriority(), c.getDescription(), c.getComplaintDate(), c.getStatus(), c.getAssignedStaff());
        }
        TableFormatter.printSeparator(widths);
        System.out.println("Tickets count: " + list.size());
    }

    // -------------------------------------------------------------------------
    // 5. REPORTS & SUMMARY SUBMENU
    // -------------------------------------------------------------------------
    private static void handleReportsMenu() throws ApartmentException {
        System.out.println("\n--- ANALYTICAL REPORTS & SUMMARY ---");
        System.out.println("1. Category-Wise Complaint Breakdown");
        System.out.println("2. Outstanding Maintenance Dues Financial Summary");
        System.out.print("Select: ");
        String ch = scanner.nextLine().trim();

        if ("1".equals(ch)) {
            Map<String, Long> categoryMap = service.getComplaintCountByCategory();
            TableFormatter.printHeader("COMPLAINTS DISTRIBUTION BY FACILITY CATEGORY");
            int[] widths = {25, 15};
            TableFormatter.printSeparator(widths);
            TableFormatter.printRow(widths, "Facility Category", "Total Count");
            TableFormatter.printSeparator(widths);
            categoryMap.forEach((cat, cnt) -> TableFormatter.printRow(widths, cat, String.valueOf(cnt)));
            TableFormatter.printSeparator(widths);
        } else if ("2".equals(ch)) {
            List<MaintenancePayment> pending = service.getPendingPayments();
            double totalDue = service.getTotalOutstandingBalance();
            System.out.println("\n========================================================");
            System.out.println("       MAINTENANCE DUES FINANCIAL EXECUTIVE SUMMARY     ");
            System.out.println("========================================================");
            System.out.println(" Total Pending Invoices : " + pending.size());
            System.out.printf(" Total Outstanding Dues : ₹%.2f%n", totalDue);
            System.out.println(" Action Required        : Send payment reminder notifications.");
            System.out.println("========================================================");
        }
    }
}
