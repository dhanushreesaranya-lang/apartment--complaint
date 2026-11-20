-- =============================================================================
-- APARTMENT MAINTENANCE AND RESIDENT COMPLAINT MANAGEMENT SYSTEM
-- Course Alignment: 25ITT31 - Database Management Systems
-- Scenario 18 (Register No.: 18, 48, 78, 108)
-- DDL Script: Database Schema Creation
-- Compatible with SQLite and standard SQL (MySQL / PostgreSQL)
-- =============================================================================

-- Enable Foreign Key constraints (SQLite specific, harmless in standard SQL)
PRAGMA foreign_keys = ON;

-- 1. Table: APARTMENTS
-- Stores details about each physical apartment unit in the complex.
DROP TABLE IF EXISTS complaints;
DROP TABLE IF EXISTS maintenance_payments;
DROP TABLE IF EXISTS residents;
DROP TABLE IF EXISTS apartments;

CREATE TABLE apartments (
    apartment_id VARCHAR(10) PRIMARY KEY,
    block_number VARCHAR(10) NOT NULL,
    floor_number INT NOT NULL,
    apartment_type VARCHAR(20) NOT NULL,
    square_feet INT NOT NULL,
    occupancy_status VARCHAR(25) NOT NULL DEFAULT 'Vacant',
    CONSTRAINT chk_apartment_type CHECK (apartment_type IN ('1BHK', '2BHK', '3BHK', '4BHK', 'Penthouse')),
    CONSTRAINT chk_occupancy_status CHECK (occupancy_status IN ('Occupied', 'Vacant', 'Under Maintenance'))
);

-- 2. Table: RESIDENTS
-- Stores profile and contact information for occupants living in the apartment.
CREATE TABLE residents (
    resident_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    apartment_id VARCHAR(10) NOT NULL,
    resident_type VARCHAR(20) NOT NULL DEFAULT 'Owner',
    move_in_date DATE NOT NULL,
    emergency_contact VARCHAR(20),
    FOREIGN KEY (apartment_id) REFERENCES apartments(apartment_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_resident_type CHECK (resident_type IN ('Owner', 'Tenant'))
);

-- 3. Table: MAINTENANCE_PAYMENTS
-- Records monthly maintenance billing, charges, dues, and payment statuses.
CREATE TABLE maintenance_payments (
    payment_id VARCHAR(20) PRIMARY KEY,
    apartment_id VARCHAR(10) NOT NULL,
    resident_id VARCHAR(10) NOT NULL,
    billing_month VARCHAR(10) NOT NULL, -- Format: YYYY-MM
    amount_due DECIMAL(10,2) NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    due_date DATE NOT NULL,
    payment_date DATE,
    payment_mode VARCHAR(30) DEFAULT 'Pending',
    payment_status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (apartment_id) REFERENCES apartments(apartment_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (resident_id) REFERENCES residents(resident_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT chk_payment_mode CHECK (payment_mode IN ('Online/UPI', 'Credit Card', 'Net Banking', 'Cash', 'Cheque', 'Pending')),
    CONSTRAINT chk_payment_status CHECK (payment_status IN ('Paid', 'Pending', 'Overdue'))
);

-- 4. Table: COMPLAINTS
-- Tracks resident service requests, facility grievances, priority, and resolution lifecycle.
CREATE TABLE complaints (
    complaint_id VARCHAR(20) PRIMARY KEY,
    resident_id VARCHAR(10) NOT NULL,
    apartment_id VARCHAR(10) NOT NULL,
    category VARCHAR(50) NOT NULL,
    priority VARCHAR(20) NOT NULL DEFAULT 'Medium',
    description TEXT NOT NULL,
    complaint_date DATE NOT NULL,
    resolution_date DATE,
    assigned_staff VARCHAR(100) DEFAULT 'Unassigned',
    status VARCHAR(20) NOT NULL DEFAULT 'Open',
    resident_remarks TEXT,
    FOREIGN KEY (resident_id) REFERENCES residents(resident_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (apartment_id) REFERENCES apartments(apartment_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT chk_complaint_category CHECK (category IN ('Water Supply', 'Electrical', 'Cleanliness', 'Elevator', 'Security', 'Plumbing', 'Parking', 'Other')),
    CONSTRAINT chk_complaint_priority CHECK (priority IN ('Low', 'Medium', 'High', 'Critical')),
    CONSTRAINT chk_complaint_status CHECK (status IN ('Open', 'In Progress', 'Resolved', 'Closed'))
);

-- =============================================================================
-- INDEXES FOR QUERY OPTIMIZATION (Unit 3: Normalization & Query Optimization)
-- =============================================================================
CREATE INDEX idx_resident_apartment ON residents(apartment_id);
CREATE INDEX idx_payment_status ON maintenance_payments(payment_status);
CREATE INDEX idx_payment_month ON maintenance_payments(billing_month);
CREATE INDEX idx_complaint_status ON complaints(status);
CREATE INDEX idx_complaint_category ON complaints(category);

-- =============================================================================
-- VIEWS FOR REPORTING (Unit 2: SQL Views)
-- =============================================================================

-- View 1: Outstanding/Pending Maintenance Balances
CREATE VIEW view_pending_payments AS
SELECT 
    p.payment_id,
    p.apartment_id,
    r.resident_id,
    r.name AS resident_name,
    r.phone AS resident_phone,
    p.billing_month,
    p.amount_due,
    p.amount_paid,
    (p.amount_due - p.amount_paid) AS balance_due,
    p.due_date,
    p.payment_status
FROM maintenance_payments p
JOIN residents r ON p.resident_id = r.resident_id
WHERE p.payment_status IN ('Pending', 'Overdue');

-- View 2: Active (Unresolved) Complaints
CREATE VIEW view_active_complaints AS
SELECT 
    c.complaint_id,
    c.apartment_id,
    r.name AS resident_name,
    c.category,
    c.priority,
    c.description,
    c.complaint_date,
    c.assigned_staff,
    c.status
FROM complaints c
JOIN residents r ON c.resident_id = r.resident_id
WHERE c.status IN ('Open', 'In Progress');

-- View 3: Category-wise Complaint Summary
CREATE VIEW view_complaint_category_summary AS
SELECT 
    category,
    COUNT(*) AS total_complaints,
    SUM(CASE WHEN status = 'Resolved' OR status = 'Closed' THEN 1 ELSE 0 END) AS resolved_count,
    SUM(CASE WHEN status IN ('Open', 'In Progress') THEN 1 ELSE 0 END) AS pending_count
FROM complaints
GROUP BY category;
