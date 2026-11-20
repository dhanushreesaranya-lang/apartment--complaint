# Software Testing Documentation and Test Suite
## Apartment Maintenance and Resident Complaint Management System
**Course Alignment:** 25ITT33 - Software Engineering (Unit 4: Verification and Validation, Testing Strategies)  
**Scenario:** 18 (Apartment Maintenance and Resident Complaint Management System)

---

## 1. Testing Strategy and Methodologies

The testing lifecycle follows IEEE standard verification and validation procedures across multiple testing tiers:

```
                      +-----------------------------+
                      |       ACCEPTANCE / EDA      |
                      |   Python Visual Validation  |
                      +-----------------------------+
                                     ^
                                     |
                      +-----------------------------+
                      |       SYSTEM TESTING        |
                      |   End-to-End CLI Scenarios  |
                      +-----------------------------+
                                     ^
                                     |
                      +-----------------------------+
                      |     INTEGRATION TESTING     |
                      |    DAO <-> JDBC Database    |
                      +-----------------------------+
                                     ^
                                     |
                      +-----------------------------+
                      |        UNIT TESTING         |
                      | Model / Validation Logic    |
                      +-----------------------------+
```

### 1.1 Testing Levels
1. **Unit Testing:** Testing isolated models, regex input validators, and business logic methods.
2. **Integration Testing:** Verifying communication between Java Service layer, DAO implementations, JDBC driver, and SQLite database.
3. **System Testing:** Exercising full interactive flows through console menu interfaces.
4. **Data Verification Testing:** Validating Python queries, aggregations, and visual chart rendering accuracy against raw DB states.

### 1.2 Testing Techniques Applied
* **Black-Box Testing:**
  * **Equivalence Class Partitioning (ECP):** Valid and invalid email patterns, valid/invalid payment amounts.
  * **Boundary Value Analysis (BVA):** Minimum and maximum values for apartment areas, floor numbers, and zero-amount payments.
* **White-Box Testing:**
  * **Statement Coverage:** Ensuring all code statements in DAO CRUD operations are executed.
  * **Branch / Decision Coverage:** Exercising both true and false paths in conditional status checks and exception triggers.

---

## 2. Test Cases and Execution Results

