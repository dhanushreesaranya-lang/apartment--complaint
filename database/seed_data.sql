-- =============================================================================
-- APARTMENT MAINTENANCE AND RESIDENT COMPLAINT MANAGEMENT SYSTEM
-- Seed Data Script (Realistic data across multiple blocks, apartments, dues, complaints)
-- =============================================================================

-- 1. Insert Apartments (Blocks A, B, C)
INSERT INTO apartments (apartment_id, block_number, floor_number, apartment_type, square_feet, occupancy_status) VALUES
('A-101', 'Block A', 1, '2BHK', 1200, 'Occupied'),
('A-102', 'Block A', 1, '3BHK', 1650, 'Occupied'),
('A-201', 'Block A', 2, '2BHK', 1200, 'Occupied'),
('A-202', 'Block A', 2, '3BHK', 1650, 'Occupied'),
('A-301', 'Block A', 3, 'Penthouse', 2400, 'Occupied'),
('A-302', 'Block A', 3, '2BHK', 1200, 'Vacant'),
('B-101', 'Block B', 1, '1BHK', 850, 'Occupied'),
('B-102', 'Block B', 1, '2BHK', 1250, 'Occupied'),
('B-201', 'Block B', 2, '2BHK', 1250, 'Occupied'),
('B-202', 'Block B', 2, '3BHK', 1700, 'Occupied'),
('B-301', 'Block B', 3, '3BHK', 1700, 'Under Maintenance'),
('B-302', 'Block B', 3, 'Penthouse', 2500, 'Occupied'),
('C-101', 'Block C', 1, '2BHK', 1300, 'Occupied'),
('C-102', 'Block C', 1, '2BHK', 1300, 'Occupied'),
('C-201', 'Block C', 2, '3BHK', 1800, 'Occupied'),
('C-202', 'Block C', 2, '3BHK', 1800, 'Vacant');

-- 2. Insert Residents
INSERT INTO residents (resident_id, name, email, phone, apartment_id, resident_type, move_in_date, emergency_contact) VALUES
('RES001', 'Arun Kumar', 'arun.kumar@gmail.com', '9842112345', 'A-101', 'Owner', '2024-01-15', '9842199991'),
('RES002', 'Priya Senthil', 'priya.senthil@outlook.com', '9842223456', 'A-102', 'Owner', '2024-02-10', '9842299992'),
('RES003', 'Rajesh K', 'rajesh.k@yahoo.com', '9842334567', 'A-201', 'Tenant', '2024-06-01', '9842399993'),
('RES004', 'Deepa Murugan', 'deepa.m@gmail.com', '9842445678', 'A-202', 'Owner', '2023-11-20', '9842499994'),
('RES005', 'Karthik Raja', 'karthik.raja@hotmail.com', '9842556789', 'A-301', 'Owner', '2023-08-05', '9842599995'),
('RES006', 'Sneha Lakshmi', 'sneha.l@gmail.com', '9842667890', 'B-101', 'Tenant', '2025-01-10', '9842699996'),
('RES007', 'Venkatesh Babu', 'venky.babu@gmail.com', '9842778901', 'B-102', 'Owner', '2024-04-12', '9842799997'),
('RES008', 'Meenakshi Sundaram', 'meena.sundaram@gmail.com', '9842889012', 'B-201', 'Tenant', '2024-07-25', '9842899998'),
('RES009', 'Gopalakrishnan V', 'gopal.v@yahoo.com', '9842990123', 'B-202', 'Owner', '2023-10-14', '9842999999'),
('RES010', 'Divya Bharathi', 'divya.b@outlook.com', '9842001234', 'B-302', 'Owner', '2023-09-01', '9842099990'),
('RES011', 'Suresh Babu', 'suresh.b@gmail.com', '9843112233', 'C-101', 'Tenant', '2024-12-01', '9843199991'),
('RES012', 'Anitha Radhakrishnan', 'anitha.r@gmail.com', '9843223344', 'C-102', 'Owner', '2024-03-18', '9843299992'),
('RES013', 'Balaji Subramanian', 'balaji.subbu@gmail.com', '9843334455', 'C-201', 'Owner', '2023-12-15', '9843399993');

