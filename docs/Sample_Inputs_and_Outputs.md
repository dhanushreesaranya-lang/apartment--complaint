# Sample Inputs and Execution Outputs
## Apartment Maintenance and Resident Complaint Management System
**Scenario:** 18 (Apartment Maintenance and Resident Complaint Management System)  
**Institution:** Velalar College of Engineering and Technology (Autonomous), Erode – 638012  
**Department:** Information Technology

---

## 1. Application Startup Banner
```text
===========================================================================
   VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS) - ERODE     
                 DEPARTMENT OF INFORMATION TECHNOLOGY                      
  Scenario 18: Apartment Maintenance & Resident Complaint Management System 
===========================================================================

============================================================
                 MAIN NAVIGATION MENU                      
============================================================
  [1] Apartment Unit Management
  [2] Resident Profile Management
  [3] Monthly Maintenance & Payment Tracking
  [4] Resident Complaint Lifecycle Management
  [5] Analytical Reports & Summary Statistics
  [6] Exit Application
------------------------------------------------------------
Enter your choice (1-6): 
```

---

## 2. Sample Execution Traces

### Sample 1: Viewing All Registered Apartment Units
**Input:** `1` -> `1`  
**Console Output:**
```text
===========================================================================
  APARTMENT UNITS DIRECTORY
===========================================================================
+------------+------------+--------+--------------+------------+----------------------+
| Unit ID    | Block      | Floor  | Type         | Area(sqft) | Occupancy Status     | 
+------------+------------+--------+--------------+------------+----------------------+
| A-101      | Block A    | 1      | 2BHK         | 1200       | Occupied             | 
| A-102      | Block A    | 1      | 3BHK         | 1650       | Occupied             | 
| A-201      | Block A    | 2      | 2BHK         | 1200       | Occupied             | 
| A-202      | Block A    | 2      | 3BHK         | 1650       | Occupied             | 
| A-301      | Block A    | 3      | Penthouse    | 2400       | Occupied             | 
| A-302      | Block A    | 3      | 2BHK         | 1200       | Vacant               | 
| B-101      | Block B    | 1      | 1BHK         | 850        | Occupied             | 
| B-102      | Block B    | 1      | 2BHK         | 1250       | Occupied             | 
| B-201      | Block B    | 2      | 2BHK         | 1250       | Occupied             | 
| B-202      | Block B    | 2      | 3BHK         | 1700       | Occupied             | 
| B-301      | Block B    | 3      | 3BHK         | 1700       | Under Maintenance    | 
| B-302      | Block B    | 3      | Penthouse    | 2500       | Occupied             | 
| C-101      | Block C    | 1      | 2BHK         | 1300       | Occupied             | 
| C-102      | Block C    | 1      | 2BHK         | 1300       | Occupied             | 
| C-201      | Block C    | 2      | 3BHK         | 1800       | Occupied             | 
| C-202      | Block C    | 2      | 3BHK         | 1800       | Vacant               | 
+------------+------------+--------+--------------+------------+----------------------+
Total units registered: 16
```

---

### Sample 2: Registering a New Resident with Input Validation
**Input:** `2` -> `2`  
```text
Enter Resident ID (e.g., RES014): RES014
Enter Resident Full Name: Rengarajan M
Enter Email Address: rengarajan.m@gmail.com
Enter Contact Phone: 9842177889
Enter Assigned Apartment ID (e.g., A-302): A-302
Enter Resident Type (Owner/Tenant): Owner
Enter Emergency Contact Phone: 9842199887
```
**Console Output:**
```text
[SUCCESS] Resident Rengarajan M assigned to Unit A-302 successfully.
```

---

