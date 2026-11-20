-- =============================================================================
-- APARTMENT MAINTENANCE AND RESIDENT COMPLAINT MANAGEMENT SYSTEM
-- Course Alignment: 25ITT31 - Database Management Systems (Units 1, 2, 3, 4)
-- SQL Queries Demonstrating:
-- 1. Basic & Aggregate Queries
-- 2. Multi-Table Joins (INNER, LEFT, Self/Correlated)
-- 3. Nested Subqueries & Correlated Subqueries
-- 4. Analytical Grouping & HAVING Clauses
-- 5. Set Operations & Advanced Views
-- =============================================================================

-- -----------------------------------------------------------------------------
-- QUERY 1: Retrieve All Occupied Apartments with Resident Details (INNER JOIN)
-- -----------------------------------------------------------------------------
SELECT 
    a.apartment_id,
    a.block_number,
    a.apartment_type,
    a.square_feet,
    r.resident_id,
    r.name AS resident_name,
    r.phone,
    r.resident_type
FROM apartments a
JOIN residents r ON a.apartment_id = r.apartment_id
ORDER BY a.block_number, a.apartment_id;

-- -----------------------------------------------------------------------------
-- QUERY 2: Identify Residents with Overdue/Pending Maintenance Payments (JOIN + WHERE)
-- -----------------------------------------------------------------------------
SELECT 
    p.payment_id,
    p.apartment_id,
    r.name AS resident_name,
    r.phone,
    p.billing_month,
    p.amount_due,
    p.due_date,
    p.payment_status
FROM maintenance_payments p
JOIN residents r ON p.resident_id = r.resident_id
WHERE p.payment_status IN ('Pending', 'Overdue')
ORDER BY p.due_date ASC;

-- -----------------------------------------------------------------------------
-- QUERY 3: Monthly Maintenance Collection Summary (AGGREGATION & GROUP BY)
-- -----------------------------------------------------------------------------
SELECT 
    billing_month,
    COUNT(payment_id) AS total_bills_generated,
    SUM(amount_due) AS total_demand,
    SUM(amount_paid) AS total_collected,
    (SUM(amount_due) - SUM(amount_paid)) AS total_outstanding,
    ROUND((SUM(amount_paid) * 100.0 / SUM(amount_due)), 2) AS collection_percentage
FROM maintenance_payments
GROUP BY billing_month
ORDER BY billing_month ASC;

-- -----------------------------------------------------------------------------
-- QUERY 4: Complaint Statistics Grouped by Category and Current Status (PIVOT/CONDITIONAL AGGREGATION)
-- -----------------------------------------------------------------------------
SELECT 
    category,
    COUNT(*) AS total_complaints,
    SUM(CASE WHEN status = 'Open' THEN 1 ELSE 0 END) AS open_count,
    SUM(CASE WHEN status = 'In Progress' THEN 1 ELSE 0 END) AS in_progress_count,
    SUM(CASE WHEN status = 'Resolved' THEN 1 ELSE 0 END) AS resolved_count,
    ROUND((SUM(CASE WHEN status = 'Resolved' THEN 1.0 ELSE 0 END) * 100.0 / COUNT(*)), 1) AS resolution_rate_pct
FROM complaints
GROUP BY category
ORDER BY total_complaints DESC;

-- -----------------------------------------------------------------------------
-- QUERY 5: Nested Subquery - Apartments where Maintenance has NEVER been Overdue
-- -----------------------------------------------------------------------------
SELECT 
    a.apartment_id,
    a.block_number,
    a.apartment_type
FROM apartments a
WHERE a.occupancy_status = 'Occupied'
AND a.apartment_id NOT IN (
    SELECT DISTINCT apartment_id 
    FROM maintenance_payments 
    WHERE payment_status = 'Overdue'
);

-- -----------------------------------------------------------------------------
-- QUERY 6: Correlated Subquery - Find Residents who logged more complaints than average
-- -----------------------------------------------------------------------------
SELECT 
    r.resident_id,
    r.name,
    r.apartment_id,
    COUNT(c.complaint_id) AS complaint_count
FROM residents r
JOIN complaints c ON r.resident_id = c.resident_id
GROUP BY r.resident_id, r.name, r.apartment_id
HAVING COUNT(c.complaint_id) > (
    SELECT AVG(complaint_tally) FROM (
        SELECT COUNT(complaint_id) AS complaint_tally 
        FROM complaints 
        GROUP BY resident_id
    ) AS avg_table
);

-- -----------------------------------------------------------------------------
-- QUERY 7: Staff Workload Distribution for Active Complaints
-- -----------------------------------------------------------------------------
SELECT 
    assigned_staff,
    COUNT(*) AS active_tasks,
    GROUP_CONCAT(complaint_id, ', ') AS complaint_ids
FROM complaints
WHERE status IN ('Open', 'In Progress')
GROUP BY assigned_staff
ORDER BY active_tasks DESC;

-- -----------------------------------------------------------------------------
-- QUERY 8: Apartment Occupancy Breakdown by Block (Cross-tabulation)
-- -----------------------------------------------------------------------------
SELECT 
    block_number,
    COUNT(*) AS total_apartments,
    SUM(CASE WHEN occupancy_status = 'Occupied' THEN 1 ELSE 0 END) AS occupied_units,
    SUM(CASE WHEN occupancy_status = 'Vacant' THEN 1 ELSE 0 END) AS vacant_units,
    SUM(CASE WHEN occupancy_status = 'Under Maintenance' THEN 1 ELSE 0 END) AS maintenance_units
FROM apartments
GROUP BY block_number;