-- 3. Insert Maintenance Payments (Q1 2026 / Recent Months)
INSERT INTO maintenance_payments (payment_id, apartment_id, resident_id, billing_month, amount_due, amount_paid, due_date, payment_date, payment_mode, payment_status) VALUES
-- January 2026
('PAY-2026-01-01', 'A-101', 'RES001', '2026-01', 3000.00, 3000.00, '2026-01-10', '2026-01-05', 'Online/UPI', 'Paid'),
('PAY-2026-01-02', 'A-102', 'RES002', '2026-01', 4000.00, 4000.00, '2026-01-10', '2026-01-08', 'Net Banking', 'Paid'),
('PAY-2026-01-03', 'A-201', 'RES003', '2026-01', 3000.00, 3000.00, '2026-01-10', '2026-01-09', 'Online/UPI', 'Paid'),
('PAY-2026-01-04', 'A-202', 'RES004', '2026-01', 4000.00, 4000.00, '2026-01-10', '2026-01-04', 'Credit Card', 'Paid'),
('PAY-2026-01-05', 'A-301', 'RES005', '2026-01', 5500.00, 5500.00, '2026-01-10', '2026-01-07', 'Online/UPI', 'Paid'),
('PAY-2026-01-06', 'B-101', 'RES006', '2026-01', 2500.00, 2500.00, '2026-01-10', '2026-01-10', 'Cash', 'Paid'),
('PAY-2026-01-07', 'B-102', 'RES007', '2026-01', 3200.00, 3200.00, '2026-01-10', '2026-01-06', 'Online/UPI', 'Paid'),
('PAY-2026-01-08', 'B-201', 'RES008', '2026-01', 3200.00, 0.00,    '2026-01-10', NULL,         'Pending', 'Overdue'),
('PAY-2026-01-09', 'B-202', 'RES009', '2026-01', 4200.00, 4200.00, '2026-01-10', '2026-01-09', 'Cheque', 'Paid'),
('PAY-2026-01-10', 'B-302', 'RES010', '2026-01', 5800.00, 5800.00, '2026-01-10', '2026-01-02', 'Online/UPI', 'Paid'),
('PAY-2026-01-11', 'C-101', 'RES011', '2026-01', 3300.00, 3300.00, '2026-01-10', '2026-01-10', 'Online/UPI', 'Paid'),
('PAY-2026-01-12', 'C-102', 'RES012', '2026-01', 3300.00, 3300.00, '2026-01-10', '2026-01-05', 'Net Banking', 'Paid'),
('PAY-2026-01-13', 'C-201', 'RES013', '2026-01', 4500.00, 4500.00, '2026-01-10', '2026-01-08', 'Credit Card', 'Paid'),