### Sample 3: Viewing Pending & Overdue Maintenance Dues
**Input:** `3` -> `2`  
**Console Output:**
```text
===========================================================================
  PENDING & OVERDUE MAINTENANCE DUES
===========================================================================
+-----------------+----------+----------+----------+-----------+-----------+------------+------------+--------------+-----------+
| Payment ID      | Unit     | Resident | Month    | Due(₹)    | Paid(₹)   | Due Date   | Pay Date   | Mode         | Status    | 
+-----------------+----------+----------+----------+-----------+-----------+------------+------------+--------------+-----------+
| PAY-2026-01-08  | B-201    | RES008   | 2026-01  | 3200.00   | 0.00      | 2026-01-10 | -          | Pending      | Overdue   | 
| PAY-2026-02-03  | A-201    | RES003   | 2026-02  | 3000.00   | 0.00      | 2026-02-10 | -          | Pending      | Overdue   | 
| PAY-2026-02-08  | B-201    | RES008   | 2026-02  | 3200.00   | 0.00      | 2026-02-10 | -          | Pending      | Overdue   | 
| PAY-2026-02-11  | C-101    | RES011   | 2026-02  | 3300.00   | 0.00      | 2026-02-10 | -          | Pending      | Overdue   | 
| PAY-2026-03-02  | A-102    | RES002   | 2026-03  | 4000.00   | 0.00      | 2026-03-10 | -          | Pending      | Pending   | 
| PAY-2026-03-03  | A-201    | RES003   | 2026-03  | 3000.00   | 0.00      | 2026-03-10 | -          | Pending      | Pending   | 
| PAY-2026-03-06  | B-101    | RES006   | 2026-03  | 2500.00   | 0.00      | 2026-03-10 | -          | Pending      | Pending   | 
| PAY-2026-03-08  | B-201    | RES008   | 2026-03  | 3200.00   | 0.00      | 2026-03-10 | -          | Pending      | Pending   | 
| PAY-2026-03-11  | C-101    | RES011   | 2026-03  | 3300.00   | 0.00      | 2026-03-10 | -          | Pending      | Pending   | 
| PAY-2026-03-13  | C-201    | RES013   | 2026-03  | 4500.00   | 0.00      | 2026-03-10 | -          | Pending      | Pending   | 
+-----------------+----------+----------+----------+-----------+-----------+------------+------------+--------------+-----------+
Records displayed: 10
```

---

### Sample 4: Recording a Maintenance Payment
**Input:** `3` -> `3`  
```text
Enter Payment Bill ID (e.g., PAY-2026-03-02): PAY-2026-03-02
Enter Amount to Pay: ₹4000.00
Enter Payment Mode (Online/UPI, Credit Card, Net Banking, Cash, Cheque): Online/UPI
```
**Console Output:**
```text
[SUCCESS] Payment of ₹4000.0 recorded successfully for bill PAY-2026-03-02.
```

---

### Sample 5: Registering and Updating a Resident Grievance
**Input:** `4` -> `3`  
```text
Enter Resident ID filing complaint: RES002
Enter Apartment Unit ID: A-102
Categories: Water Supply | Electrical | Cleanliness | Elevator | Security | Plumbing | Parking | Other
Enter Category: Electrical
Enter Priority (Low/Medium/High/Critical): High
Enter Detailed Description: Power tripping whenever geyser is turned on in guest bathroom.
```
**Console Output:**
```text
[SUCCESS] Complaint registered successfully! Ticket ID: CMP-2026-842
```

**Status Update Input:** `4` -> `4`  
```text
Enter Complaint Ticket ID (e.g., CMP-2026-014): CMP-2026-842
Enter New Status (In Progress / Resolved / Closed): In Progress
Enter Assigned Staff Name: Senthil (Electrician)
Enter Resolution Remarks: Inspected circuit; replacing 16A MCB.
```
**Console Output:**
```text
[SUCCESS] Complaint CMP-2026-842 updated to status 'In Progress'.
```

---

### Sample 6: Analytical Summary Reports
**Input:** `5` -> `1`  
**Console Output:**
```text
===========================================================================
  COMPLAINTS DISTRIBUTION BY FACILITY CATEGORY
===========================================================================
+---------------------------+-----------------+
| Facility Category         | Total Count     | 
+---------------------------+-----------------+
| Water Supply              | 3               | 
| Electrical                | 3               | 
| Cleanliness               | 3               | 
| Elevator                  | 3               | 
| Security                  | 2               | 
| Plumbing                  | 1               | 
+---------------------------+-----------------+
```

---

## 3. Python Data Science Pipeline Terminal Output
**Command:** `python python_eda/eda_visualization.py`  
**Terminal Output:**
```text
[1/4] Retrieving datasets from SQLite database...
[2/4] Preprocessing datasets and feature engineering...
Loaded: 16 apartments, 13 residents, 39 payment bills, 15 complaints.
[3/4] Generating exploratory data visualizations...
 -> Saved: python_eda/output_visualizations/01_payment_status_distribution.png
 -> Saved: python_eda/output_visualizations/02_complaints_by_category.png
 -> Saved: python_eda/output_visualizations/03_complaint_status_stacked.png
 -> Saved: python_eda/output_visualizations/04_monthly_collection_trend.png
 -> Saved: python_eda/output_visualizations/05_resolution_time_boxplot.png
 -> Saved: python_eda/output_visualizations/06_complaint_block_heatmap.png
[4/4] All 6 exploratory visualizations successfully exported!
```