| Test ID | Module / Feature | Test Objective | Test Input Data | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|---|
| **TC-001** | Unit Management | Verify valid apartment unit registration | `A-301`, `Block A`, Floor 3, `Penthouse`, 2400 sq.ft | Apartment registered in database with 'Vacant' status | Apartment added successfully; query returns 1 row | **PASS** |
| **TC-002** | Unit Management | Prevent duplicate apartment unit registration | `A-101` (already exists in DB) | System rejects insert and throws `InvalidInputException` | `InvalidInputException: Apartment ID already exists: A-101` displayed | **PASS** |
| **TC-003** | Resident Module | Verify valid resident registration & unit occupancy update | `RES014`, `Ravi Shankar`, `ravi.s@gmail.com`, `9842100000`, `A-302`, `Tenant` | Resident record created; Apartment `A-302` status changed to 'Occupied' | Resident saved; apartment status updated to 'Occupied' in DB | **PASS** |
| **TC-004** | Resident Module | Validation of invalid resident email syntax (ECP) | `RES015`, `Kavitha`, `kavitha-invalid-email`, `9842100000`, `B-101` | System rejects with `InvalidInputException` | `Input Validation Error: Invalid email format` displayed | **PASS** |
| **TC-005** | Resident Module | Lookup existing resident by ID | Resident ID: `RES001` | Displays profile for 'Arun Kumar', Apt `A-101`, phone `9842112345` | Accurate details fetched and displayed | **PASS** |
| **TC-006** | Resident Module | Lookup non-existent resident by ID | Resident ID: `RES999` | Throws `ResidentNotFoundException` | `[ERROR] Resident with ID 'RES999' was not found in the system.` | **PASS** |
| **TC-007** | Maintenance | Query all pending & overdue maintenance accounts | View Pending Payments command | Returns all records with status `Pending` or `Overdue` with calculated balances | Correctly lists 7 overdue/pending records with matching balances | **PASS** |
| **TC-008** | Maintenance | Process payment for pending invoice | Bill ID: `PAY-2026-03-02`, Amount: ₹4000.00, Mode: `Online/UPI` | Bill status updated to `Paid`, amount recorded, payment date stamped | Record updated to 'Paid'; subsequent pending query excludes bill | **PASS** |
| **TC-009** | Maintenance | Reject zero or negative payment amounts (BVA) | Bill ID: `PAY-2026-03-03`, Amount: ₹0.00 | Rejected with `InvalidInputException` | `Input Validation Error: Payment amount must be greater than zero.` | **PASS** |
| **TC-010** | Complaint Module | Register new facility complaint | `RES001`, `A-101`, Category: `Plumbing`, Priority: `High`, Desc: `Pipe leakage` | Ticket auto-assigned unique ID `CMP-2026-xxx` in `Open` state | Ticket generated and saved with status 'Open' | **PASS** |
| **TC-011** | Complaint Module | Update ticket status and assign repair technician | Ticket: `CMP-2026-014`, Status: `In Progress`, Staff: `Security Lead`, Remarks: `Technician visiting` | Status updated in DB, assigned staff saved | Ticket updated; displayed correctly in active tickets table | **PASS** |
| **TC-012** | Complaint Module | Resolve complaint and record closure timestamp | Ticket: `CMP-2026-011`, Status: `Resolved`, Staff: `Ramesh (Plumber)`, Remarks: `Capacitor replaced` | Status updated to 'Resolved'; resolution date populated with current date | Status marked 'Resolved'; resolution date automatically set to current date | **PASS** |
| **TC-013** | Reporting Module | Aggregate complaint count by category using Java Streams | Reports Menu Option 1 | Correct tally grouped across Water Supply, Electrical, Cleanliness, Elevator, Security | Accurate breakdown matches SQL aggregate counts exactly | **PASS** |
| **TC-014** | Python EDA | Validate database connectivity and table extraction | `python python_eda/eda_visualization.py` | Connects to SQLite and loads DataFrames without errors | Successfully retrieved 16 apartments, 13 residents, 39 payments, 15 complaints | **PASS** |
| **TC-015** | Python EDA | Generate and export high-resolution chart images | Matplotlib / Seaborn batch execution | 6 PNG images generated in `python_eda/output_visualizations/` | All 6 `.png` images generated and validated on disk | **PASS** |

---

## 3. Requirements Traceability Matrix (RTM)

| Functional Requirement | Test Case ID(s) | Verification Technique | Outcome |
|---|---|---|---|
| **FR-1:** Resident Details Management | TC-003, TC-004, TC-005, TC-006 | Unit & Functional Testing | **VERIFIED (PASS)** |
| **FR-2:** Apartment Details & Occupancy | TC-001, TC-002, TC-003 | Functional Testing | **VERIFIED (PASS)** |
| **FR-3:** Monthly Maintenance Charges & Payments | TC-007, TC-008, TC-009 | Integration Testing | **VERIFIED (PASS)** |
| **FR-4:** Register Complaints with Category | TC-010 | Functional Testing | **VERIFIED (PASS)** |
| **FR-5:** Track Complaint Status (Open/In Progress/Resolved) | TC-011, TC-012 | Lifecycle State Testing | **VERIFIED (PASS)** |
| **FR-6:** Reports on Payments & Complaints | TC-007, TC-013 | Aggregation Testing | **VERIFIED (PASS)** |
| **FR-7:** Python Retrieval & Visualizations | TC-014, TC-015 | End-to-End Visual Verification | **VERIFIED (PASS)** |

---

## 4. Test Conclusion and Quality Summary
* **Total Test Cases Executed:** 15
* **Passed:** 15 (100%)
* **Failed:** 0 (0%)
* **Defect Density:** 0 Critical Defects
* **Conclusion:** The application meets all software engineering quality benchmarks, strict data integrity constraints, and functional requirements specified in the Scenario 18 problem statement.