-- February 2026
('PAY-2026-02-01', 'A-101', 'RES001', '2026-02', 3000.00, 3000.00, '2026-02-10', '2026-02-04', 'Online/UPI', 'Paid'),
('PAY-2026-02-02', 'A-102', 'RES002', '2026-02', 4000.00, 4000.00, '2026-02-10', '2026-02-07', 'Net Banking', 'Paid'),
('PAY-2026-02-03', 'A-201', 'RES003', '2026-02', 3000.00, 0.00,    '2026-02-10', NULL,         'Pending', 'Overdue'),
('PAY-2026-02-04', 'A-202', 'RES004', '2026-02', 4000.00, 4000.00, '2026-02-10', '2026-02-06', 'Online/UPI', 'Paid'),
('PAY-2026-02-05', 'A-301', 'RES005', '2026-02', 5500.00, 5500.00, '2026-02-10', '2026-02-05', 'Online/UPI', 'Paid'),
('PAY-2026-02-06', 'B-101', 'RES006', '2026-02', 2500.00, 2500.00, '2026-02-10', '2026-02-09', 'Cash', 'Paid'),
('PAY-2026-02-07', 'B-102', 'RES007', '2026-02', 3200.00, 3200.00, '2026-02-10', '2026-02-08', 'Online/UPI', 'Paid'),
('PAY-2026-02-08', 'B-201', 'RES008', '2026-02', 3200.00, 0.00,    '2026-02-10', NULL,         'Pending', 'Overdue'),
('PAY-2026-02-09', 'B-202', 'RES009', '2026-02', 4200.00, 4200.00, '2026-02-10', '2026-02-10', 'Net Banking', 'Paid'),
('PAY-2026-02-10', 'B-302', 'RES010', '2026-02', 5800.00, 5800.00, '2026-02-10', '2026-02-03', 'Credit Card', 'Paid'),
('PAY-2026-02-11', 'C-101', 'RES011', '2026-02', 3300.00, 0.00,    '2026-02-10', NULL,         'Pending', 'Overdue'),
('PAY-2026-02-12', 'C-102', 'RES012', '2026-02', 3300.00, 3300.00, '2026-02-10', '2026-02-07', 'Online/UPI', 'Paid'),
('PAY-2026-02-13', 'C-201', 'RES013', '2026-02', 4500.00, 4500.00, '2026-02-10', '2026-02-08', 'Cheque', 'Paid'),

-- March 2026 (Current Active Billing)
('PAY-2026-03-01', 'A-101', 'RES001', '2026-03', 3000.00, 3000.00, '2026-03-10', '2026-03-02', 'Online/UPI', 'Paid'),
('PAY-2026-03-02', 'A-102', 'RES002', '2026-03', 4000.00, 0.00,    '2026-03-10', NULL,         'Pending', 'Pending'),
('PAY-2026-03-03', 'A-201', 'RES003', '2026-03', 3000.00, 0.00,    '2026-03-10', NULL,         'Pending', 'Pending'),
('PAY-2026-03-04', 'A-202', 'RES004', '2026-03', 4000.00, 4000.00, '2026-03-10', '2026-03-05', 'Online/UPI', 'Paid'),
('PAY-2026-03-05', 'A-301', 'RES005', '2026-03', 5500.00, 5500.00, '2026-03-10', '2026-03-04', 'Credit Card', 'Paid'),
('PAY-2026-03-06', 'B-101', 'RES006', '2026-03', 2500.00, 0.00,    '2026-03-10', NULL,         'Pending', 'Pending'),
('PAY-2026-03-07', 'B-102', 'RES007', '2026-03', 3200.00, 3200.00, '2026-03-10', '2026-03-07', 'Net Banking', 'Paid'),
('PAY-2026-03-08', 'B-201', 'RES008', '2026-03', 3200.00, 0.00,    '2026-03-10', NULL,         'Pending', 'Pending'),
('PAY-2026-03-09', 'B-202', 'RES009', '2026-03', 4200.00, 4200.00, '2026-03-10', '2026-03-06', 'Online/UPI', 'Paid'),
('PAY-2026-03-10', 'B-302', 'RES010', '2026-03', 5800.00, 5800.00, '2026-03-10', '2026-03-01', 'Online/UPI', 'Paid'),
('PAY-2026-03-11', 'C-101', 'RES011', '2026-03', 3300.00, 0.00,    '2026-03-10', NULL,         'Pending', 'Pending'),
('PAY-2026-03-12', 'C-102', 'RES012', '2026-03', 3300.00, 3300.00, '2026-03-10', '2026-03-08', 'Online/UPI', 'Paid'),
('PAY-2026-03-13', 'C-201', 'RES013', '2026-03', 4500.00, 0.00,    '2026-03-10', NULL,         'Pending', 'Pending');

-- 4. Insert Resident Complaints
INSERT INTO complaints (complaint_id, resident_id, apartment_id, category, priority, description, complaint_date, resolution_date, assigned_staff, status, resident_remarks) VALUES
('CMP-2026-001', 'RES001', 'A-101', 'Water Supply', 'High', 'Low water pressure in kitchen faucet and bathroom', '2026-01-04', '2026-01-06', 'Ramesh (Plumber)', 'Resolved', 'Pressure restored satisfactorily'),
('CMP-2026-002', 'RES002', 'A-102', 'Electrical', 'Critical', 'Main corridor switchboard sparking intermittently', '2026-01-11', '2026-01-12', 'Senthil (Electrician)', 'Resolved', 'Replaced faulty circuit breaker'),
('CMP-2026-003', 'RES003', 'A-201', 'Cleanliness', 'Medium', 'Garbage collection missed for two consecutive days', '2026-01-15', '2026-01-16', 'Murugan (Housekeeping)', 'Resolved', 'Waste cleared and bin sanitized'),
('CMP-2026-004', 'RES004', 'A-202', 'Elevator', 'High', 'Block A lift jerky motion between 2nd and 3rd floor', '2026-01-20', '2026-01-23', 'Schindler Service Team', 'Resolved', 'Guide rail lubrication and cable alignment done'),
('CMP-2026-005', 'RES005', 'A-301', 'Plumbing', 'Medium', 'Seepage near terrace rainwater drain pipe', '2026-02-01', '2026-02-05', 'Ramesh (Plumber)', 'Resolved', 'Waterproofing patch applied'),
('CMP-2026-006', 'RES006', 'B-101', 'Electrical', 'Medium', 'Corridor lighting flickering on 1st floor Block B', '2026-02-08', '2026-02-09', 'Senthil (Electrician)', 'Resolved', 'LED driver replaced'),
('CMP-2026-007', 'RES007', 'B-102', 'Security', 'Low', 'Visitor parking gate access sensor delays entry', '2026-02-14', '2026-02-18', 'Security Agency Lead', 'Resolved', 'Sensor calibrated'),
('CMP-2026-008', 'RES008', 'B-201', 'Water Supply', 'High', 'Discolored yellowish tap water received in master bedroom', '2026-02-22', '2026-02-24', 'Ramesh (Plumber)', 'Resolved', 'Overhead tank flushed and cleaned'),
('CMP-2026-009', 'RES009', 'B-202', 'Cleanliness', 'Low', 'Staircase railing in Block B needs dusting and mop cleaning', '2026-02-28', '2026-03-01', 'Murugan (Housekeeping)', 'Resolved', 'Cleaned thoroughly'),
('CMP-2026-010', 'RES010', 'B-302', 'Elevator', 'Critical', 'Lift stopped suddenly with alarm buzzer ringing', '2026-03-02', '2026-03-02', 'Schindler Service Team', 'Resolved', 'Emergency sensor reset; door lock adjusted'),
('CMP-2026-011', 'RES011', 'C-101', 'Water Supply', 'High', 'Water motor not pumping water to Block C overhead tank', '2026-03-05', NULL, 'Ramesh (Plumber)', 'In Progress', 'Pump motor starter capacitor under replacement'),
('CMP-2026-012', 'RES012', 'C-102', 'Electrical', 'Medium', 'Power fluctuation observed during evening peak hours', '2026-03-08', NULL, 'Senthil (Electrician)', 'In Progress', 'Phase load balancing in progress'),
('CMP-2026-013', 'RES013', 'C-201', 'Cleanliness', 'Medium', 'Basement drainage smelling foul near parking slot 45', '2026-03-12', NULL, 'Murugan (Housekeeping)', 'In Progress', 'Drain jetting and chemical deodorizer scheduled'),
('CMP-2026-014', 'RES001', 'A-101', 'Security', 'Medium', 'CCTV camera near Block A entrance has blurry feed', '2026-03-15', NULL, 'Security Agency Lead', 'Open', 'Technician visit pending'),
('CMP-2026-015', 'RES003', 'A-201', 'Elevator', 'High', 'Block A lift door sensor not detecting obstacle', '2026-03-16', NULL, 'Schindler Service Team', 'Open', 'Awaiting service engineer');
